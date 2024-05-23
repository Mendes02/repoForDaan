package fontys.sem3.school.business.impl.ForumPostLogicTests;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fontys.sem3.school.business.ForumPostLogic.Impl.GetForumPostUseCaseImpl;
import fontys.sem3.school.business.impl.converters.ForumPostConverter;
import fontys.sem3.school.domain.ForumPost;
import fontys.sem3.school.persistence.ForumPostRepo;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class GetForumPostUseCaseImplTest {

    @InjectMocks
    private GetForumPostUseCaseImpl useCase;

    @Mock
    private ForumPostRepo repo;

    @Mock
    private ForumPostConverter converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getForumPost_WhenForumPostExists() {
        // Arrange
        long postId = 1L;

        ForumPostEntity postEntity = createForumPostEntity(postId, "Sample Post");
        when(repo.findById(postId)).thenReturn(Optional.of(postEntity));

        ForumPost expectedPost = createForumPost(postId, "Sample Post");
        when(converter.convert(postEntity)).thenReturn(expectedPost);

        // Act
        Optional<ForumPost> result = useCase.getForumPost(postId);

        // Assert
        assertEquals(Optional.of(expectedPost), result);
    }

    @Test
    void getForumPost_WhenForumPostDoesNotExist() {
        // Arrange
        long postId = 1L;

        when(repo.findById(postId)).thenReturn(Optional.empty());

        // Act
        Optional<ForumPost> result = useCase.getForumPost(postId);

        // Assert
        assertEquals(Optional.empty(), result);
    }

    private ForumPostEntity createForumPostEntity(long id, String postContent) {
        ForumPostEntity postEntity = new ForumPostEntity();
        postEntity.setId(id);
        postEntity.setPostContent(postContent);
        return postEntity;
    }

    private ForumPost createForumPost(long id, String postContent) {
        return ForumPost.builder()
                .id(id)
                .postContent(postContent)
                .negativeRatings(0)
                .positiveRatings(0)
                .comments(0)
                .build();
    }
}

