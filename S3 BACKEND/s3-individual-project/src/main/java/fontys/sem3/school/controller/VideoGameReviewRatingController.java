package fontys.sem3.school.controller;

import fontys.sem3.school.business.VideoGameReviewRatingLogic.CreateVideoGameReviewRatingUseCase;
import fontys.sem3.school.domain.SaveVideoGameReviewRatingRequest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviewRating")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class VideoGameReviewRatingController {
    private final CreateVideoGameReviewRatingUseCase saveRatingUseCase;
    @PostMapping
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<Void> saveRating(@RequestBody @Valid SaveVideoGameReviewRatingRequest request){
        if(!saveRatingUseCase.saveRating(request)) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
