package fontys.sem3.school.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveVideoGameReviewRatingRequest {
    @NotNull
    private long ratedById;
    @NotNull
    private boolean rateValue;
    @NotNull
    private long videogameReviewId;

    public boolean getRateValue(){
        return this.rateValue;
    }
}
