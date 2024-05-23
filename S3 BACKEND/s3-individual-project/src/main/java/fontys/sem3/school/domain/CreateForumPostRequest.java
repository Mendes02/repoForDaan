package fontys.sem3.school.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateForumPostRequest {
    @NotNull
    private long forumId;
    @NotNull
    private long submittedById;
    @NotBlank
    private String postContent;
}
