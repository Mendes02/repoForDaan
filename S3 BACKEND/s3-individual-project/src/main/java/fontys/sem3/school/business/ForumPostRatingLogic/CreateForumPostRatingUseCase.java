package fontys.sem3.school.business.ForumPostRatingLogic;

import fontys.sem3.school.domain.CreateForumPostRatingRequest;

public interface CreateForumPostRatingUseCase {
    boolean createForumPostRating(CreateForumPostRatingRequest request);
}
