package fontys.sem3.school.business.VideoGameReviewLogic;

import fontys.sem3.school.domain.VideoGameReview;

import java.util.ArrayList;

public interface GetVideoGameReviewsByVideoGameUseCase {
    public ArrayList<VideoGameReview> getVideoGameReviewsByVideoGame(long id);
}
