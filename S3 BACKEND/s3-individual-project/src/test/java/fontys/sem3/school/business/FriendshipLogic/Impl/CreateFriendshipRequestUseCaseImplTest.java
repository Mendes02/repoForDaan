package fontys.sem3.school.business.FriendshipLogic.Impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import fontys.sem3.school.business.exception.BadCredentialsException;
import fontys.sem3.school.business.exception.FriendshipAlreadyExistsException;
import fontys.sem3.school.business.exception.FriendshipRequestAlreadyExistsException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.persistence.FriendshipRepo;
import fontys.sem3.school.persistence.FriendshipRequestsRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.FriendshipRequestEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class CreateFriendshipRequestUseCaseImplTest {

    @Mock
    private UserRepoFr userRepo;
    @Mock
    private FriendshipRepo friendshipRepo;
    @Mock
    private FriendshipRequestsRepo friendshipRequestsRepo;
    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private CreateFriendshipRequestUseCaseImpl createFriendshipRequestUseCase;

    private final long requesterId = 1L;
    private final long requestedId = 2L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenValidUsers_thenCreateFriendshipRequest() throws Exception {
        // Arrange
        UserEntity requesterEntity = new UserEntity();
        UserEntity requestedEntity = new UserEntity();
        when(authenticationService.getAuthenticatedUserId()).thenReturn(requesterId);
        when(userRepo.findById(requesterId)).thenReturn(Optional.of(requesterEntity));
        when(userRepo.findById(requestedId)).thenReturn(Optional.of(requestedEntity));
        when(friendshipRepo.existsByUsers(requesterEntity, requestedEntity)).thenReturn(false);
        when(friendshipRequestsRepo.existsByRequesterAndRequested(requesterEntity, requestedEntity)).thenReturn(false);

        // Act
        createFriendshipRequestUseCase.createFriendshipRequest(requesterId, requestedId);

        // Assert
        verify(friendshipRequestsRepo).save(any(FriendshipRequestEntity.class));
    }

    @Test
    public void whenRequesterDoesNotExist_thenThrowUserDoesNotExistException() {
        // Arrange
        when(authenticationService.getAuthenticatedUserId()).thenReturn(requesterId);
        when(userRepo.findById(requesterId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserDoesNotExistException.class, () -> createFriendshipRequestUseCase.createFriendshipRequest(requesterId, requestedId));
    }
    @Test
    public void whenRequestedDoesNotExist_thenThrowUserDoesNotExistException() {
        // Arrange
        UserEntity requesterEntity = new UserEntity();
        when(authenticationService.getAuthenticatedUserId()).thenReturn(requesterId);
        when(userRepo.findById(requesterId)).thenReturn(Optional.of(requesterEntity));
        when(userRepo.findById(requestedId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserDoesNotExistException.class, () -> createFriendshipRequestUseCase.createFriendshipRequest(requesterId, requestedId));
    }

    @Test
    public void whenFriendshipAlreadyExists_thenThrowFriendshipAlreadyExistsException() {
        // Arrange
        UserEntity requesterEntity = new UserEntity();
        UserEntity requestedEntity = new UserEntity();
        when(authenticationService.getAuthenticatedUserId()).thenReturn(requesterId);
        when(userRepo.findById(requesterId)).thenReturn(Optional.of(requesterEntity));
        when(userRepo.findById(requestedId)).thenReturn(Optional.of(requestedEntity));
        when(friendshipRepo.existsByUsers(requesterEntity, requestedEntity)).thenReturn(true);

        // Act & Assert
        assertThrows(FriendshipAlreadyExistsException.class, () -> createFriendshipRequestUseCase.createFriendshipRequest(requesterId, requestedId));
    }

    @Test
    public void whenFriendshipRequestAlreadyExists_thenThrowFriendshipRequestAlreadyExistsException() {
        // Arrange
        UserEntity requesterEntity = new UserEntity();
        UserEntity requestedEntity = new UserEntity();
        when(authenticationService.getAuthenticatedUserId()).thenReturn(requesterId);
        when(userRepo.findById(requesterId)).thenReturn(Optional.of(requesterEntity));
        when(userRepo.findById(requestedId)).thenReturn(Optional.of(requestedEntity));
        when(friendshipRepo.existsByUsers(requesterEntity, requestedEntity)).thenReturn(false);
        when(friendshipRequestsRepo.existsByRequesterAndRequested(requesterEntity, requestedEntity)).thenReturn(true);

        // Act & Assert
        assertThrows(FriendshipRequestAlreadyExistsException.class, () -> createFriendshipRequestUseCase.createFriendshipRequest(requesterId, requestedId));
    }

    @Test
    public void whenAuthenticatedUserIdDoesNotMatchRequesterId_thenThrowBadCredentialsException() {
        // Arrange
        when(authenticationService.getAuthenticatedUserId()).thenReturn(3L); // Different from requesterId

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> createFriendshipRequestUseCase.createFriendshipRequest(requesterId, requestedId));
    }
}