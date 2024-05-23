package fontys.sem3.school.business.impl.ForumPostLogicTests;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fontys.sem3.school.business.ForumPostLogic.Impl.DeleteForumPostUseCaseImpl;
import fontys.sem3.school.persistence.ForumPostRepo;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class DeleteForumPostUseCaseImplTest {

    @Mock
    private ForumPostRepo forumPostRepo;

    @InjectMocks
    private DeleteForumPostUseCaseImpl deleteForumPostUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenPostExists_thenPostShouldBeDeleted() {
        long postId = 1L;

        when(forumPostRepo.findById(postId)).thenReturn(Optional.of(new ForumPostEntity()));

        boolean result = deleteForumPostUseCase.deleteForumPost(postId);

        assertTrue(result);
        verify(forumPostRepo).deleteById(postId);
    }

    @Test
    public void whenPostDoesNotExist_thenDeletionShouldFail() {
        long postId = 2L;

        when(forumPostRepo.findById(postId)).thenReturn(Optional.empty());

        boolean result = deleteForumPostUseCase.deleteForumPost(postId);

        assertFalse(result);
        verify(forumPostRepo, never()).deleteById(postId);
    }
}

