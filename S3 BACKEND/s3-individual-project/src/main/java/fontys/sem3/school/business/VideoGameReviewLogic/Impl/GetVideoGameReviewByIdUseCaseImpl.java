package fontys.sem3.school.business.VideoGameReviewLogic.Impl;

import fontys.sem3.school.business.VideoGameReviewLogic.GetVideoGameReviewByIdUseCase;
import fontys.sem3.school.business.impl.converters.VideoGameReviewConverter;
import fontys.sem3.school.domain.VideoGameReview;
import fontys.sem3.school.persistence.VideoGameReviewRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetVideoGameReviewByIdUseCaseImpl implements GetVideoGameReviewByIdUseCase {
    VideoGameReviewRepo repo;
    VideoGameReviewConverter converter;
    @Override
    @Transactional
    public Optional<VideoGameReview> getReviewById(long id) {
        return repo.findById(id)
                .map(converter::convert); // Safely apply the converter only if the value is present
    }
}
