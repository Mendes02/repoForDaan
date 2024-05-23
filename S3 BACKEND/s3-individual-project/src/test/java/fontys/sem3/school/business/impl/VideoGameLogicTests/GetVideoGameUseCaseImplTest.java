package fontys.sem3.school.business.impl.VideoGameLogicTests;
import fontys.sem3.school.business.VideoGameLogic.Impl.GetVideoGameUseCaseImpl;
import fontys.sem3.school.domain.VideoGame;
import fontys.sem3.school.persistence.VideoGameRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class GetVideoGameUseCaseImplTest {

    @Mock
    private VideoGameRepo videoGameRepo;

    @InjectMocks
    private GetVideoGameUseCaseImpl getVideoGameUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void getVideoGame_WhenGameDoesNotExist() {
        // Arrange
        long videoGameId = 1L;
        when(videoGameRepo.findById(videoGameId)).thenReturn(Optional.empty());

        // Act
        Optional<VideoGame> result = getVideoGameUseCase.getVideoGame(videoGameId);

        // Assert
        assertFalse(result.isPresent());
    }
}
