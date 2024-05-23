package fontys.sem3.school.business.ForumPostLogic;

import fontys.sem3.school.domain.DeleteOwnForumPostRequest;

public interface DeleteOwnForumPostUseCase {
    boolean deleteOwnForumPost(DeleteOwnForumPostRequest request);
}
