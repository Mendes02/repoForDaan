package fontys.sem3.school.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyToFriendshipRequestRequest {
    @NotNull
    private long friendshipRequestId;
    @NotNull
    private boolean replyValue;
}
