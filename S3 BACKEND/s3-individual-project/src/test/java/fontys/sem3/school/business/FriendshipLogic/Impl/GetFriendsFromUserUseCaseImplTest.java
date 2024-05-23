package fontys.sem3.school.business.FriendshipLogic.Impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import fontys.sem3.school.business.exception.BadCredentialsException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.persistence.FriendshipRepo;
import fontys.sem3.school.persistence.UserRepoFr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

public class GetFriendsFromUserUseCaseImplTest {

    @Mock
    private UserRepoFr userRepo;
    @Mock
    private FriendshipRepo friendshipRepo;
    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private GetFriendsFromUserUseCaseImpl getFriendsFromUserUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }




    @Test
    public void whenUserDoesNotExist_thenThrowUserDoesNotExistException() {
        // Arrange
        long userId = 1L;
        when(authenticationService.getAuthenticatedUserId()).thenReturn(userId);
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserDoesNotExistException.class, () -> getFriendsFromUserUseCase.getFriendsFromUser(userId));
    }

    @Test
    public void whenAuthenticatedUserIdDoesNotMatch_thenThrowBadCredentialsException() {
        // Arrange
        long authenticatedUserId = 1L;
        long requestedUserId = 2L;
        when(authenticationService.getAuthenticatedUserId()).thenReturn(authenticatedUserId);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> getFriendsFromUserUseCase.getFriendsFromUser(requestedUserId));
    }
}
