package fontys.sem3.school.business.impl.converters;


import fontys.sem3.school.domain.ForumPost;
import fontys.sem3.school.domain.ForumPostRating;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import fontys.sem3.school.persistence.entity.ForumPostRatingEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ForumPostRatingConvertersTest {

    @Mock
    private ForumPostConverter mockConverter;
    @Mock
    private ForumPostRatingEntity mockEntity;
    @Mock
    private ForumPost mockForumPost;

    private ForumPostRatingConverters converters;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        converters = new ForumPostRatingConverters(mockConverter);

        when(mockEntity.getId()).thenReturn(1L);
        when(mockEntity.getRatedBy()).thenReturn(new UserEntity()); // Assuming existence of UserEntity
        when(mockEntity.getRatedValue()).thenReturn(true);
        when(mockEntity.getForumPost()).thenReturn(new ForumPostEntity()); // Assuming existence of ForumPostEntity
        when(mockConverter.convert(any(ForumPostEntity.class))).thenReturn(mockForumPost);
    }

    @Test
    public void convert_ShouldConvertEntityToDto() {
        ForumPostRating result = converters.convert(mockEntity);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(true, result.getRatedValue());
        assertEquals(mockForumPost, result.getForumPost());
        // Additional assertions can be added for other fields
    }

    @Test
    public void convertList_ShouldConvertListOfEntitiesToListOfDtos() {
        List<ForumPostRatingEntity> entityList = new ArrayList<>();
        entityList.add(mockEntity);
        entityList.add(mockEntity);

        List<ForumPostRating> resultList = converters.convertList(entityList);

        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        for (ForumPostRating dto : resultList) {
            assertNotNull(dto);
            // Additional assertions as in convert_ShouldConvertEntityToDto
        }
    }
}
