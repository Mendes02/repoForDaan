package fontys.sem3.school.business.impl.ForumLogicTests;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import fontys.sem3.school.business.ForumLogic.Impl.GetTopForumsUseCaseImpl;
import fontys.sem3.school.persistence.ForumRepo;
import fontys.sem3.school.persistence.entity.ForumEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class GetTopForumsUseCaseImplTest {

    @Mock
    private ForumRepo forumRepo;

    @InjectMocks
    private GetTopForumsUseCaseImpl getTopForumsUseCase;

    @Test
    public void whenGetTopForumsCalled_verifyRepoFindAll() {
        // Arrange
        ForumEntity forum1 = new ForumEntity(/* set properties */);
        ForumEntity forum2 = new ForumEntity(/* set properties */);
        List<ForumEntity> mockForums = Arrays.asList(forum1, forum2);
        when(forumRepo.findAll()).thenReturn(mockForums);

        // Act
        List<ForumEntity> result = getTopForumsUseCase.getTopForums();

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(mockForums.size(), result.size(), "The size of results should match the mock");
        verify(forumRepo, times(1)).findAll();
    }
}

