package fontys.sem3.school.business.impl.ForumLogicTests;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fontys.sem3.school.business.ForumLogic.Impl.GetForumUseCaseImpl;
import fontys.sem3.school.persistence.ForumRepo;
import fontys.sem3.school.persistence.entity.ForumEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class GetForumUseCaseImplTest {

    @Mock
    private ForumRepo forumRepo;

    @InjectMocks
    private GetForumUseCaseImpl getForumUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenForumExists_thenForumShouldBeReturned() {
        long forumId = 1L;
        ForumEntity expectedForum = new ForumEntity();
        expectedForum.setId(forumId);

        when(forumRepo.findById(forumId)).thenReturn(Optional.of(expectedForum));

        Optional<ForumEntity> result = getForumUseCase.getForum(forumId);

        assertTrue(result.isPresent());
        assertEquals(expectedForum, result.get());
    }

    @Test
    public void whenForumDoesNotExist_thenEmptyOptionalShouldBeReturned() {
        long forumId = 2L;
        when(forumRepo.findById(forumId)).thenReturn(Optional.empty());

        Optional<ForumEntity> result = getForumUseCase.getForum(forumId);

        assertFalse(result.isPresent());
    }
}

