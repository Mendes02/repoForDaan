package fontys.sem3.school.business.VideoGameReviewLogic;

import fontys.sem3.school.domain.CreateVideoGameReviewRequest;

public interface CreateVideoGameReviewUseCase {
    boolean createReview(CreateVideoGameReviewRequest request);
}
