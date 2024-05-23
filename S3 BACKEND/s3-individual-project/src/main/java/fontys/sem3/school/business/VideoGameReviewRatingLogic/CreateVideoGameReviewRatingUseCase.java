package fontys.sem3.school.business.VideoGameReviewRatingLogic;

import fontys.sem3.school.domain.SaveVideoGameReviewRatingRequest;

public interface CreateVideoGameReviewRatingUseCase {
    boolean saveRating(SaveVideoGameReviewRatingRequest request);
}
