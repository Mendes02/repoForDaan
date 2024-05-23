package fontys.sem3.school.business.ForumPostCommentLogic.Impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import fontys.sem3.school.business.exception.ForumPostDoesNotExistException;
import fontys.sem3.school.business.impl.converters.ForumPostCommentConverter;
import fontys.sem3.school.domain.ForumPostComment;
import fontys.sem3.school.persistence.ForumPostCommentRepo;
import fontys.sem3.school.persistence.ForumPostRepo;
import fontys.sem3.school.persistence.entity.ForumPostCommentEntity;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GetCommentsFromPostUseCaseImplTest {

    @Mock
    private ForumPostCommentConverter converter;
    @Mock
    private ForumPostRepo forumPostRepo;
    @Mock
    private ForumPostCommentRepo forumPostCommentRepo;

    @InjectMocks
    private GetCommentsFromPostUseCaseImpl getCommentsFromPostUseCase;

    private final long postId = 1L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenValidPostId_thenRetrieveComments() throws ForumPostDoesNotExistException {
        // Arrange
        ForumPostEntity foundPost = mock(ForumPostEntity.class);
        List<ForumPostCommentEntity> commentEntities = List.of(mock(ForumPostCommentEntity.class));
        List<ForumPostComment> comments = List.of(mock(ForumPostComment.class));

        when(forumPostRepo.findById(postId)).thenReturn(Optional.of(foundPost));
        when(forumPostCommentRepo.findAllByForumPost(foundPost)).thenReturn(commentEntities);
        when(converter.convertList(commentEntities)).thenReturn(comments);

        // Act
        List<ForumPostComment> result = getCommentsFromPostUseCase.getComments(postId);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(comments, result);
    }

    @Test
    public void whenPostDoesNotExist_thenThrowException() {
        // Arrange
        when(forumPostRepo.findById(postId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ForumPostDoesNotExistException.class, () -> getCommentsFromPostUseCase.getComments(postId));
    }

    @Test
    public void whenNoComments_thenReturnEmptyList() throws ForumPostDoesNotExistException {
        // Arrange
        ForumPostEntity foundPost = mock(ForumPostEntity.class);
        List<ForumPostCommentEntity> commentEntities = Collections.emptyList();
        List<ForumPostComment> comments = Collections.emptyList();

        when(forumPostRepo.findById(postId)).thenReturn(Optional.of(foundPost));
        when(forumPostCommentRepo.findAllByForumPost(foundPost)).thenReturn(commentEntities);
        when(converter.convertList(commentEntities)).thenReturn(comments);

        // Act
        List<ForumPostComment> result = getCommentsFromPostUseCase.getComments(postId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
