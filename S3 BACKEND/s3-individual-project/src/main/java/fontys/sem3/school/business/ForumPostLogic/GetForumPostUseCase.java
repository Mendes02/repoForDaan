package fontys.sem3.school.business.ForumPostLogic;

import fontys.sem3.school.domain.ForumPost;

import java.util.Optional;

public interface GetForumPostUseCase {
    public Optional<ForumPost> getForumPost(long id);
}
