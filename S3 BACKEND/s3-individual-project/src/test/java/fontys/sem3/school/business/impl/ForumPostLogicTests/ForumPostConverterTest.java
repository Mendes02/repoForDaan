package fontys.sem3.school.business.impl.ForumPostLogicTests;


import fontys.sem3.school.business.impl.converters.ForumPostConverter;
import fontys.sem3.school.domain.ForumPost;
import fontys.sem3.school.persistence.ForumPostCommentRepo;
import fontys.sem3.school.persistence.ForumPostRatingRepo;
import fontys.sem3.school.persistence.ForumRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.ForumEntity;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class ForumPostConverterTest {

    @InjectMocks
    private ForumPostConverter forumPostConverter;

    @Mock
    private ForumPostRatingRepo ratingRepo;

    @Mock
    private ForumPostCommentRepo commentRepo;

    @Mock
    private ForumRepo forumRepo;

    @Mock
    private UserRepoFr userRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void convertList_WhenListIsValid() {
        // Arrange
        List<ForumPostEntity> entityList = new ArrayList<>();
        ForumPostEntity entity1 = createForumPostEntity(1L, "Post 1");
        ForumPostEntity entity2 = createForumPostEntity(2L, "Post 2");
        entityList.add(entity1);
        entityList.add(entity2);

        when(ratingRepo.countAllByForumPostAndRateValue(entity1, false)).thenReturn(5);
        when(ratingRepo.countAllByForumPostAndRateValue(entity1, true)).thenReturn(10);
        when(commentRepo.countAllByForumPost(entity1)).thenReturn(3);

        when(ratingRepo.countAllByForumPostAndRateValue(entity2, false)).thenReturn(2);
        when(ratingRepo.countAllByForumPostAndRateValue(entity2, true)).thenReturn(8);
        when(commentRepo.countAllByForumPost(entity2)).thenReturn(4);

        // Act
        ArrayList<ForumPost> result = forumPostConverter.convertList(entityList);

        // Assert
        assertEquals(2, result.size());

        ForumPost post1 = result.get(0);
        assertEquals(1L, post1.getId());
        assertEquals("Post 1", post1.getPostContent());
        assertEquals(5L, post1.getNegativeRatings());
        assertEquals(10L, post1.getPositiveRatings());
        assertEquals(3L, post1.getComments());

        ForumPost post2 = result.get(1);
        assertEquals(2L, post2.getId());
        assertEquals("Post 2", post2.getPostContent());
        assertEquals(2L, post2.getNegativeRatings());
        assertEquals(8L, post2.getPositiveRatings());
        assertEquals(4L, post2.getComments());
    }

    private ForumPostEntity createForumPostEntity(Long id, String postContent) {
        ForumPostEntity entity = new ForumPostEntity();
        entity.setId(id);
        entity.setForum(new ForumEntity());
        entity.setPostedBy(new UserEntity());
        entity.setPostContent(postContent);
        entity.setCreationDate(LocalDate.now());
        return entity;
    }
}
