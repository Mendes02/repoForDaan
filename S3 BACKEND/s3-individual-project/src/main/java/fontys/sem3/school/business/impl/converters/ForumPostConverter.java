package fontys.sem3.school.business.impl.converters;

import fontys.sem3.school.domain.ForumPost;
import fontys.sem3.school.persistence.ForumPostCommentRepo;
import fontys.sem3.school.persistence.ForumPostRatingRepo;
import fontys.sem3.school.persistence.ForumRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ForumPostConverter {
    private final ForumPostRatingRepo repo;
    ForumPostCommentRepo commentRepo;
    private final ForumRepo forumRepo;
    private final UserRepoFr userRepo;
    public  ForumPost convert(ForumPostEntity entity){
        return ForumPost.builder()
                .id(entity.getId())
                .forum(entity.getForum())
                .user(UserConverter.convert(entity.getPostedBy()))
                .postContent(entity.getPostContent())
                .negativeRatings(repo.countAllByForumPostAndRateValue(entity,false))
                .positiveRatings(repo.countAllByForumPostAndRateValue(entity,true))
                .comments(commentRepo.countAllByForumPost(entity))
                .creationDate(entity.getCreationDate())
                .build();
    }
    public  ArrayList<ForumPost> convertList(List<ForumPostEntity> list){
        ArrayList<ForumPost> newArray = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            newArray.add(convert(list.get(i)));
        }
        return newArray;
    }

}
