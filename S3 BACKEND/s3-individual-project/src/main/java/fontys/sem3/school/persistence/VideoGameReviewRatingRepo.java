package fontys.sem3.school.persistence;

import fontys.sem3.school.persistence.entity.UserEntity;
import fontys.sem3.school.persistence.entity.VideoGameReviewEntity;
import fontys.sem3.school.persistence.entity.VideoGameReviewRatingEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VideoGameReviewRatingRepo extends JpaRepository<VideoGameReviewRatingEntity,Long> {
    Integer countAllByVideogameReviewAndRateValue(VideoGameReviewEntity review, boolean rateValue);
    Optional<VideoGameReviewRatingEntity> findByRatedByAndVideogameReview(UserEntity user, VideoGameReviewEntity review);
    @Query("SELECT v.videogameReview FROM VideoGameReviewRatingEntity v " +
            "WHERE v.rateValue = true AND v.videogameReview.creationDate >= :yesterday " +
            "GROUP BY v.videogameReview ORDER BY COUNT(v) DESC")
    List<VideoGameReviewEntity> findTopVideoGameReviewsWithMostTrueRatingsSince(Pageable pageable, @Param("yesterday") LocalDate yesterday);

    @Query("SELECT COUNT(r) FROM VideoGameReviewRatingEntity r WHERE r.videogameReview.reviewedBy = :user AND r.rateValue = true")
    long countTotalLikesOnUserReviews(UserEntity user);
    void deleteAllByVideogameReview(VideoGameReviewEntity entity);
}
