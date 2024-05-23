package fontys.sem3.school.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "forum")
public class ForumEntity {
    @Id
    private long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private VideoGameEntity videoGame;
}
