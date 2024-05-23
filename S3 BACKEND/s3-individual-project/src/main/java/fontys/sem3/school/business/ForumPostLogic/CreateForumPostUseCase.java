package fontys.sem3.school.business.ForumPostLogic;

import fontys.sem3.school.domain.CreateForumPostRequest;

public interface CreateForumPostUseCase {
    public boolean createForumPost(CreateForumPostRequest request);
}
