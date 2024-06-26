package fontys.sem3.school.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoGameReviewRating {
    private long id;
    private boolean rateValue;
    private VideoGameReview review;
}
