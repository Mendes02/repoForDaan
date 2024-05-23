package fontys.sem3.school.business.impl.VideoGameLogicTests;
import fontys.sem3.school.business.impl.converters.VideoGameConverter;
import fontys.sem3.school.domain.VideoGame;
import fontys.sem3.school.persistence.entity.VideoGameEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

class VideoGameConverterTest {


    @Test
    void convert_WhenEntityIsNotNull() {
        // Arrange
        VideoGameEntity entity = new VideoGameEntity("Nintendo", "Zelda", LocalDate.of(1986, 2, 21), "Adventure");
        entity.setId(1L);

        // Act
        VideoGame videoGame = VideoGameConverter.convert(entity);

        // Assert
        assertNotNull(videoGame);
        assertEquals(entity.getId(), videoGame.getId());
        assertEquals(entity.getPublisher(), videoGame.getPublisher());
        assertEquals(entity.getName(), videoGame.getName());
        assertEquals(entity.getReleased(), videoGame.getReleased());
        assertEquals(entity.getGenre(), videoGame.getGenre());
    }

    @Test
    void convertList_WhenListIsNotEmpty() {
        // Arrange
        VideoGameEntity entity1 = new VideoGameEntity("Nintendo", "Zelda", LocalDate.of(1986, 2, 21), "Adventure");
        entity1.setId(1L);
        VideoGameEntity entity2 = new VideoGameEntity("Capcom", "Mega Man", LocalDate.of(1987, 12, 17), "Action");
        entity2.setId(2L);
        List<VideoGameEntity> entityList = Arrays.asList(entity1, entity2);

        // Act
        ArrayList<VideoGame> videoGameList = VideoGameConverter.convertList(entityList);

        // Assert
        assertEquals(2, videoGameList.size());
        // Assert the first element
        assertEquals(entityList.get(0).getId(), videoGameList.get(0).getId());
        assertEquals(entityList.get(0).getPublisher(), videoGameList.get(0).getPublisher());
        // Assert the second element
        assertEquals(entityList.get(1).getId(), videoGameList.get(1).getId());
        assertEquals(entityList.get(1).getPublisher(), videoGameList.get(1).getPublisher());
    }

    @Test
    void convertList_WhenListIsEmpty() {
        // Arrange
        List<VideoGameEntity> entityList = new ArrayList<>();

        // Act
        ArrayList<VideoGame> videoGameList = VideoGameConverter.convertList(entityList);

        // Assert
        assertTrue(videoGameList.isEmpty());
    }
}

