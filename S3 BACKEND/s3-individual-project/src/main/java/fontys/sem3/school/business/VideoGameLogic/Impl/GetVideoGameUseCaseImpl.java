package fontys.sem3.school.business.VideoGameLogic.Impl;

import fontys.sem3.school.business.VideoGameLogic.GetVideoGameUseCase;
import fontys.sem3.school.business.impl.converters.VideoGameConverter;
import fontys.sem3.school.domain.VideoGame;
import fontys.sem3.school.persistence.VideoGameRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetVideoGameUseCaseImpl implements GetVideoGameUseCase {
    private VideoGameRepo repo;

    @Override
    public Optional<VideoGame> getVideoGame(long videoGameId){
        return repo.findById(videoGameId).map(VideoGameConverter::convert);
    }
}
