package fontys.sem3.school.business.impl.ForumPostLogicTests;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import fontys.sem3.school.business.ForumPostLogic.Impl.CreateForumPostUseCaseImpl;
import fontys.sem3.school.domain.CreateForumPostRequest;
import fontys.sem3.school.persistence.ForumPostRepo;
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

import java.util.Optional;

public class CreateForumPostUseCaseImplTest {

    @Mock
    private UserRepoFr userRepo;
    @Mock
    private ForumRepo forumRepo;
    @Mock
    private ForumPostRepo forumPostRepo;

    @InjectMocks
    private CreateForumPostUseCaseImpl createForumPostUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenUserAndForumExist_thenPostShouldBeCreated() {
        CreateForumPostRequest request = new CreateForumPostRequest();
        UserEntity userEntity = new UserEntity();
        ForumEntity forumEntity = new ForumEntity();

        when(userRepo.findById(request.getSubmittedById())).thenReturn(Optional.of(userEntity));
        when(forumRepo.findById(request.getForumId())).thenReturn(Optional.of(forumEntity));

        boolean result = createForumPostUseCase.createForumPost(request);

        assertTrue(result);
        verify(forumPostRepo).save(any(ForumPostEntity.class));
    }

    @Test
    public void whenUserDoesNotExist_thenPostShouldNotBeCreated() {
        CreateForumPostRequest request = new CreateForumPostRequest(/* parameters */);

        when(userRepo.findById(request.getSubmittedById())).thenReturn(Optional.empty());

        boolean result = createForumPostUseCase.createForumPost(request);

        assertFalse(result);
        verify(forumPostRepo, never()).save(any(ForumPostEntity.class));
    }

    @Test
    public void whenForumDoesNotExist_thenPostShouldNotBeCreated() {
        CreateForumPostRequest request = new CreateForumPostRequest(/* parameters */);
        UserEntity userEntity = new UserEntity(); // set up user entity

        when(userRepo.findById(request.getSubmittedById())).thenReturn(Optional.of(userEntity));
        when(forumRepo.findById(request.getForumId())).thenReturn(Optional.empty());

        boolean result = createForumPostUseCase.createForumPost(request);

        assertFalse(result);
        verify(forumPostRepo, never()).save(any(ForumPostEntity.class));
    }
}

