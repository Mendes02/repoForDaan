package fontys.sem3.school.persistence;

import fontys.sem3.school.persistence.entity.ForumPostCommentEntity;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ForumPostCommentRepo extends JpaRepository<ForumPostCommentEntity,Long> {
    List<ForumPostCommentEntity> findAllByForumPost(ForumPostEntity entity);
    Integer countAllByForumPost(ForumPostEntity entity);
}
