package fontys.sem3.school.business.UserLogic.Impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.domain.ProfileDataResponse;
import fontys.sem3.school.persistence.*;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GetProfileDataUseCaseImplTest {

    @Mock
    private UserRepoFr userRepo;

    @Mock
    private ForumPostRepo postRepo;

    @Mock
    private VideoGameReviewRepo reviewRepo;

    @Mock
    private VideoGameReviewRatingRepo videoGameReviewRatingRepo;

    @Mock
    private ForumPostRatingRepo forumPostRatingRepo;

    @Mock
    private FriendshipRequestsRepo friendshipRequestsRepo;

    @Mock
    private FriendshipRepo friendshipRepo;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private GetProfileDataUseCaseImpl useCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProfileDataWithExistingUser() throws UserDoesNotExistException {
        long id = 1L;
        Optional<UserEntity> foundUser = Optional.of(new UserEntity(id, "user@example.com", "User", "User", "password"));
        Optional<UserEntity> authenticatedUser = Optional.of(new UserEntity(id, "user@example.com", "User", "User", "password"));
        when(authenticationService.getAuthenticatedUserId()).thenReturn(id);
        when(userRepo.findById(id)).thenReturn(foundUser);
        when(userRepo.findById(authenticationService.getAuthenticatedUserId())).thenReturn(authenticatedUser);
        when(friendshipRepo.existsByUsers(foundUser.get(), authenticatedUser.get())).thenReturn(true);

// Act
        Optional<ProfileDataResponse> result = useCase.getProfileData(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("User", result.get().getUser().getUsername());
        assertEquals("S", result.get().getFriendRequestStatus());
        verify(friendshipRepo).existsByUsers(foundUser.get(), authenticatedUser.get());
    }

    @Test
    void testGetProfileDataWithNonExistingUser() {
        // Arrange
        long userId = 1L;
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserDoesNotExistException.class, () -> {
            useCase.getProfileData(userId);
        });

        verify(userRepo).findById(userId);
    }
}
