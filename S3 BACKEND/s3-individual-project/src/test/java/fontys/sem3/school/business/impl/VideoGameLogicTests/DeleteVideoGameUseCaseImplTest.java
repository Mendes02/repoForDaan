package fontys.sem3.school.business.impl.VideoGameLogicTests;
import static org.mockito.Mockito.*;

import fontys.sem3.school.business.VideoGameLogic.Impl.DeleteVideoGameUseCaseImpl;
import fontys.sem3.school.persistence.VideoGameRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
public class DeleteVideoGameUseCaseImplTest {

    @Test
    public void deleteVideoGame_CallsDeleteById() {
        // Mock the repository
        VideoGameRepo mockedRepo = Mockito.mock(VideoGameRepo.class);

        // Create an instance of the use case implementation
        DeleteVideoGameUseCaseImpl useCase = new DeleteVideoGameUseCaseImpl(mockedRepo);

        // Define the ID to delete
        long videoGameId = 1L;

        // Call the method under test
        useCase.deleteVideoGame(videoGameId);

        // Verify that deleteById was called with the correct ID
        verify(mockedRepo, times(1)).deleteById(videoGameId);
    }
}

