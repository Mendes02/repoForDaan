package fontys.sem3.school.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "videogame_review")
public class VideoGameReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "videogame", referencedColumnName = "id")
    private VideoGameEntity videoGame;

    @ManyToOne
    @JoinColumn(name = "reviewed_by", referencedColumnName = "id")
    private UserEntity reviewedBy;
    @Column(name = "review_content")
    private String reviewContent;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "review_value")
    private boolean reviewValue;

    public boolean getRateValue(){
        return this.reviewValue;
    }
}
