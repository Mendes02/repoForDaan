package fontys.sem3.school.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoGameReview {
    private long id;
    private VideoGame videoGame;
    private User reviewedBy;
    private String reviewContent;
    private int positiveRatings;
    private int negativeRatings;
    private LocalDate creationDate;
    private boolean rateValue;
}
