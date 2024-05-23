package fontys.sem3.school.business.impl.ForumPostRatingTests;
import fontys.sem3.school.business.ForumPostRatingLogic.Impl.CreateForumPostRatingUseCaseImpl;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.domain.CreateForumPostRatingRequest;
import fontys.sem3.school.persistence.ForumPostRatingRepo;
import fontys.sem3.school.persistence.ForumPostRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import fontys.sem3.school.persistence.entity.ForumPostRatingEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CreateForumPostRatingUseCaseImplTest {

    @Mock
    private UserRepoFr userRepo;

    @Mock
    private ForumPostRepo forumPostRepo;

    @Mock
    private ForumPostRatingRepo forumPostRatingRepo;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private CreateForumPostRatingUseCaseImpl createForumPostRatingUseCase;

    private UserEntity user;
    private ForumPostEntity post;
    private ForumPostRatingEntity rating;

    @BeforeEach
    void setUp() {
        user = new UserEntity("test@example.com", "testuser", "USER", "password123");
        user.setId(1L);

        post = new ForumPostEntity();
        post.setId(1L);

        rating = new ForumPostRatingEntity(user, true, post);
    }

    @Test
    public void whenAuthenticatedUserIsNotRater_thenRatingIsNotCreated() {
        Long authenticatedUserId = 2L; // Different from the rater's ID

        when(authenticationService.getAuthenticatedUserId()).thenReturn(authenticatedUserId);
        CreateForumPostRatingRequest request = new CreateForumPostRatingRequest(post.getId(),true, user.getId());

        boolean result = createForumPostRatingUseCase.createForumPostRating(request);

        assertFalse(result);
        verify(forumPostRatingRepo, never()).save(any(ForumPostRatingEntity.class));
    }

    @Test
    public void whenUserNotFound_thenRatingIsNotCreated() {
        when(authenticationService.getAuthenticatedUserId()).thenReturn(user.getId());
        when(userRepo.findById(user.getId())).thenReturn(Optional.empty());

        CreateForumPostRatingRequest request = new CreateForumPostRatingRequest(post.getId(),true, user.getId());

        boolean result = createForumPostRatingUseCase.createForumPostRating(request);

        assertFalse(result);
        verify(forumPostRatingRepo, never()).save(any(ForumPostRatingEntity.class));
    }

    @Test
    public void whenPostNotFound_thenRatingIsNotCreated() {
        when(authenticationService.getAuthenticatedUserId()).thenReturn(user.getId());
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        when(forumPostRepo.findById(post.getId())).thenReturn(Optional.empty());

        CreateForumPostRatingRequest request = new CreateForumPostRatingRequest(post.getId(),true, user.getId());

        boolean result = createForumPostRatingUseCase.createForumPostRating(request);

        assertFalse(result);
        verify(forumPostRatingRepo, never()).save(any(ForumPostRatingEntity.class));
    }

    @Test
    public void whenRatingExistsWithSameValue_thenRatingIsDeleted() {
        when(authenticationService.getAuthenticatedUserId()).thenReturn(user.getId());
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        when(forumPostRepo.findById(post.getId())).thenReturn(Optional.of(post));
        when(forumPostRatingRepo.findByRatedByAndForumPost(user, post)).thenReturn(Optional.of(rating));

        CreateForumPostRatingRequest request = new CreateForumPostRatingRequest(post.getId(),true, user.getId());

        boolean result = createForumPostRatingUseCase.createForumPostRating(request);

        assertTrue(result);
        verify(forumPostRatingRepo).delete(rating);
    }

    @Test
    public void whenRatingExistsWithDifferentValue_thenRatingIsUpdated() {
        rating.setRateValue(false);
        when(authenticationService.getAuthenticatedUserId()).thenReturn(user.getId());
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        when(forumPostRepo.findById(post.getId())).thenReturn(Optional.of(post));
        when(forumPostRatingRepo.findByRatedByAndForumPost(user, post)).thenReturn(Optional.of(rating));

        CreateForumPostRatingRequest request = new CreateForumPostRatingRequest(post.getId(),true, user.getId() );

        boolean result = createForumPostRatingUseCase.createForumPostRating(request);

        assertTrue(result);
        verify(forumPostRatingRepo).save(rating);
        assertTrue(rating.getRatedValue());
    }

    @Test
    public void whenNewRatingIsCreated_thenSuccess() {
        when(authenticationService.getAuthenticatedUserId()).thenReturn(user.getId());
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        when(forumPostRepo.findById(post.getId())).thenReturn(Optional.of(post));
        when(forumPostRatingRepo.findByRatedByAndForumPost(user, post)).thenReturn(Optional.empty());

        CreateForumPostRatingRequest request = new CreateForumPostRatingRequest(post.getId(),true, user.getId());

        boolean result = createForumPostRatingUseCase.createForumPostRating(request);

        assertTrue(result);
        verify(forumPostRatingRepo).save(any(ForumPostRatingEntity.class));
    }
}
