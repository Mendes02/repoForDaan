package fontys.sem3.school.persistence;

import fontys.sem3.school.persistence.entity.ForumPostEntity;
import fontys.sem3.school.persistence.entity.ForumPostRatingEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ForumPostRatingRepo extends JpaRepository<ForumPostRatingEntity,Long> {
     List<ForumPostRatingEntity> findByForumPost(ForumPostEntity post);
     Optional<ForumPostRatingEntity> findByRatedByAndForumPost(UserEntity user, ForumPostEntity post);
     @Query("SELECT fpr FROM ForumPostRatingEntity fpr WHERE fpr.forumPost.id IN :postIds")
     List<ForumPostRatingEntity> findAllByForumPostIds(@Param("postIds") ArrayList<Long> postIds);

     @Query("SELECT fpr.forumPost FROM ForumPostRatingEntity fpr " +
             "WHERE fpr.rateValue = true AND fpr.forumPost.creationDate >= :yesterday " +
             "GROUP BY fpr.forumPost ORDER BY COUNT(fpr) DESC")
     List<ForumPostEntity> findTopForumPostsByPositiveRatingsSince(Pageable pageable, @Param("yesterday") LocalDate yesterday);
     List<ForumPostRatingEntity> findByRatedBy(UserEntity user);
     Integer countAllByForumPostAndRateValue(ForumPostEntity post,boolean rateValue);

     @Query("SELECT COUNT(r) FROM ForumPostRatingEntity r WHERE r.forumPost.postedBy = :user AND r.rateValue = true")
     long countLikesOnUserForumPosts(UserEntity user);
}
