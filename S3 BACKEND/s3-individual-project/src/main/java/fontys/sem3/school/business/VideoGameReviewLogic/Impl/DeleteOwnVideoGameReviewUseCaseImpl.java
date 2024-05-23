package fontys.sem3.school.business.VideoGameReviewLogic.Impl;

import fontys.sem3.school.business.VideoGameReviewLogic.DeleteOwnVideoGameReviewUseCase;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.configuration.security.token.AccessToken;
import fontys.sem3.school.configuration.security.token.AccessTokenDecoder;
import fontys.sem3.school.domain.DeleteOwnVideoGameReviewRequest;
import fontys.sem3.school.persistence.VideoGameReviewRatingRepo;
import fontys.sem3.school.persistence.VideoGameReviewRepo;
import fontys.sem3.school.persistence.entity.VideoGameReviewEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteOwnVideoGameReviewUseCaseImpl implements DeleteOwnVideoGameReviewUseCase {
    AccessTokenDecoder decoder;
    VideoGameReviewRepo repo;
    VideoGameReviewRatingRepo ratingRepo;
    AuthenticationService authenticationService;
    @Override
    @Transactional
    public boolean deleteOwnReview(DeleteOwnVideoGameReviewRequest request) {
        AccessToken token = decoder.decode(request.getAccessToken());
        Optional<VideoGameReviewEntity> videoGameReview = repo.findById(request.getVideogameReviewId());
        if (videoGameReview.isEmpty()) return false;

        long authenticatedUserId = authenticationService.getAuthenticatedUserId();
        long reviewAuthorId = videoGameReview.get().getReviewedBy().getId();

        if (authenticatedUserId == reviewAuthorId || authenticationService.userIsAdmin()) {
            ratingRepo.deleteAllByVideogameReview(videoGameReview.get());
            repo.deleteById(request.getVideogameReviewId());
            return true;
        }
        return false;
    }
}
