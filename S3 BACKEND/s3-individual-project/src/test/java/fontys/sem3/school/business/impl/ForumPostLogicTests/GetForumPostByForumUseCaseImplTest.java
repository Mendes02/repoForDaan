package fontys.sem3.school.business.impl.ForumPostLogicTests;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fontys.sem3.school.business.ForumPostLogic.Impl.GetForumPostByForumUseCaseImpl;
import fontys.sem3.school.business.impl.converters.ForumPostConverter;
import fontys.sem3.school.business.impl.converters.UserConverter;
import fontys.sem3.school.domain.ForumPost;
import fontys.sem3.school.persistence.ForumPostRepo;
import fontys.sem3.school.persistence.ForumRepo;
import fontys.sem3.school.persistence.entity.ForumEntity;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

class GetForumPostByForumUseCaseImplTest {

    @InjectMocks
    private GetForumPostByForumUseCaseImpl useCase;

    @Mock
    private ForumPostRepo repo;

    @Mock
    private ForumRepo forumRepo;

    @Mock
    private ForumPostConverter converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getForumPostsByForum_WhenForumExists() {
        // Arrange
        long forumId = 1L;

        ForumEntity forumEntity = createForumEntity(forumId, "Sample Forum");
        when(forumRepo.findById(forumId)).thenReturn(Optional.of(forumEntity));

        List<ForumPostEntity> postEntities = new ArrayList<>();
        ForumPostEntity post1 = createForumPostEntity(1L, forumEntity, "Post 1");
        ForumPostEntity post2 = createForumPostEntity(2L, forumEntity, "Post 2");
        postEntities.add(post1);
        postEntities.add(post2);

        when(repo.findByForum(forumEntity)).thenReturn(postEntities);

        ArrayList<ForumPost> expectedPosts = new ArrayList<>();
        expectedPosts.add(createForumPost(1L, "Post 1"));
        expectedPosts.add(createForumPost(2L, "Post 2"));

        when(converter.convertList(postEntities)).thenReturn(expectedPosts);

        // Act
        ArrayList<ForumPost> result = useCase.getForumPostsByForum(forumId);

        // Assert
        assertEquals(expectedPosts, result);
    }



    private ForumEntity createForumEntity(long id, String name) {
        ForumEntity forumEntity = new ForumEntity();
        forumEntity.setId(id);// Set the name for the forum entity
        return forumEntity;
    }

    private ForumPostEntity createForumPostEntity(long id, ForumEntity forumEntity, String postContent) {
        ForumPostEntity postEntity = new ForumPostEntity();
        postEntity.setId(id);
        postEntity.setForum(forumEntity);
        postEntity.setPostedBy(new UserEntity());
        postEntity.setPostContent(postContent);
        postEntity.setCreationDate(LocalDate.now());
        return postEntity;
    }

    private ForumPost createForumPost(long id, String postContent) {
        return ForumPost.builder()
                .id(id)
                .forum(new ForumEntity())
                .user(UserConverter.convert(new UserEntity()))
                .postContent(postContent)
                .negativeRatings(0)
                .positiveRatings(0)
                .comments(0)
                .creationDate(LocalDate.now())
                .build();
    }
}

