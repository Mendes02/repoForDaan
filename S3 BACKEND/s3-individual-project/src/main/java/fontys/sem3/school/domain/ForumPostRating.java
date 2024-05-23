package fontys.sem3.school.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForumPostRating {
    private long id;
    private User ratedBy;
    private boolean rateValue;
    private ForumPost forumPost;

    public boolean getRatedValue(){
        return this.rateValue;
    }
}
