package fontys.sem3.school.business.impl.VideoGameLogicTests;
import static org.junit.jupiter.api.Assertions.*;

import fontys.sem3.school.business.VideoGameLogic.Impl.GetAllVideoGamesUseCaseImpl;
import fontys.sem3.school.domain.VideoGame;
import fontys.sem3.school.persistence.VideoGameRepo;
import fontys.sem3.school.persistence.entity.VideoGameEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
public class GetAllVideoGamesUseCaseImplTest {

    @Test
    public void getAllVideogames_SuccessfulRetrieval() {
        // Mock the dependencies
        VideoGameRepo mockedRepo = Mockito.mock(VideoGameRepo.class);
        VideoGameEntity mockedEntity1 = Mockito.mock(VideoGameEntity.class); // Assuming VideoGameEntity is your entity class
        VideoGameEntity mockedEntity2 = Mockito.mock(VideoGameEntity.class);

        // Prepare the data to be returned by the repo
        List<VideoGameEntity> videoGameEntities = new ArrayList<>();
        videoGameEntities.add(mockedEntity1);
        videoGameEntities.add(mockedEntity2);

        Mockito.when(mockedRepo.findAll()).thenReturn(videoGameEntities);

        // Create an instance of the use case implementation
        GetAllVideoGamesUseCaseImpl useCase = new GetAllVideoGamesUseCaseImpl(mockedRepo);

        // Call the method under test
        ArrayList<VideoGame> result = useCase.getAllVideogames();

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        // Additional assertions based on expected behavior
    }
}
