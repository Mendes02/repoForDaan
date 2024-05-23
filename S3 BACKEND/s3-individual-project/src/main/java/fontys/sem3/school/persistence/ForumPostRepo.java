package fontys.sem3.school.persistence;

import fontys.sem3.school.persistence.entity.ForumEntity;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ForumPostRepo extends JpaRepository<ForumPostEntity,Long> {
     List<ForumPostEntity> findByForum(ForumEntity forum);
     List<ForumPostEntity> findByPostedBy(UserEntity user);
     Optional<ForumPostEntity> findById(long id);

     int countAllByPostedBy(UserEntity entity);
     @Query(value = "SELECT fp.id, fp.post_content, fp.creation_date, " +
             "COUNT(fpr.id) AS ratingsCount, " +
             "SUM(CASE WHEN fpr.rate_value = TRUE THEN 1 ELSE 0 END) AS positiveRatingsSum " +
             "FROM forum_post_rating fpr " +
             "JOIN forum_post fp ON fpr.forum_post = fp.id " +
             "GROUP BY fp.id, fp.post_content, fp.creation_date " +
             "ORDER BY ratingsCount DESC, positiveRatingsSum DESC",
             nativeQuery = true)
     List<Object[]> findTrendingPosts();
}
