package fontys.sem3.school.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "forum_post_comments")
public class ForumPostCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forum_post_id", nullable = false)
    private ForumPostEntity forumPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitted_by_id", nullable = false)
    private UserEntity submittedBy;

    @Column(name = "comment_content", nullable = false, columnDefinition = "TEXT")
    private String commentContent;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;

    public ForumPostCommentEntity(ForumPostEntity post, UserEntity submittedBy, String commentContent, LocalDate publishedDate){
        this.forumPost = post;
        this.submittedBy = submittedBy;
        this.commentContent = commentContent;
        this.publishedDate = publishedDate;
    }

}
