package fontys.sem3.school.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "forum_post_rating",
        uniqueConstraints = @UniqueConstraint(columnNames = {"rated_by", "forum_post"}))
public class ForumPostRatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rated_by")
    private UserEntity ratedBy;

    @Column(name = "rate_value")
    private boolean rateValue;

    @ManyToOne
    @JoinColumn(name = "forum_post")
    private ForumPostEntity forumPost;

    public boolean getRatedValue(){
        return this.rateValue;
    }
    public ForumPostRatingEntity(UserEntity ratedBy,boolean rateValue,ForumPostEntity forumPost){
        this.ratedBy = ratedBy;
        this.rateValue = rateValue;
        this.forumPost = forumPost;
    }
    public void setRatedValue(boolean value){
        this.rateValue = value;
    }
}