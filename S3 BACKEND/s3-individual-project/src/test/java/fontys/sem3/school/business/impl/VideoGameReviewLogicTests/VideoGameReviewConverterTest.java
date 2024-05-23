package fontys.sem3.school.business.impl.VideoGameReviewLogicTests;
import fontys.sem3.school.business.impl.converters.VideoGameReviewConverter;
import fontys.sem3.school.domain.VideoGameReview;
import fontys.sem3.school.persistence.VideoGameReviewRatingRepo;
import fontys.sem3.school.persistence.entity.UserEntity;
import fontys.sem3.school.persistence.entity.VideoGameEntity;
import fontys.sem3.school.persistence.entity.VideoGameReviewEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

class VideoGameReviewConverterTest {

    @Mock
    private VideoGameReviewRatingRepo videoGameReviewRatingRepo;
    @InjectMocks
    private VideoGameReviewConverter videoGameReviewConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void convert_WhenEntityIsValid() {
        // Arrange
        VideoGameReviewEntity reviewEntity = mock(VideoGameReviewEntity.class);
        VideoGameEntity videoGameEntity = mock(VideoGameEntity.class);
        UserEntity reviewedBy = mock(UserEntity.class);
        when(reviewEntity.getId()).thenReturn(1L);
        when(reviewEntity.getVideoGame()).thenReturn(videoGameEntity);
        when(reviewEntity.getReviewedBy()).thenReturn(reviewedBy);
        when(reviewEntity.getReviewContent()).thenReturn("Great game!");
        when(reviewEntity.getRateValue()).thenReturn(true);
        when(reviewEntity.getCreationDate()).thenReturn(LocalDate.now());
        when(videoGameReviewRatingRepo.countAllByVideogameReviewAndRateValue(reviewEntity, true)).thenReturn(10);
        when(videoGameReviewRatingRepo.countAllByVideogameReviewAndRateValue(reviewEntity, false)).thenReturn(3);

        // Act
        VideoGameReview review = videoGameReviewConverter.convert(reviewEntity);

        // Assert
        assertNotNull(review);
        assertEquals(reviewEntity.getId(), review.getId());
        assertEquals(10L, review.getPositiveRatings());
        assertEquals(3L, review.getNegativeRatings());
        // ... assert the remaining fields as needed.
    }

    @Test
    void convertList_WhenListIsNotEmpty() {
        // Arrange
        VideoGameReviewEntity reviewEntity1 = mock(VideoGameReviewEntity.class);
        VideoGameReviewEntity reviewEntity2 = mock(VideoGameReviewEntity.class);
        UserEntity reviewer1 = mock(UserEntity.class);
        UserEntity reviewer2 = mock(UserEntity.class);
        VideoGameEntity videoGame1 = mock(VideoGameEntity.class);
        VideoGameEntity videoGame2 = mock(VideoGameEntity.class);

        when(reviewEntity1.getReviewedBy()).thenReturn(reviewer1);
        when(reviewEntity2.getReviewedBy()).thenReturn(reviewer2);
        when(reviewEntity1.getVideoGame()).thenReturn(videoGame1);
        when(reviewEntity2.getVideoGame()).thenReturn(videoGame2);
        // Mock other required methods similarly...

        when(videoGameReviewRatingRepo.countAllByVideogameReviewAndRateValue(reviewEntity1, true)).thenReturn(10);
        when(videoGameReviewRatingRepo.countAllByVideogameReviewAndRateValue(reviewEntity1, false)).thenReturn(1);
        when(videoGameReviewRatingRepo.countAllByVideogameReviewAndRateValue(reviewEntity2, true)).thenReturn(5);
        when(videoGameReviewRatingRepo.countAllByVideogameReviewAndRateValue(reviewEntity2, false)).thenReturn(3);

        List<VideoGameReviewEntity> entityList = Arrays.asList(reviewEntity1, reviewEntity2);

        // Act
        ArrayList<VideoGameReview> reviewList = videoGameReviewConverter.convertList(entityList);

        // Assert
        assertEquals(entityList.size(), reviewList.size());
        // Assert the details of each VideoGameReview in the list to ensure they are converted correctly
        // Example:
        VideoGameReview review1 = reviewList.get(0);
        VideoGameReview review2 = reviewList.get(1);
        assertNotNull(review1);
        assertNotNull(review2);
        assertEquals(10L, review1.getPositiveRatings());
        assertEquals(1L, review1.getNegativeRatings());
        assertEquals(5L, review2.getPositiveRatings());
        assertEquals(3L, review2.getNegativeRatings());
        // ... additional assertions for other fields
    }

    // Additional tests can be added to cover other scenarios, such as an empty list
}
