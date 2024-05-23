package fontys.sem3.school.business.impl.VideoGameReviewLogicTests;


import fontys.sem3.school.business.VideoGameReviewLogic.Impl.CreateVideoGameReviewUseCaseImpl;
import fontys.sem3.school.domain.CreateVideoGameReviewRequest;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.VideoGameRepo;
import fontys.sem3.school.persistence.VideoGameReviewRepo;
import fontys.sem3.school.persistence.entity.VideoGameEntity;
import fontys.sem3.school.persistence.entity.VideoGameReviewEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CreateVideoGameReviewUseCaseImplTest {

    @Test
    public void createReview_Successful_WhenUserAndVideoGameExist() {
        // Mock the dependencies
        UserRepoFr mockedUserRepo = Mockito.mock(UserRepoFr.class);
        VideoGameRepo mockedVideoGameRepo = Mockito.mock(VideoGameRepo.class);
        VideoGameReviewRepo mockedReviewRepo = Mockito.mock(VideoGameReviewRepo.class);

        UserEntity mockedUserEntity = Mockito.mock(UserEntity.class);
        VideoGameEntity mockedVideoGameEntity = Mockito.mock(VideoGameEntity.class);

        Mockito.when(mockedUserRepo.findById(anyLong())).thenReturn(Optional.of(mockedUserEntity));
        Mockito.when(mockedVideoGameRepo.findById(anyLong())).thenReturn(Optional.of(mockedVideoGameEntity));

        CreateVideoGameReviewUseCaseImpl useCase = new CreateVideoGameReviewUseCaseImpl(mockedUserRepo, mockedVideoGameRepo, mockedReviewRepo);

        CreateVideoGameReviewRequest request = new CreateVideoGameReviewRequest(1L, 1L, "Great game!",true);

        // Call the method under test
        boolean result = useCase.createReview(request);

        // Assertions
        assertTrue(result);
        verify(mockedReviewRepo, times(1)).save(any(VideoGameReviewEntity.class));
    }
    @Test
    public void createReview_Fails_WhenUserOrVideoGameNotExist() {
        // Mock the dependencies
        UserRepoFr mockedUserRepo = Mockito.mock(UserRepoFr.class);
        VideoGameRepo mockedVideoGameRepo = Mockito.mock(VideoGameRepo.class);
        VideoGameReviewRepo mockedReviewRepo = Mockito.mock(VideoGameReviewRepo.class);

        Mockito.when(mockedUserRepo.findById(anyLong())).thenReturn(Optional.empty()); // User does not exist
        Mockito.when(mockedVideoGameRepo.findById(anyLong())).thenReturn(Optional.of(Mockito.mock(VideoGameEntity.class))); // Video game exists

        CreateVideoGameReviewUseCaseImpl useCase = new CreateVideoGameReviewUseCaseImpl(mockedUserRepo, mockedVideoGameRepo, mockedReviewRepo);

        CreateVideoGameReviewRequest request = new CreateVideoGameReviewRequest(1L, 1L, "Great game!",true);

        // Call the method under test
        boolean result = useCase.createReview(request);

        // Assertions
        assertFalse(result);
        verify(mockedReviewRepo, never()).save(any(VideoGameReviewEntity.class));
    }
}

