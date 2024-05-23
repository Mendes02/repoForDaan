package fontys.sem3.school.business.ForumPostCommentLogic;

import fontys.sem3.school.business.exception.ForumPostDoesNotExistException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.domain.CreateForumPostCommentRequest;

public interface CreateForumPostCommentUseCase {
    void createComment(CreateForumPostCommentRequest request) throws ForumPostDoesNotExistException, UserDoesNotExistException;
}
