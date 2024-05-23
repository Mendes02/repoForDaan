package fontys.sem3.school.business.HomePageLogic.Impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import fontys.sem3.school.business.exception.BadCredentialsException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.business.impl.converters.ForumPostConverter;
import fontys.sem3.school.business.impl.converters.VideoGameReviewConverter;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.domain.*;
import fontys.sem3.school.persistence.ForumPostRatingRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.VideoGameReviewRatingRepo;
import fontys.sem3.school.persistence.VideoGameReviewRepo;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import fontys.sem3.school.persistence.entity.VideoGameEntity;
import fontys.sem3.school.persistence.entity.VideoGameReviewEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

public class GetHomePageDataUseCaseImplTest {

    @Mock
    private UserRepoFr userRepo;
    @Mock
    private VideoGameReviewRepo videoGameReviewRepo;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private VideoGameReviewConverter videoGameReviewConverter;
    @Mock
    private ForumPostConverter forumPostConverter;
    @Mock
    private VideoGameReviewRatingRepo videoGameReviewRatingRepo;
    @Mock
    private ForumPostRatingRepo forumPostRatingRepo;

    @InjectMocks
    private GetHomePageDataUseCaseImpl getHomePageDataUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenUserDoesNotExist_thenThrowUserDoesNotExistException() {
        long userId = 1L;
        when(authenticationService.getAuthenticatedUserId()).thenReturn(userId);
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserDoesNotExistException.class, () -> getHomePageDataUseCase.getHomePageData(userId));
    }

    @Test
    public void whenBadCredentials_thenThrowBadCredentialsException() {
        long authenticatedUserId = 1L;
        long requestedUserId = 2L; // Different from authenticatedUserId to simulate bad credentials
        when(authenticationService.getAuthenticatedUserId()).thenReturn(authenticatedUserId);
        when(userRepo.findById(requestedUserId)).thenReturn(Optional.of(new UserEntity()));

        assertThrows(BadCredentialsException.class, () -> getHomePageDataUseCase.getHomePageData(requestedUserId));
    }

    @Test
    public void whenVideoGameReviewRepoFails_thenHandleException() throws UserDoesNotExistException, BadCredentialsException {
        long userId = 1L;
        UserEntity userEntity = new UserEntity(); // Initialize as needed

        // Mocking the required dependencies
        when(authenticationService.getAuthenticatedUserId()).thenReturn(userId);
        when(userRepo.findById(userId)).thenReturn(Optional.of(userEntity));
        when(videoGameReviewRatingRepo.findTopVideoGameReviewsWithMostTrueRatingsSince(any(PageRequest.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());
        when(forumPostRatingRepo.findTopForumPostsByPositiveRatingsSince(any(PageRequest.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());
        when(videoGameReviewRepo.findVideoGamesWithMostPositiveReviewsSince(any(LocalDate.class), any(PageRequest.class)))
                .thenThrow(new RuntimeException("Database error")); // Simulate repository failure

        // Act and Assert
        assertThrows(RuntimeException.class, () -> getHomePageDataUseCase.getHomePageData(userId));
    }
    @Test
    public void whenDataIsRetrieved_thenCorrectlyConvertToDTOs() throws UserDoesNotExistException, BadCredentialsException {
        long userId = 1L;
        LocalDate yesterday = LocalDate.now().minusDays(1);
        UserEntity userEntity = new UserEntity(); // Initialize as needed

        // Mock entities
        VideoGameReviewEntity mockReviewEntity = new VideoGameReviewEntity(); // Initialize as needed
        ForumPostEntity mockPostEntity = new ForumPostEntity(); // Initialize as needed
        VideoGameEntity mockVideoGameEntity = new VideoGameEntity(); // Initialize as needed

        // Mock the repository responses
        when(authenticationService.getAuthenticatedUserId()).thenReturn(userId);
        when(userRepo.findById(userId)).thenReturn(Optional.of(userEntity));
        when(videoGameReviewRatingRepo.findTopVideoGameReviewsWithMostTrueRatingsSince(any(PageRequest.class), eq(yesterday)))
                .thenReturn(Collections.singletonList(mockReviewEntity));
        when(forumPostRatingRepo.findTopForumPostsByPositiveRatingsSince(any(PageRequest.class), eq(yesterday)))
                .thenReturn(Collections.singletonList(mockPostEntity));
        when(videoGameReviewRepo.findVideoGamesWithMostPositiveReviewsSince(eq(yesterday), any(PageRequest.class)))
                .thenReturn(Collections.singletonList(mockVideoGameEntity));

        // Act
        HomePageDataResponse response = getHomePageDataUseCase.getHomePageData(userId);

        // Assert
        assertNotNull(response, "Response should not be null");
        // You can add more specific assertions here if you know what the converted data should look like
    }



    // Additional tests for checking exceptions like UserDoesNotExistException and BadCredentialsException
}
