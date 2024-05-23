package fontys.sem3.school.business.ForumPostCommentLogic.Impl;

import fontys.sem3.school.business.exception.ForumPostDoesNotExistException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.domain.CreateForumPostCommentRequest;
import fontys.sem3.school.persistence.ForumPostCommentRepo;
import fontys.sem3.school.persistence.ForumPostRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.ForumPostCommentEntity;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CreateForumPostCommentUseCaseImplTest {

    @Mock
    private UserRepoFr userRepo;

    @Mock
    private ForumPostRepo forumPostRepo;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private ForumPostCommentRepo forumPostCommentRepo;

    private CreateForumPostCommentUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new CreateForumPostCommentUseCaseImpl(userRepo, forumPostRepo, authenticationService, forumPostCommentRepo);
    }

    @Test
    void testCreateCommentSuccessful() throws UserDoesNotExistException, ForumPostDoesNotExistException {
        // Arrange
        CreateForumPostCommentRequest request = new CreateForumPostCommentRequest(1L, "Comment content");
        UserEntity userEntity = new UserEntity(1L,"user@example.com", "username", "role", "password");
        ForumPostEntity forumPostEntity = new ForumPostEntity();

        when(authenticationService.getAuthenticatedUserId()).thenReturn(1L);
        when(userRepo.findById(authenticationService.getAuthenticatedUserId())).thenReturn(Optional.of(userEntity));
        when(forumPostRepo.findById(1L)).thenReturn(Optional.of(forumPostEntity));

        // Act
        useCase.createComment(request);

        // Assert
        verify(forumPostCommentRepo).save(any(ForumPostCommentEntity.class));
    }

    @Test
    void testCreateCommentUserDoesNotExist() {
        // Arrange
        CreateForumPostCommentRequest request = new CreateForumPostCommentRequest(1L, "Comment content");

        when(authenticationService.getAuthenticatedUserId()).thenReturn(1L);
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserDoesNotExistException.class, () -> useCase.createComment(request));
        verify(forumPostCommentRepo, never()).save(any(ForumPostCommentEntity.class));
    }

    @Test
    void testCreateCommentForumPostDoesNotExist() {
        // Arrange
        CreateForumPostCommentRequest request = new CreateForumPostCommentRequest(1L, "Comment content");

        when(authenticationService.getAuthenticatedUserId()).thenReturn(1L);
        when(userRepo.findById(authenticationService.getAuthenticatedUserId())).thenReturn(Optional.of(new UserEntity()));
        when(forumPostRepo.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ForumPostDoesNotExistException.class, () -> useCase.createComment(request));
        verify(forumPostCommentRepo, never()).save(any(ForumPostCommentEntity.class));
    }
}
