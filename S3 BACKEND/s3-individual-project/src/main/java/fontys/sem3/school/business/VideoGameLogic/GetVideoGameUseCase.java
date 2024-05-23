package fontys.sem3.school.business.VideoGameLogic;

import fontys.sem3.school.domain.VideoGame;

import java.util.Optional;

public interface GetVideoGameUseCase {
    public Optional<VideoGame> getVideoGame(long id);
}