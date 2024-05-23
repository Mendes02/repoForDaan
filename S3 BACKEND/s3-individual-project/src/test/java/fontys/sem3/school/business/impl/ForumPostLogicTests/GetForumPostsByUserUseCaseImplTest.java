package fontys.sem3.school.business.impl.ForumPostLogicTests;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fontys.sem3.school.business.ForumPostLogic.Impl.GetForumPostsByUserUseCaseImpl;
import fontys.sem3.school.business.impl.converters.ForumPostConverter;
import fontys.sem3.school.business.impl.converters.UserConverter;
import fontys.sem3.school.domain.ForumPost;
import fontys.sem3.school.persistence.ForumPostRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

class GetForumPostsByUserUseCaseImplTest {

    @InjectMocks
    private GetForumPostsByUserUseCaseImpl useCase;

    @Mock
    private ForumPostRepo forumPostRepo;

    @Mock
    private UserRepoFr userRepo;

    @Mock
    private ForumPostConverter converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getForumPostsByUser_WhenUserExists() {
        // Arrange
        long userId = 1L;

        UserEntity userEntity = createUserEntity(userId, "John Doe");
        when(userRepo.findById(userId)).thenReturn(Optional.of(userEntity));

        List<ForumPostEntity> postEntities = new ArrayList<>();
        ForumPostEntity post1 = createForumPostEntity(1L, userEntity, "Post 1");
        ForumPostEntity post2 = createForumPostEntity(2L, userEntity, "Post 2");
        postEntities.add(post1);
        postEntities.add(post2);

        when(forumPostRepo.findByPostedBy(userEntity)).thenReturn(postEntities);

        ArrayList<ForumPost> expectedPosts = new ArrayList<>();
        expectedPosts.add(createForumPost(1L, "Post 1"));
        expectedPosts.add(createForumPost(2L, "Post 2"));

        when(converter.convertList(postEntities)).thenReturn(expectedPosts);

        // Act
        ArrayList<ForumPost> result = useCase.getForumPostsByUser(userId);

        // Assert
        assertEquals(expectedPosts, result);
    }




    private UserEntity createUserEntity(long id, String name) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setUsername(name);
        return userEntity;
    }

    private ForumPostEntity createForumPostEntity(long id, UserEntity userEntity, String postContent) {
        ForumPostEntity postEntity = new ForumPostEntity();
        postEntity.setId(id);
        postEntity.setPostedBy(userEntity);
        postEntity.setPostContent(postContent);
        return postEntity;
    }

    private ForumPost createForumPost(long id, String postContent) {
        return ForumPost.builder()
                .id(id)
                .user(UserConverter.convert(new UserEntity()))
                .postContent(postContent)
                .negativeRatings(0)
                .positiveRatings(0)
                .comments(0)
                .build();
    }
}
