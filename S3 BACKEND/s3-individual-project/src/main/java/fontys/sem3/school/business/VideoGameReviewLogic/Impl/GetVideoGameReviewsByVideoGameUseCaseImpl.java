package fontys.sem3.school.business.VideoGameReviewLogic.Impl;

import fontys.sem3.school.business.VideoGameReviewLogic.GetVideoGameReviewsByVideoGameUseCase;
import fontys.sem3.school.business.impl.converters.VideoGameReviewConverter;
import fontys.sem3.school.domain.VideoGameReview;
import fontys.sem3.school.persistence.VideoGameReviewRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class GetVideoGameReviewsByVideoGameUseCaseImpl implements GetVideoGameReviewsByVideoGameUseCase {
    VideoGameReviewRepo repo;
    VideoGameReviewConverter converter;
    @Override
    public ArrayList<VideoGameReview> getVideoGameReviewsByVideoGame(long id) {
        return converter.convertList(repo.findByVideoGameId(id));
    }
}
