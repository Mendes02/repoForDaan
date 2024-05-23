package fontys.sem3.school.business.ForumPostCommentLogic.Impl;

import fontys.sem3.school.business.ForumPostCommentLogic.CreateForumPostCommentUseCase;
import fontys.sem3.school.business.exception.ForumPostDoesNotExistException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.domain.CreateForumPostCommentRequest;
import fontys.sem3.school.persistence.ForumPostCommentRepo;
import fontys.sem3.school.persistence.ForumPostRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.ForumPostCommentEntity;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateForumPostCommentUseCaseImpl implements CreateForumPostCommentUseCase {
    UserRepoFr userRepo;
    ForumPostRepo forumPostRepo;
    AuthenticationService authenticationService;
    ForumPostCommentRepo forumPostCommentRepo;

    @Override
    @Transactional
    public void createComment(CreateForumPostCommentRequest request) throws UserDoesNotExistException, ForumPostDoesNotExistException {
        Optional<UserEntity> foundUser = userRepo.findById(authenticationService.getAuthenticatedUserId());
        if(foundUser.isEmpty()) throw new UserDoesNotExistException();
        Optional<ForumPostEntity> postToComment = forumPostRepo.findById(request.getPostId());
        if(postToComment.isEmpty()) throw new ForumPostDoesNotExistException();
        forumPostCommentRepo.save(new ForumPostCommentEntity(postToComment.get(),foundUser.get(),request.getCommentContent(), LocalDate.now()));
    }
}
