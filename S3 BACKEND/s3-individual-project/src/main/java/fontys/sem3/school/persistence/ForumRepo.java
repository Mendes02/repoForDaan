package fontys.sem3.school.persistence;

import fontys.sem3.school.persistence.entity.ForumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForumRepo extends JpaRepository<ForumEntity,Long> {
     Optional<ForumEntity> findById(long id);
}
