package fontys.sem3.school.controller;

import fontys.sem3.school.business.ForumPostRatingLogic.CreateForumPostRatingUseCase;
import fontys.sem3.school.domain.CreateForumPostRatingRequest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/forumPostRatings")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class ForumPostRatingController {
    CreateForumPostRatingUseCase createForumPostRatingUseCase;


    @PostMapping
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<Void> saveRating(@RequestBody @Valid CreateForumPostRatingRequest request){
        if(!createForumPostRatingUseCase.createForumPostRating(request)) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
