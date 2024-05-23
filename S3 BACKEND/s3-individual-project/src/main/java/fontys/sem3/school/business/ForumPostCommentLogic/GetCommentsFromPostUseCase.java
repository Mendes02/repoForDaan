package fontys.sem3.school.business.ForumPostCommentLogic;

import fontys.sem3.school.business.exception.ForumPostDoesNotExistException;
import fontys.sem3.school.domain.ForumPostComment;

import java.util.List;

public interface GetCommentsFromPostUseCase {
    List<ForumPostComment> getComments(long postId) throws ForumPostDoesNotExistException;
}
