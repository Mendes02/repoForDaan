package fontys.sem3.school.domain;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ForumPostComment {
    private Long id;

    private ForumPost forumPost;

    private User submittedBy;

    private String commentContent;

    private LocalDate publishedDate;
}
