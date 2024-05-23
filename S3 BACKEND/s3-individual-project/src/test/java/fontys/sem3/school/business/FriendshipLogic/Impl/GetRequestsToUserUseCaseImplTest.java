package fontys.sem3.school.business.FriendshipLogic.Impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import fontys.sem3.school.business.exception.BadCredentialsException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.persistence.FriendshipRequestsRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

public class GetRequestsToUserUseCaseImplTest {

    @Mock
    private UserRepoFr userRepo;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private FriendshipRequestsRepo friendshipRequestsRepo;

    @InjectMocks
    private GetRequestsToUserUseCaseImpl getRequestsToUserUseCase;

    private final long authenticatedUserId = 1L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(authenticationService.getAuthenticatedUserId()).thenReturn(authenticatedUserId);
    }


    @Test
    public void whenUserDoesNotExist_thenThrowUserDoesNotExistException() {
        // Arrange
        when(userRepo.findById(authenticatedUserId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserDoesNotExistException.class, () -> getRequestsToUserUseCase.get(authenticatedUserId));
    }

    @Test
    public void whenAuthenticatedUserIdDoesNotMatch_thenThrowBadCredentialsException() {
        // Arrange
        long anotherUserId = 2L;

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> getRequestsToUserUseCase.get(anotherUserId));
    }
}
