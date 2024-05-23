package fontys.sem3.school.business.VideoGameReviewLogic.Impl;

import fontys.sem3.school.business.VideoGameReviewLogic.CreateVideoGameReviewUseCase;
import fontys.sem3.school.domain.CreateVideoGameReviewRequest;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.VideoGameRepo;
import fontys.sem3.school.persistence.VideoGameReviewRepo;
import fontys.sem3.school.persistence.entity.UserEntity;
import fontys.sem3.school.persistence.entity.VideoGameEntity;
import fontys.sem3.school.persistence.entity.VideoGameReviewEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateVideoGameReviewUseCaseImpl implements CreateVideoGameReviewUseCase {
    UserRepoFr userRepo;
    VideoGameRepo videoGameRepo;
    VideoGameReviewRepo videoGameReviewRepo;
    @Override
    @Transactional
    public boolean createReview(CreateVideoGameReviewRequest request) {
        Optional<UserEntity> user = userRepo.findById(request.getUserId());
        Optional<VideoGameEntity> videoGame = videoGameRepo.findById(request.getVideoGameId());
        if(user.isEmpty() || videoGame.isEmpty()) return false;
        VideoGameReviewEntity review = VideoGameReviewEntity.builder()
                .videoGame(videoGame.get())
                .reviewedBy(user.get())
                .reviewContent(request.getReviewContent())
                .creationDate(LocalDate.now())
                .reviewValue(request.getRateValue())
                .build();
        videoGameReviewRepo.save(review);
        return true;
    }
}
