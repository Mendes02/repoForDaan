package fontys.sem3.school.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVideoGameReviewRequest {
    @NotNull
    private long videoGameId;
    @NotNull
    private long userId;
    @NotBlank
    private String reviewContent;
    @NotNull
    private boolean rateValue;

    public boolean getRateValue(){
        return this.rateValue;
    }
}
