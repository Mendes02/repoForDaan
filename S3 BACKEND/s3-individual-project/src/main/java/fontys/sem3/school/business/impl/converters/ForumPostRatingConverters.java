package fontys.sem3.school.business.impl.converters;

import fontys.sem3.school.domain.ForumPostRating;
import fontys.sem3.school.persistence.entity.ForumPostRatingEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ForumPostRatingConverters {
    ForumPostConverter converter;
    public  ForumPostRating convert(ForumPostRatingEntity entity){
        return ForumPostRating.builder()
                .id(entity.getId())
                .ratedBy(UserConverter.convert(entity.getRatedBy()))
                .rateValue(entity.getRatedValue())
                .forumPost(converter.convert(entity.getForumPost()))
                .build();
    }

    public  ArrayList<ForumPostRating> convertList(List<ForumPostRatingEntity> list){
        ArrayList<ForumPostRating> dtoList = new ArrayList<>();
        for(ForumPostRatingEntity rating : list){
            dtoList.add(convert(rating));
        }
        return dtoList;
    }
}
