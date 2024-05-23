package fontys.sem3.school.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDataResponse {
    private User user;
    private int friends;
    private int numberOfPosts;
    private int numberOfReviews;
    private long numberOfLikesFromOtherUsers;
    private String friendRequestStatus;
}
