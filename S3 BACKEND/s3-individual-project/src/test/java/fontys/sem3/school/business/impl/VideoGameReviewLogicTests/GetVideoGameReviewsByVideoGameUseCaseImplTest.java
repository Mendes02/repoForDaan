package fontys.sem3.school.business.impl.VideoGameReviewLogicTests;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import fontys.sem3.school.business.VideoGameReviewLogic.Impl.GetVideoGameReviewsByVideoGameUseCaseImpl;
import fontys.sem3.school.business.impl.converters.VideoGameReviewConverter;
import fontys.sem3.school.domain.User;
import fontys.sem3.school.domain.VideoGame;
import fontys.sem3.school.domain.VideoGameReview;
import fontys.sem3.school.persistence.VideoGameReviewRepo;
import fontys.sem3.school.persistence.entity.VideoGameReviewEntity;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GetVideoGameReviewsByVideoGameUseCaseImplTest {

    @Test
    public void getVideoGameReviewsByVideoGame_ReturnsConvertedReviews() {
        // Arrange
        VideoGameReviewRepo mockedRepo = Mockito.mock(VideoGameReviewRepo.class);
        VideoGameReviewConverter converter = Mockito.mock(VideoGameReviewConverter.class);
        long videoGameId = 1L;

        // Create a list of mocked VideoGameReviewEntities
        List<VideoGameReviewEntity> reviewEntities = new ArrayList<>();
        reviewEntities.add(Mockito.mock(VideoGameReviewEntity.class)); // Add as many mocks as needed for the test

        // When the repo is asked for reviews by video game ID, return the mock list
        when(mockedRepo.findByVideoGameId(videoGameId)).thenReturn(reviewEntities);

        // Mock the static convertList method in VideoGameReviewConverter
        // Note: You will need to use the mockito-inline or mockito-core library for mocking static methods
        try (MockedStatic<VideoGameReviewConverter> mockedConverter = Mockito.mockStatic(VideoGameReviewConverter.class)) {
            // Prepare the expected result
            ArrayList<VideoGameReview> convertedReviews = new ArrayList<>();
            convertedReviews.add(VideoGameReview.builder()
                    .id(1L) // Provide an appropriate ID
                    .videoGame(VideoGame.builder()
                            .id(1L) // Provide the VideoGame's ID
                            .publisher("Sample Publisher") // Provide the publisher
                            .name("Sample Game Name") // Provide the game name
                            .released(LocalDate.of(2022, 1, 1)) // Provide the release date
                            .build())
                    .reviewedBy(User.builder()
                            .id(1L) // Provide the User's ID
                            .email("user@example.com") // Provide the email
                            .username("sample_user") // Provide the username
                            .role("user") // Provide the role
                            .build())
                    .reviewContent("Sample review content") // Provide the review content
                    .build());

            // Specify the behavior for the convertList static method
            mockedConverter.when(() -> converter.convertList(reviewEntities))
                    .thenReturn(convertedReviews);

            GetVideoGameReviewsByVideoGameUseCaseImpl useCase = new GetVideoGameReviewsByVideoGameUseCaseImpl(mockedRepo,converter);

            // Act
            ArrayList<VideoGameReview> result = useCase.getVideoGameReviewsByVideoGame(videoGameId);

            // Assert
            assertEquals(convertedReviews, result);
        }
    }
}
