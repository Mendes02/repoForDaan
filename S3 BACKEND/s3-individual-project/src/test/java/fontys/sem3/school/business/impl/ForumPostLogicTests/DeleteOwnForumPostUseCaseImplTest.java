package fontys.sem3.school.business.impl.ForumPostLogicTests;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fontys.sem3.school.business.ForumPostLogic.Impl.DeleteOwnForumPostUseCaseImpl;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.configuration.security.token.AccessToken;
import fontys.sem3.school.configuration.security.token.AccessTokenDecoder;
import fontys.sem3.school.domain.DeleteOwnForumPostRequest;
import fontys.sem3.school.persistence.ForumPostRepo;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

class DeleteOwnForumPostUseCaseImplTest {

    @Mock
    private ForumPostRepo forumPostRepo;
    @Mock
    private AccessTokenDecoder accessTokenDecoder;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private DeleteOwnForumPostUseCaseImpl deleteOwnForumPostUseCase;

    private final Long VALID_POST_ID = 1L;
    private final Long VALID_USER_ID = 2L;
    private final Long ADMIN_USER_ID = 3L;
    private final String ACCESS_TOKEN = "validToken";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteOwnForumPost_WhenPostDoesNotExist() {
        // Arrange
        DeleteOwnForumPostRequest request = new DeleteOwnForumPostRequest(ACCESS_TOKEN, VALID_POST_ID);
        when(forumPostRepo.findById(VALID_POST_ID)).thenReturn(Optional.empty());

        // Act
        boolean result = deleteOwnForumPostUseCase.deleteOwnForumPost(request);

        // Assert
        assertFalse(result);
    }

    @Test
    void deleteOwnForumPost_WhenNotOwnerNorAdmin() {
        // Arrange
        String accessToken = "validToken";
        long forumPostId = 1L;
        long userId = 2L; // User ID of the requester
        long differentUserId = 3L; // Different User ID (neither owner nor admin)

        DeleteOwnForumPostRequest request = new DeleteOwnForumPostRequest(accessToken, forumPostId);
        AccessToken token = mock(AccessToken.class); // Mocked AccessToken
        ForumPostEntity forumPostEntity = mock(ForumPostEntity.class); // Mocked ForumPostEntity
        UserEntity postedBy = mock(UserEntity.class); // Mocked UserEntity

        when(accessTokenDecoder.decode(accessToken)).thenReturn(token);
        when(token.getUserId()).thenReturn(userId);
        when(forumPostRepo.findById(forumPostId)).thenReturn(Optional.of(forumPostEntity));
        when(forumPostEntity.getPostedBy()).thenReturn(postedBy);
        when(postedBy.getId()).thenReturn(differentUserId);
        when(authenticationService.getAuthenticatedUserId()).thenReturn(userId);
        when(authenticationService.userIsAdmin()).thenReturn(false);

        // Act
        boolean result = deleteOwnForumPostUseCase.deleteOwnForumPost(request);

        // Assert
        assertFalse(result);
    }







    @Test
    void deleteOwnForumPost_WhenUserIsAdmin() {
        // Arrange
        String accessToken = "validToken";
        long forumPostId = 1L;
        long adminUserId = 2L;

        DeleteOwnForumPostRequest request = new DeleteOwnForumPostRequest(accessToken, forumPostId);
        AccessToken token = mock(AccessToken.class); // Make sure this is a mock.

        ForumPostEntity forumPostEntity = mock(ForumPostEntity.class); // Make sure this is a mock.
        UserEntity postedBy = mock(UserEntity.class); // Make sure this is a mock.

        when(accessTokenDecoder.decode(accessToken)).thenReturn(token);
        when(token.getUserId()).thenReturn(adminUserId);
        when(forumPostRepo.findById(forumPostId)).thenReturn(Optional.of(forumPostEntity));
        when(forumPostEntity.getPostedBy()).thenReturn(postedBy);
        when(postedBy.getId()).thenReturn(adminUserId);
        when(authenticationService.getAuthenticatedUserId()).thenReturn(adminUserId);
        when(authenticationService.userIsAdmin()).thenReturn(true);

        // Act
        boolean result = deleteOwnForumPostUseCase.deleteOwnForumPost(request);

        // Assert
        assertTrue(result);
        verify(forumPostRepo).deleteById(forumPostId);
    }
}

