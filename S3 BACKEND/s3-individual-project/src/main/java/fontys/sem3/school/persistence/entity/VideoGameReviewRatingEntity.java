package fontys.sem3.school.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "videogame_review_rating")
public class VideoGameReviewRatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "rate_value")
    private boolean rateValue;
    @ManyToOne
    @JoinColumn(name = "videogame_review_id", referencedColumnName = "id")
    private VideoGameReviewEntity videogameReview;
    @ManyToOne
    @JoinColumn(name = "rated_by")
    private UserEntity ratedBy;

    public boolean getRateValue(){
        return this.rateValue;
    }
    public VideoGameReviewRatingEntity(UserEntity user, VideoGameReviewEntity review, boolean rateValue){
        this.ratedBy = user;
        this.videogameReview = review;
        this.rateValue = rateValue;
    }
}
