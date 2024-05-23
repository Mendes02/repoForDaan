package fontys.sem3.school.business.VideoGameLogic.Impl;

import fontys.sem3.school.business.VideoGameLogic.GetAllVideoGamesUseCase;
import fontys.sem3.school.business.impl.converters.VideoGameConverter;
import fontys.sem3.school.domain.VideoGame;
import fontys.sem3.school.persistence.VideoGameRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@AllArgsConstructor
public class GetAllVideoGamesUseCaseImpl implements GetAllVideoGamesUseCase {
    VideoGameRepo repo;

    @Override
    public ArrayList<VideoGame> getAllVideogames() {
        return VideoGameConverter.convertList(repo.findAll());
    }
}
