package fontys.sem3.school.business.ForumPostLogic;

import fontys.sem3.school.domain.ForumPost;

import java.util.ArrayList;

public interface GetForumPostsByUserUseCase {
    public ArrayList<ForumPost> getForumPostsByUser(long id);
}
