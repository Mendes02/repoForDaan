package fontys.sem3.school.persistence;

import fontys.sem3.school.persistence.entity.UserEntity;
import fontys.sem3.school.persistence.entity.VideoGameEntity;
import fontys.sem3.school.persistence.entity.VideoGameReviewEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VideoGameReviewRepo extends JpaRepository<VideoGameReviewEntity,Long> {
    List<VideoGameReviewEntity> findByVideoGameId(long id);
    int countAllByReviewedBy(UserEntity entity);

    @Query("SELECT v.videoGame FROM VideoGameReviewEntity v " +
            "WHERE v.reviewValue = true AND v.creationDate >= :yesterday " +
            "GROUP BY v.videoGame " +
            "ORDER BY COUNT(v) DESC")
    List<VideoGameEntity> findVideoGamesWithMostPositiveReviewsSince(@Param("yesterday") LocalDate yesterday, Pageable pageable);


}
