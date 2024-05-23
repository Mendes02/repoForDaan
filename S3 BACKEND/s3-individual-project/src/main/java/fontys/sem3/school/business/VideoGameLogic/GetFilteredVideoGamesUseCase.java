package fontys.sem3.school.business.VideoGameLogic;


import fontys.sem3.school.domain.VideoGame;

import java.util.ArrayList;

public interface GetFilteredVideoGamesUseCase {
    ArrayList<VideoGame> getFilteredVideoGames(String genre, String name);
}
