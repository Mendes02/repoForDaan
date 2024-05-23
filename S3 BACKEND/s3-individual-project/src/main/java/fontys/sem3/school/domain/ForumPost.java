package fontys.sem3.school.domain;

import fontys.sem3.school.business.impl.converters.UserConverter;
import fontys.sem3.school.persistence.entity.ForumEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForumPost {
    private long id;
    private ForumEntity forum;
    private User user;
    private String postContent;
    private int positiveRatings;
    private int negativeRatings;
    private int comments;
    private LocalDate creationDate;

    public ForumPost(long id, ForumEntity forum, UserEntity user,String postContent,int positiveRatings,int negativeRatings){
        this.id = id;
        this.forum = forum;
        this.user = UserConverter.convert(user);
        this.postContent = postContent;
        this.positiveRatings = positiveRatings;
        this.negativeRatings = negativeRatings;
    }
}
