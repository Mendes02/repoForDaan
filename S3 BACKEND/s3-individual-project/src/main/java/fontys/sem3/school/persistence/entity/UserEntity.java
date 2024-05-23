package fontys.sem3.school.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String email;
    private String username;
    private String role;
    private String password;

    public UserEntity(String email,String username,String role,String password){
        this.email = email;
        this.username = username;
        this.role = role;
        this.password = password;
    }

}
