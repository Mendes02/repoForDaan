package fontys.sem3.school.business.VideoGameLogic.Impl;

import fontys.sem3.school.business.VideoGameLogic.GetFilteredVideoGamesUseCase;
import fontys.sem3.school.business.impl.converters.VideoGameConverter;
import fontys.sem3.school.domain.VideoGame;
import fontys.sem3.school.persistence.VideoGameRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@AllArgsConstructor
public class GetFilteredVideoGamesUseCaseImpl implements GetFilteredVideoGamesUseCase {
    VideoGameRepo videoGameRepo;
    @Override
    public ArrayList<VideoGame> getFilteredVideoGames(String genre, String name) {
        if (!Objects.equals(name, "null") && !Objects.equals(genre, "null")) {
            return VideoGameConverter.convertList(videoGameRepo.findByNameContainingAndGenre(name, genre));
        } else if (!Objects.equals(name, "null") && Objects.equals(genre, "null")) {
            return VideoGameConverter.convertList(videoGameRepo.findByNameContaining(name));
        } else if (!Objects.equals(genre, "null") && Objects.equals(name, "null")) {
            return VideoGameConverter.convertList(videoGameRepo.findByGenre(genre));
        } else {
            return VideoGameConverter.convertList(videoGameRepo.findAll());
        }
    }
}
