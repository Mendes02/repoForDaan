package fontys.sem3.school.business.impl.converters;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fontys.sem3.school.domain.ForumPost;
import fontys.sem3.school.domain.ForumPostComment;
import fontys.sem3.school.persistence.entity.ForumPostCommentEntity;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ForumPostCommentConverterTest {

    @Mock
    private ForumPostConverter mockForumPostConverter;
    @Mock
    private ForumPostCommentEntity mockCommentEntity;
    @Mock
    private ForumPost mockForumPost;

    private ForumPostCommentConverter converter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        converter = new ForumPostCommentConverter(mockForumPostConverter);

        when(mockCommentEntity.getId()).thenReturn(1L);
        when(mockCommentEntity.getForumPost()).thenReturn(new ForumPostEntity()); // Assuming existence of ForumPostEntity
        when(mockForumPostConverter.convert(any(ForumPostEntity.class))).thenReturn(mockForumPost);
        when(mockCommentEntity.getSubmittedBy()).thenReturn(new UserEntity()); // Assuming existence of UserEntity
        when(mockCommentEntity.getCommentContent()).thenReturn("Test comment");
        // Mock other necessary methods from mockCommentEntity
    }

    @Test
    public void convert_ShouldConvertEntityToDto() {
        ForumPostComment result = converter.convert(mockCommentEntity);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test comment", result.getCommentContent());
        assertEquals(mockForumPost, result.getForumPost());
        // Additional assertions can be added for other fields
    }

    @Test
    public void convertList_ShouldConvertListOfEntitiesToListOfDtos() {
        List<ForumPostCommentEntity> entityList = new ArrayList<>();
        entityList.add(mockCommentEntity);
        entityList.add(mockCommentEntity);

        List<ForumPostComment> resultList = converter.convertList(entityList);

        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        resultList.forEach(dto -> {
            assertNotNull(dto);
            // Additional assertions as in convert_ShouldConvertEntityToDto
        });
    }
}
