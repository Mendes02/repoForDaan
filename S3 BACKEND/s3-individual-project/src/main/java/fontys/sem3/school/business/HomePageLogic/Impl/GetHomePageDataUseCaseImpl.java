package fontys.sem3.school.business.HomePageLogic.Impl;

import fontys.sem3.school.business.HomePageLogic.GetHomePageDataUseCase;
import fontys.sem3.school.business.exception.BadCredentialsException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.business.impl.converters.ForumPostConverter;
import fontys.sem3.school.business.impl.converters.VideoGameConverter;
import fontys.sem3.school.business.impl.converters.VideoGameReviewConverter;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.domain.HomePageDataResponse;
import fontys.sem3.school.persistence.*;
import fontys.sem3.school.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetHomePageDataUseCaseImpl implements GetHomePageDataUseCase {
    UserRepoFr userRepo;
    VideoGameReviewRepo videoGameReviewRepo;
    AuthenticationService authenticationService;
    VideoGameReviewConverter videoGameReviewConverter;
    ForumPostConverter forumPostConverter;
    VideoGameReviewRatingRepo videoGameReviewRatingRepo;
    ForumPostRatingRepo forumPostRatingRepo;
    @Override
    @Transactional
    public HomePageDataResponse getHomePageData(long id) throws UserDoesNotExistException,BadCredentialsException {
        checkDataIntegrity(id);
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return HomePageDataResponse.builder()
                .suggestedVideoGameReviews(videoGameReviewConverter.convertList(videoGameReviewRatingRepo.findTopVideoGameReviewsWithMostTrueRatingsSince(PageRequest.of(0, 6),yesterday)))
                .suggestedForumPosts(forumPostConverter.convertList(forumPostRatingRepo.findTopForumPostsByPositiveRatingsSince(PageRequest.of(0, 6),yesterday)))
                .trendingVideoGames(VideoGameConverter.convertList(videoGameReviewRepo.findVideoGamesWithMostPositiveReviewsSince(yesterday,PageRequest.of(0, 6))))
                .build();
    }

    private void checkDataIntegrity(long requestId) throws UserDoesNotExistException,BadCredentialsException{
        Long userId = authenticationService.getAuthenticatedUserId();
        if(userId != requestId) throw new BadCredentialsException();
        Optional<UserEntity> foundUser = userRepo.findById(requestId);
        if(foundUser.isEmpty()) throw new UserDoesNotExistException();
    }
}
