package fontys.sem3.school.business.VideoGameReviewLogic;

import fontys.sem3.school.domain.VideoGameReview;

import java.util.Optional;

public interface GetVideoGameReviewByIdUseCase {
    Optional<VideoGameReview> getReviewById(long id);
}
