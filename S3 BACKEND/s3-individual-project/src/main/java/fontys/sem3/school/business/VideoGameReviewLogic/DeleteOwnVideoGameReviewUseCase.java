package fontys.sem3.school.business.VideoGameReviewLogic;

import fontys.sem3.school.domain.DeleteOwnVideoGameReviewRequest;

public interface DeleteOwnVideoGameReviewUseCase {
    boolean deleteOwnReview(DeleteOwnVideoGameReviewRequest request);
}
