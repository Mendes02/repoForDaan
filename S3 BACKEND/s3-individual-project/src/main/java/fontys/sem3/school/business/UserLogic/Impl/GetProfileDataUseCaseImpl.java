package fontys.sem3.school.business.UserLogic.Impl;
import fontys.sem3.school.business.UserLogic.GetProfileDataUseCase;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.business.impl.converters.UserConverter;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.domain.ProfileDataResponse;
import fontys.sem3.school.domain.User;
import fontys.sem3.school.persistence.*;
import fontys.sem3.school.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetProfileDataUseCaseImpl implements GetProfileDataUseCase {
    UserRepoFr userRepo;
    ForumPostRepo postRepo;
    VideoGameReviewRepo reviewRepo;
    VideoGameReviewRatingRepo videoGameReviewRatingRepo;
    ForumPostRatingRepo forumPostRatingRepo;
    FriendshipRequestsRepo friendshipRequestsRepo;
    FriendshipRepo friendshipRepo;
    AuthenticationService authenticationService;
    @Override
    @Transactional
    public Optional<ProfileDataResponse> getProfileData(long id) throws UserDoesNotExistException {
        Optional<UserEntity> foundUser = userRepo.findById(id);
        if(foundUser.isEmpty()) throw new UserDoesNotExistException();
        User user = UserConverter.convert(foundUser.get());
        String friendRequestSatus = "Available";
        Optional<UserEntity> authenticatedUser = userRepo.findById(authenticationService.getAuthenticatedUserId());
        if(friendshipRepo.existsByUsers(foundUser.get(),authenticatedUser.get())) friendRequestSatus = "NA";
        if(friendshipRequestsRepo.existsByRequesterAndRequested(authenticatedUser.get(),foundUser.get())) friendRequestSatus = "Pending";
        if(authenticationService.getAuthenticatedUserId() == id) friendRequestSatus = "S";
        long likesFromReviews = videoGameReviewRatingRepo.countTotalLikesOnUserReviews(foundUser.get());
        long likesFromPosts = forumPostRatingRepo.countLikesOnUserForumPosts(foundUser.get());
        return Optional.of(ProfileDataResponse.builder()
                .user(user)
                .friends(friendshipRepo.countAllByAcceptedByOrRequestedBy(foundUser.get(),foundUser.get()))
                .numberOfPosts(postRepo.countAllByPostedBy(foundUser.get()))
                .numberOfReviews(reviewRepo.countAllByReviewedBy(foundUser.get()))
                .numberOfLikesFromOtherUsers(likesFromPosts + likesFromReviews)
                .friendRequestStatus(friendRequestSatus)
                .build()) ;
    }
}
