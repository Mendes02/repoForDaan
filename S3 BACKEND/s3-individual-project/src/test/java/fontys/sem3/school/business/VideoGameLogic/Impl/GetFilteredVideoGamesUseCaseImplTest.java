package fontys.sem3.school.business.VideoGameLogic.Impl;

import fontys.sem3.school.domain.VideoGame;
import fontys.sem3.school.persistence.VideoGameRepo;
import fontys.sem3.school.persistence.entity.VideoGameEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GetFilteredVideoGamesUseCaseImplTest {

    @Mock
    private VideoGameRepo videoGameRepo;

    private GetFilteredVideoGamesUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new GetFilteredVideoGamesUseCaseImpl(videoGameRepo);
    }

    @Test
    void testGetFilteredVideoGamesWithGenreAndName() {
        // Arrange
        String genre = "Action";
        String name = "Game";
        List<VideoGameEntity> videoGameEntities = new ArrayList<>();
        videoGameEntities.add(new VideoGameEntity(1L, "Publisher","Game1", LocalDate.now(),"Action"));
        videoGameEntities.add(new VideoGameEntity(2L, "Publisher","Game2", LocalDate.now(),"Adventure"));

        when(videoGameRepo.findByNameContainingAndGenre(name, genre)).thenReturn(videoGameEntities);

        // Act
        ArrayList<VideoGame> result = useCase.getFilteredVideoGames(genre, name);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Game1", result.get(0).getName());
        assertEquals("Action", result.get(0).getGenre());
        assertEquals("Game2", result.get(1).getName());
        assertEquals("Adventure", result.get(1).getGenre());
    }
    @Test
    void testGetFilteredVideoGamesWithName() {
        // Arrange
        String name = "Game";
        List<VideoGameEntity> videoGameEntities = new ArrayList<>();
        videoGameEntities.add(new VideoGameEntity(1L, "Publisher", "Game1", LocalDate.now(), "Action"));
        videoGameEntities.add(new VideoGameEntity(2L, "Publisher", "Game2", LocalDate.now(), "Adventure"));

        when(videoGameRepo.findByNameContaining(name)).thenReturn(videoGameEntities);

        // Act
        ArrayList<VideoGame> result = useCase.getFilteredVideoGames("null", name);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Game1", result.get(0).getName());
        assertEquals("Action", result.get(0).getGenre());
        assertEquals("Game2", result.get(1).getName());
        assertEquals("Adventure", result.get(1).getGenre());
    }

    @Test
    void testGetFilteredVideoGamesWithGenre() {
        // Arrange
        String genre = "Action";
        List<VideoGameEntity> videoGameEntities = new ArrayList<>();
        videoGameEntities.add(new VideoGameEntity(1L, "Publisher", "Game1", LocalDate.now(), "Action"));
        videoGameEntities.add(new VideoGameEntity(2L, "Publisher", "Game2", LocalDate.now(), "Adventure"));

        when(videoGameRepo.findByGenre(genre)).thenReturn(videoGameEntities);

        // Act
        ArrayList<VideoGame> result = useCase.getFilteredVideoGames(genre, "null");

        // Assert
        assertEquals(2, result.size());
        assertEquals("Game1", result.get(0).getName());
        assertEquals("Action", result.get(0).getGenre());
        assertEquals("Game2", result.get(1).getName());
        assertEquals("Adventure", result.get(1).getGenre());
    }

    @Test
    void testGetFilteredVideoGamesWithNullGenreAndName() {
        // Arrange
        List<VideoGameEntity> videoGameEntities = new ArrayList<>();
        videoGameEntities.add(new VideoGameEntity(1L, "Publisher", "Game1", LocalDate.now(), "Action"));
        videoGameEntities.add(new VideoGameEntity(2L, "Publisher", "Game2", LocalDate.now(), "Adventure"));

        when(videoGameRepo.findAll()).thenReturn(videoGameEntities);

        // Act
        ArrayList<VideoGame> result = useCase.getFilteredVideoGames("null", "null");

        // Assert
        assertEquals(2, result.size());
        assertEquals("Game1", result.get(0).getName());
        assertEquals("Action", result.get(0).getGenre());
        assertEquals("Game2", result.get(1).getName());
        assertEquals("Adventure", result.get(1).getGenre());
    }


    // Additional test cases for other scenarios can be added similarly
}
