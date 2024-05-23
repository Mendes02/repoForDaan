package fontys.sem3.school.business.VideoGameReviewRatingLogic.Impl;

import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.domain.SaveVideoGameReviewRatingRequest;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.VideoGameReviewRatingRepo;
import fontys.sem3.school.persistence.VideoGameReviewRepo;
import fontys.sem3.school.persistence.entity.UserEntity;
import fontys.sem3.school.persistence.entity.VideoGameEntity;
import fontys.sem3.school.persistence.entity.VideoGameReviewEntity;
import fontys.sem3.school.persistence.entity.VideoGameReviewRatingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateVideoGameReviewRatingUseCaseImplTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserRepoFr userRepo;

    @Mock
    private VideoGameReviewRepo videoGameReviewRepo;

    @Mock
    private VideoGameReviewRatingRepo videoGameReviewRatingRepo;

    private CreateVideoGameReviewRatingUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new CreateVideoGameReviewRatingUseCaseImpl(authenticationService, userRepo, videoGameReviewRepo, videoGameReviewRatingRepo);
    }

    @Test
    void testSaveRatingSuccessful() {
        // Arrange
        UserEntity userEntity = new UserEntity(1L,"user@example.com", "username", "role", "password");
        VideoGameEntity videoGameEntity = new VideoGameEntity(1L,"String publisher", "String name", LocalDate .now(),"String genre");
        VideoGameReviewEntity reviewEntity = new VideoGameReviewEntity(1L,videoGameEntity, userEntity, "Review content", LocalDate.now(), true);
        SaveVideoGameReviewRatingRequest request = new SaveVideoGameReviewRatingRequest(userEntity.getId(), true, 5);

        when(authenticationService.getAuthenticatedUserId()).thenReturn(userEntity.getId());
        when(userRepo.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        when(videoGameReviewRepo.findById(request.getVideogameReviewId())).thenReturn(Optional.of(reviewEntity));
        when(videoGameReviewRatingRepo.findByRatedByAndVideogameReview(any(), any())).thenReturn(Optional.empty());

        // Act
        boolean result = useCase.saveRating(request);

        // Assert
        assertTrue(result);
        verify(videoGameReviewRatingRepo).save(any());
    }

    @Test
    void testSaveRatingUnauthorizedUser() {
        // Arrange
        SaveVideoGameReviewRatingRequest request = new SaveVideoGameReviewRatingRequest(1L, true, 5); // User ID 1 is not authenticated
        when(authenticationService.getAuthenticatedUserId()).thenReturn(1L);

        // Act
        boolean result = useCase.saveRating(request);

        // Assert
        assertFalse(result);

    }

    @Test
    void testSaveRatingUserNotFound() {
        // Arrange
        SaveVideoGameReviewRatingRequest request = new SaveVideoGameReviewRatingRequest(1L, true, 5);
        when(authenticationService.getAuthenticatedUserId()).thenReturn(1L);
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        // Act
        boolean result = useCase.saveRating(request);

        // Assert
        assertFalse(result);
        verifyNoInteractions(videoGameReviewRepo, videoGameReviewRatingRepo);
    }

    @Test
    void testSaveRatingReviewNotFound() {
        // Arrange
        SaveVideoGameReviewRatingRequest request = new SaveVideoGameReviewRatingRequest(1L, true, 5);
        when(authenticationService.getAuthenticatedUserId()).thenReturn(1L);
        when(userRepo.findById(1L)).thenReturn(Optional.of(new UserEntity()));
        when(videoGameReviewRepo.findById(1L)).thenReturn(Optional.empty());

        // Act
        boolean result = useCase.saveRating(request);

        // Assert
        assertFalse(result);
        verifyNoInteractions(videoGameReviewRatingRepo);
    }

    @Test
    void testSaveRatingUpdateExistingRating() {
        // Arrange
        SaveVideoGameReviewRatingRequest request = new SaveVideoGameReviewRatingRequest(1L, true, 4);
        UserEntity userEntity = new UserEntity();
        VideoGameReviewEntity reviewEntity = new VideoGameReviewEntity();
        VideoGameReviewRatingEntity existingRating = new VideoGameReviewRatingEntity(userEntity, reviewEntity, true);
        when(authenticationService.getAuthenticatedUserId()).thenReturn(1L);
        when(userRepo.findById(1L)).thenReturn(Optional.of(userEntity));
        when(videoGameReviewRepo.findById(request.getVideogameReviewId())).thenReturn(Optional.of(reviewEntity));
        when(videoGameReviewRatingRepo.findByRatedByAndVideogameReview(userEntity, reviewEntity)).thenReturn(Optional.of(existingRating));

        // Act
        boolean result = useCase.saveRating(request);

        // Assert
        assertTrue(result);
        assertEquals(true, existingRating.getRateValue());
    }
}
