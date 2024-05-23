package fontys.sem3.school.business.ForumPostCommentLogic.Impl;

import fontys.sem3.school.business.ForumPostCommentLogic.GetCommentsFromPostUseCase;
import fontys.sem3.school.business.exception.ForumPostDoesNotExistException;
import fontys.sem3.school.business.impl.converters.ForumPostCommentConverter;
import fontys.sem3.school.domain.ForumPostComment;
import fontys.sem3.school.persistence.ForumPostCommentRepo;
import fontys.sem3.school.persistence.ForumPostRepo;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetCommentsFromPostUseCaseImpl implements GetCommentsFromPostUseCase {
    ForumPostCommentConverter converter;
    ForumPostRepo forumPostRepo;
    ForumPostCommentRepo forumPostCommentRepo;
    @Override
    @Transactional
    public List<ForumPostComment> getComments(long postId) throws ForumPostDoesNotExistException {
        Optional<ForumPostEntity> foundPost = forumPostRepo.findById(postId);
        if (foundPost.isEmpty()) throw new ForumPostDoesNotExistException();
        return converter.convertList(forumPostCommentRepo.findAllByForumPost(foundPost.get()));
    }
}
