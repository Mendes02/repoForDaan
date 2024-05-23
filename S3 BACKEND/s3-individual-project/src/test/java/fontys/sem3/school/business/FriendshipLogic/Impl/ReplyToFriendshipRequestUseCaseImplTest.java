package fontys.sem3.school.business.FriendshipLogic.Impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import fontys.sem3.school.business.exception.BadCredentialsException;
import fontys.sem3.school.business.exception.FriendshipAlreadyExistsException;
import fontys.sem3.school.business.exception.FriendshipRequestDoesNotExistException;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.domain.ReplyToFriendshipRequestRequest;
import fontys.sem3.school.persistence.FriendshipRepo;
import fontys.sem3.school.persistence.FriendshipRequestsRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.FriendshipEntity;
import fontys.sem3.school.persistence.entity.FriendshipRequestEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class ReplyToFriendshipRequestUseCaseImplTest {

    @Mock
    private UserRepoFr userRepo;
    @Mock
    private FriendshipRequestsRepo friendshipRequestsRepo;
    @Mock
    private FriendshipRepo friendshipRepo;
    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private ReplyToFriendshipRequestUseCaseImpl replyToFriendshipRequestUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void whenRejectingFriendshipRequest_thenDoNotCreateFriendship() throws Exception {
        // Arrange
        long requestId = 1L;
        long authenticatedUserId = 2L;
        ReplyToFriendshipRequestRequest rejectRequest = new ReplyToFriendshipRequestRequest(requestId, false);
        FriendshipRequestEntity foundRequest = createMockFriendshipRequest(requestId, authenticatedUserId);

        when(friendshipRequestsRepo.findById(requestId)).thenReturn(Optional.of(foundRequest));
        when(authenticationService.getAuthenticatedUserId()).thenReturn(authenticatedUserId);

        // Act
        replyToFriendshipRequestUseCase.reply(rejectRequest);

        // Assert
        verify(friendshipRequestsRepo).delete(foundRequest);
        verify(friendshipRepo, never()).save(any(FriendshipEntity.class));
    }

    @Test
    public void whenFriendshipRequestDoesNotExist_thenThrowException() {
        // Arrange
        long requestId = 1L;
        when(friendshipRequestsRepo.findById(requestId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FriendshipRequestDoesNotExistException.class, () -> replyToFriendshipRequestUseCase.reply(new ReplyToFriendshipRequestRequest(requestId, true)));
    }

    @Test
    public void whenUserIsUnauthorized_thenThrowBadCredentialsException() {
        // Arrange
        long requestId = 1L;
        long authenticatedUserId = 2L;
        FriendshipRequestEntity foundRequest = createMockFriendshipRequest(requestId, authenticatedUserId + 1); // Different user ID

        when(friendshipRequestsRepo.findById(requestId)).thenReturn(Optional.of(foundRequest));
        when(authenticationService.getAuthenticatedUserId()).thenReturn(authenticatedUserId);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> replyToFriendshipRequestUseCase.reply(new ReplyToFriendshipRequestRequest(requestId, true)));
    }

    @Test
    public void whenFriendshipAlreadyExists_thenThrowException() {
        // Arrange
        long requestId = 1L;
        long authenticatedUserId = 2L;
        FriendshipRequestEntity foundRequest = createMockFriendshipRequest(requestId, authenticatedUserId);

        when(friendshipRequestsRepo.findById(requestId)).thenReturn(Optional.of(foundRequest));
        when(authenticationService.getAuthenticatedUserId()).thenReturn(authenticatedUserId);
        when(friendshipRepo.existsByUsers(foundRequest.getRequested(), foundRequest.getRequester())).thenReturn(true);

        // Act & Assert
        assertThrows(FriendshipAlreadyExistsException.class, () -> replyToFriendshipRequestUseCase.reply(new ReplyToFriendshipRequestRequest(requestId, true)));
    }

    // Helper method to create a mock FriendshipRequestEntity
    private FriendshipRequestEntity createMockFriendshipRequest(long requestId, long authenticatedUserId) {
        UserEntity requester = mock(UserEntity.class);
        UserEntity requested = mock(UserEntity.class);

        // Stub the getId() method to return a specific value for both requester and requested
        when(requester.getId()).thenReturn(1L); // Example ID for requester
        when(requested.getId()).thenReturn(authenticatedUserId); // ID for authenticated user

        // Create the mock FriendshipRequestEntity
        FriendshipRequestEntity request = mock(FriendshipRequestEntity.class);
        when(request.getId()).thenReturn(requestId);
        when(request.getRequester()).thenReturn(requester);
        when(request.getRequested()).thenReturn(requested);

        return request;
    }

}
