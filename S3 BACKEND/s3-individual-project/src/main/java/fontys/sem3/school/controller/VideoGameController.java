package fontys.sem3.school.controller;

import fontys.sem3.school.business.VideoGameLogic.DeleteVideoGameUseCase;
import fontys.sem3.school.business.VideoGameLogic.GetAllVideoGamesUseCase;
import fontys.sem3.school.business.VideoGameLogic.GetFilteredVideoGamesUseCase;
import fontys.sem3.school.business.VideoGameLogic.GetVideoGameUseCase;
import fontys.sem3.school.domain.VideoGame;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RequestMapping("/videogame")
public class VideoGameController {
    GetVideoGameUseCase getVideoGameUseCase;
    DeleteVideoGameUseCase deleteVideoGameUseCase;
    GetAllVideoGamesUseCase getAllVideoGamesUseCase;
    GetFilteredVideoGamesUseCase getFilteredVideoGamesUseCase;
    @GetMapping("{id}")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<VideoGame> getVideoGame(@PathVariable(value = "id")final long id){
        final Optional<VideoGame> foundVideoGame = getVideoGameUseCase.getVideoGame(id);
        if(foundVideoGame.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(foundVideoGame.get());
    }

    @DeleteMapping("/delete/{id}")
    @RolesAllowed("Moderator")
    public ResponseEntity<Void> deleteVideoGame(@PathVariable(value = "id") final long id){
        deleteVideoGameUseCase.deleteVideoGame(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<ArrayList<VideoGame>> getTopVideoGames(){
        return ResponseEntity.ok().body(getAllVideoGamesUseCase.getAllVideogames());
    }

    @GetMapping("/searchFilter/{genre}/{name}")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<ArrayList<VideoGame>> getVideoGamesByGenreAndOrName(@PathVariable(value = "genre")String genre,@PathVariable(value = "name")String name){
        ArrayList<VideoGame> videoGames = getFilteredVideoGamesUseCase.getFilteredVideoGames(genre, name);
        return ResponseEntity.ok().body(videoGames);
    }

}
