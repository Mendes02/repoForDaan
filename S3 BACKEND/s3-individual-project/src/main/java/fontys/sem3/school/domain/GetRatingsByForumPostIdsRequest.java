package fontys.sem3.school.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class GetRatingsByForumPostIdsRequest {
    ArrayList<Long> postIds;
}
