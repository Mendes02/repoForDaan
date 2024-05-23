package fontys.sem3.school.persistence;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepoFr extends JpaRepository<UserEntity, Long> {
     UserEntity findByEmail(String email);
     UserEntity findByUsername(String username);
     Optional<UserEntity> findById(long id);


}
