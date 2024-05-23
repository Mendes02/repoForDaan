package fontys.sem3.school.business.impl.converters;


import fontys.sem3.school.domain.VideoGame;
import fontys.sem3.school.persistence.entity.VideoGameEntity;
import java.util.ArrayList;
import java.util.List;

public class VideoGameConverter {
    private VideoGameConverter() {
    }

    public static VideoGame convert(VideoGameEntity videoGame) {
        if(videoGame == null) return new VideoGame();
        return VideoGame.builder()
                .id(videoGame.getId())
                .publisher(videoGame.getPublisher())
                .name(videoGame.getName())
                .released(videoGame.getReleased())
                .genre(videoGame.getGenre())
                .build();
    }
    public static ArrayList<VideoGame> convertList(List<VideoGameEntity> list){
        ArrayList<VideoGame> newArray = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            newArray.add(convert(list.get(i)));
        }
        return newArray;
    }

}
