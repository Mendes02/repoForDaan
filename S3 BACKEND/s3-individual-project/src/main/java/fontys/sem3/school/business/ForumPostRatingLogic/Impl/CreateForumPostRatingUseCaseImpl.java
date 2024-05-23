package fontys.sem3.school.business.ForumPostRatingLogic.Impl;

import fontys.sem3.school.business.ForumPostRatingLogic.CreateForumPostRatingUseCase;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.domain.CreateForumPostRatingRequest;
import fontys.sem3.school.persistence.ForumPostRatingRepo;
import fontys.sem3.school.persistence.ForumPostRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import fontys.sem3.school.persistence.entity.ForumPostRatingEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateForumPostRatingUseCaseImpl implements CreateForumPostRatingUseCase {
    private final UserRepoFr userRepo;
    private final ForumPostRepo forumPostRepo;
    private final ForumPostRatingRepo forumPostRatingRepo;
    private final AuthenticationService authenticationService;

    @Override
    @Transactional
    public boolean createForumPostRating(CreateForumPostRatingRequest request) {
        Long userId = authenticationService.getAuthenticatedUserId();
        if(userId != request.getRatedById()) return false;
        Optional<UserEntity> foundUser = userRepo.findById(request.getRatedById());
        if(foundUser.isEmpty()) return false;
        Optional<ForumPostEntity> foundPost = forumPostRepo.findById(request.getForumPostId());
        if(foundPost.isEmpty()) return false;
        Optional<ForumPostRatingEntity> rating = forumPostRatingRepo.findByRatedByAndForumPost(foundUser.get(),foundPost.get());
        if(rating.isPresent() && rating.get().getRatedValue() == request.getRateValue()){
            deleteRating(rating.get());
            return true;
        }
        else if(rating.isPresent() && rating.get().getRatedValue() != request.getRateValue()){
            rating.get().setRateValue(request.getRateValue());
            replaceRatingValue(rating.get());
            return true;
        }
        forumPostRatingRepo.save(new ForumPostRatingEntity(foundUser.get(),request.getRateValue(),foundPost.get()));
        return true;
    }
    private void deleteRating(ForumPostRatingEntity entity){
        forumPostRatingRepo.delete(entity);
    }

    private void replaceRatingValue(ForumPostRatingEntity entity){
        forumPostRatingRepo.save(entity);
    }

}
