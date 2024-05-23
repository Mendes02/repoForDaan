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
@Table(name = "forum_post")
public class ForumPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "forum", referencedColumnName = "id")
    private ForumEntity forum;

    @ManyToOne
    @JoinColumn(name = "posted_by", referencedColumnName = "id")
    private UserEntity postedBy;

    @Column(name = "post_content")
    private String postContent;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    public ForumPostEntity(ForumEntity forum, UserEntity postedBy, String postContent,LocalDate localDate){
        this.forum = forum;
        this.postedBy = postedBy;
        this.postContent = postContent;
        this.creationDate = localDate;
    }
}