package fontys.sem3.school.business.VideoGameReviewRatingLogic.Impl;

import fontys.sem3.school.business.VideoGameReviewRatingLogic.CreateVideoGameReviewRatingUseCase;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.domain.SaveVideoGameReviewRatingRequest;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.VideoGameReviewRatingRepo;
import fontys.sem3.school.persistence.VideoGameReviewRepo;
import fontys.sem3.school.persistence.entity.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateVideoGameReviewRatingUseCaseImpl implements CreateVideoGameReviewRatingUseCase {
    AuthenticationService authenticationService;
    UserRepoFr userRepo;
    VideoGameReviewRepo videoGameReviewRepo;
    VideoGameReviewRatingRepo videoGameReviewRatingRepo;
    @Override
    @Transactional
    public boolean saveRating(SaveVideoGameReviewRatingRequest request) {
        Long userId = authenticationService.getAuthenticatedUserId();
        if(userId != request.getRatedById()) return false;
        Optional<UserEntity> foundUser = userRepo.findById(request.getRatedById());
        if(foundUser.isEmpty()) return false;
        Optional<VideoGameReviewEntity> foundVideoGameReview = videoGameReviewRepo.findById(request.getVideogameReviewId());
        if(foundVideoGameReview.isEmpty()) return false;
        Optional<VideoGameReviewRatingEntity> rating = videoGameReviewRatingRepo.findByRatedByAndVideogameReview(foundUser.get(),foundVideoGameReview.get());
        if(rating.isPresent() && rating.get().getRateValue() == request.getRateValue()){
            deleteRating(rating.get());
            return true;
        }
        else if(rating.isPresent() && rating.get().getRateValue() != request.getRateValue()){
            rating.get().setRateValue(request.getRateValue());
            replaceRatingValue(rating.get());
            return true;
        }
        else if(rating.isEmpty()) {
            videoGameReviewRatingRepo.save(new VideoGameReviewRatingEntity(foundUser.get(), foundVideoGameReview.get(), request.getRateValue()));
            return true;
        }
        return true;
    }
    private void deleteRating(VideoGameReviewRatingEntity entity){
        videoGameReviewRatingRepo.delete(entity);
    }

    private void replaceRatingValue(VideoGameReviewRatingEntity entity){
        videoGameReviewRatingRepo.save(entity);
    }
}
