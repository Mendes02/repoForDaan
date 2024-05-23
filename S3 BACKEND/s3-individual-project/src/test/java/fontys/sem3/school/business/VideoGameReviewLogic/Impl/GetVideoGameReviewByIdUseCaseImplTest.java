package fontys.sem3.school.business.VideoGameReviewLogic.Impl;

import fontys.sem3.school.business.impl.converters.VideoGameReviewConverter;
import fontys.sem3.school.domain.VideoGameReview;
import fontys.sem3.school.persistence.VideoGameReviewRepo;
import fontys.sem3.school.persistence.entity.VideoGameReviewEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class GetVideoGameReviewByIdUseCaseImplTest {

    @Mock
    private VideoGameReviewRepo repo;

    @Mock
    private VideoGameReviewConverter converter;

    @InjectMocks
    private GetVideoGameReviewByIdUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReviewByIdWithExistingReview() {
        // Arrange
        long id = 1L;
        VideoGameReviewEntity reviewEntity = new VideoGameReviewEntity(); // Initialize with proper attributes
        VideoGameReview review = new VideoGameReview(); // Initialize with proper attributes

        when(repo.findById(id)).thenReturn(Optional.of(reviewEntity));
        when(converter.convert(reviewEntity)).thenReturn(review);

        // Act
        Optional<VideoGameReview> result = useCase.getReviewById(id);

        // Assert
        assertTrue(result.isPresent(), "The result should be present");
        assertEquals(review, result.get(), "The review obtained should match the expected review");
        verify(repo).findById(id);
        verify(converter).convert(reviewEntity);
    }

    @Test
    void testGetReviewByIdWithNonExistingReview() {
        // Arrange
        long id = 1L;
        when(repo.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<VideoGameReview> result = useCase.getReviewById(id);

        // Assert
        assertFalse(result.isPresent(), "The result should not be present for a non-existing review");
        verify(repo).findById(id);
        verify(converter, never()).convert(any(VideoGameReviewEntity.class));
    }
}
