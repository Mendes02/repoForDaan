package fontys.sem3.school.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoGameReviewDTO {
    private Long id;
    private String reviewContent;
    private LocalDate creationDate;
    private boolean reviewValue;
    private Long positiveRatingsCount;
}
