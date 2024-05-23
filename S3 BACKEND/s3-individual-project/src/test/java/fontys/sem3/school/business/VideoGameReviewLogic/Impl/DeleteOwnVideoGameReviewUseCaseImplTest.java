package fontys.sem3.school.business.VideoGameReviewLogic.Impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.configuration.security.token.AccessTokenDecoder;
import fontys.sem3.school.domain.DeleteOwnVideoGameReviewRequest;
import fontys.sem3.school.persistence.VideoGameReviewRatingRepo;
import fontys.sem3.school.persistence.VideoGameReviewRepo;
import fontys.sem3.school.persistence.entity.UserEntity;
import fontys.sem3.school.persistence.entity.VideoGameReviewEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class DeleteOwnVideoGameReviewUseCaseImplTest {

    @Mock
    private AccessTokenDecoder decoder;
    @Mock
    private VideoGameReviewRepo repo;
    @Mock
    private VideoGameReviewRatingRepo ratingRepo;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private DeleteOwnVideoGameReviewUseCaseImpl useCase;

    private final long reviewId = 1L;
    private final long userId = 2L;
    private final String accessToken = "valid.token";
    private DeleteOwnVideoGameReviewRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new DeleteOwnVideoGameReviewRequest(accessToken, reviewId);
    }
    @Test
    void deleteOwnReview_ReviewDoesNotExist_ShouldReturnFalse() {
        // Arrange
        when(repo.findById(reviewId)).thenReturn(Optional.empty());

        // Act
        boolean result = useCase.deleteOwnReview(request);

        // Assert
        assertFalse(result);
    }

    @Test
    void deleteOwnReview_UserIsNotOwnerNorAdmin_ShouldReturnFalse() {
        // Arrange
        VideoGameReviewEntity reviewEntity = mock(VideoGameReviewEntity.class);
        UserEntity reviewAuthorEntity = mock(UserEntity.class);
        when(reviewAuthorEntity.getId()).thenReturn(userId + 1); // Different user ID
        when(reviewEntity.getReviewedBy()).thenReturn(reviewAuthorEntity);
        when(repo.findById(reviewId)).thenReturn(Optional.of(reviewEntity));
        when(authenticationService.getAuthenticatedUserId()).thenReturn(userId);
        when(authenticationService.userIsAdmin()).thenReturn(false);

        // Make sure request is initialized before using it
        // Ensure it's not getting reset or changed to null anywhere in your code before this point

        // Act
        boolean result = useCase.deleteOwnReview(request);

        // Assert
        assertFalse(result);
    }

    @Test
    void deleteOwnReview_UserIsOwner_ShouldDeleteReviewAndReturnTrue() {
        // Arrange
        VideoGameReviewEntity reviewEntity = mock(VideoGameReviewEntity.class);
        UserEntity reviewAuthorEntity = mock(UserEntity.class);
        when(reviewAuthorEntity.getId()).thenReturn(userId);
        when(reviewEntity.getReviewedBy()).thenReturn(reviewAuthorEntity);
        when(repo.findById(reviewId)).thenReturn(Optional.of(reviewEntity));
        when(authenticationService.getAuthenticatedUserId()).thenReturn(userId);
        when(authenticationService.userIsAdmin()).thenReturn(false);

        // Act
        boolean result = useCase.deleteOwnReview(request);

        // Assert
        assertTrue(result);
        verify(ratingRepo).deleteAllByVideogameReview(reviewEntity);
        verify(repo).deleteById(reviewId);
    }

    @Test
    void deleteOwnReview_UserIsAdmin_ShouldDeleteReviewAndReturnTrue() {
        // Arrange
        VideoGameReviewEntity reviewEntity = mock(VideoGameReviewEntity.class);
        UserEntity reviewAuthorEntity = mock(UserEntity.class);
        when(reviewAuthorEntity.getId()).thenReturn(userId + 1); // Different user ID
        when(reviewEntity.getReviewedBy()).thenReturn(reviewAuthorEntity);
        when(repo.findById(reviewId)).thenReturn(Optional.of(reviewEntity));
        when(authenticationService.getAuthenticatedUserId()).thenReturn(userId);
        when(authenticationService.userIsAdmin()).thenReturn(true);

        // Act
        boolean result = useCase.deleteOwnReview(request);

        // Assert
        assertTrue(result);
        verify(ratingRepo).deleteAllByVideogameReview(reviewEntity);
        verify(repo).deleteById(reviewId);
    }

    // Add test methods here
}
