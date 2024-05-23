package fontys.sem3.school.business.ForumPostLogic;

import fontys.sem3.school.domain.ForumPost;

import java.util.ArrayList;

public interface GetForumPostByForumUseCase {
    public ArrayList<ForumPost> getForumPostsByForum(long id);
}
