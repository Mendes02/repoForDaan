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
@Builder
@Data
@Table(name = "videogame")
public class VideoGameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    String publisher;
    @Column
    String name;
    @Column
    LocalDate released;
    @Column
    String genre;

    public VideoGameEntity(String publisher, String name, LocalDate released,String genre){
        this.publisher = publisher;
        this.name = name;
        this.released = released;
        this.genre=genre;
    }
}
