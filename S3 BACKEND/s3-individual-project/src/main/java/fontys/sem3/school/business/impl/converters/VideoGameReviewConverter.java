package fontys.sem3.school.business.impl.converters;

import fontys.sem3.school.domain.VideoGameReview;
import fontys.sem3.school.persistence.VideoGameReviewRatingRepo;
import fontys.sem3.school.persistence.entity.VideoGameReviewEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class VideoGameReviewConverter {
    VideoGameReviewRatingRepo repo;
    public  VideoGameReview convert(VideoGameReviewEntity entity){
        return VideoGameReview.builder()
                .id(entity.getId())
                .videoGame(VideoGameConverter.convert(entity.getVideoGame()))
                .reviewedBy(UserConverter.convert(entity.getReviewedBy()))
                .reviewContent(entity.getReviewContent())
                .rateValue(entity.getRateValue())
                .positiveRatings(repo.countAllByVideogameReviewAndRateValue(entity,true))
                .negativeRatings(repo.countAllByVideogameReviewAndRateValue(entity,false))
                .creationDate(entity.getCreationDate())
                .build();
    }
    public  ArrayList<VideoGameReview> convertList(List<VideoGameReviewEntity> list){
        ArrayList<VideoGameReview> convertedList = new ArrayList<>();
        for(int i = 0; i < list.size();i++){
            convertedList.add(convert(list.get(i)));
        }
        return convertedList;
    }



}
