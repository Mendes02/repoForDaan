package fontys.sem3.school.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
public class HomePageDataResponse {
    private List<ForumPost> suggestedForumPosts;
    private List<VideoGameReview> suggestedVideoGameReviews;
    private List<VideoGame> trendingVideoGames;
}
