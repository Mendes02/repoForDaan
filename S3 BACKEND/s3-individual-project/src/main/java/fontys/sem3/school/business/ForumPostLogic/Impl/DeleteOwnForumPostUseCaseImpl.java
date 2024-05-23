package fontys.sem3.school.business.ForumPostLogic.Impl;

import fontys.sem3.school.business.ForumPostLogic.DeleteOwnForumPostUseCase;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.configuration.security.token.AccessToken;
import fontys.sem3.school.configuration.security.token.AccessTokenDecoder;
import fontys.sem3.school.domain.DeleteOwnForumPostRequest;
import fontys.sem3.school.persistence.ForumPostRepo;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeleteOwnForumPostUseCaseImpl implements DeleteOwnForumPostUseCase {
    ForumPostRepo repo;
    AccessTokenDecoder decoder;
    AuthenticationService authenticationService;
    @Override
    @Transactional
    public boolean deleteOwnForumPost(DeleteOwnForumPostRequest request) {
        AccessToken token = decoder.decode(request.getAccessToken());
        Optional<ForumPostEntity> forumPost = repo.findById(request.getForumPostId());
        if(forumPost.isEmpty()) return false;
        if(authenticationService.getAuthenticatedUserId() != forumPost.get().getPostedBy().getId() && !authenticationService.userIsAdmin()) return false;
        else if (authenticationService.userIsAdmin()) {
            repo.deleteById(request.getForumPostId());
            return true;
        }
        return false;
    }
}
