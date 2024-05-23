package fontys.sem3.school.controller;

import fontys.sem3.school.business.VideoGameReviewLogic.*;
import fontys.sem3.school.domain.CreateVideoGameReviewRequest;
import fontys.sem3.school.domain.DeleteOwnVideoGameReviewRequest;
import fontys.sem3.school.domain.VideoGameReview;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/videogameReview")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class VideoGameReviewsController {
    private final GetVideoGameReviewsByVideoGameUseCase getVideoGameReviewsByVideoGameUseCase;
    private final DeleteOwnVideoGameReviewUseCase deleteOwnVideoGameReviewUseCase;
    private final CreateVideoGameReviewUseCase createVideoGameReviewUseCase;
    private final GetVideoGameReviewByIdUseCase getVideoGameReviewByIdUseCase;
    @GetMapping("{id}")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<ArrayList<VideoGameReview>> getVideogameReviews(@PathVariable(value = "id")long id){
        ArrayList<VideoGameReview> reviews = getVideoGameReviewsByVideoGameUseCase.getVideoGameReviewsByVideoGame(id);
        if(reviews.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(reviews);
    }

    @DeleteMapping()
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<Void> deleteOwnVideoGameReview(@RequestBody @Valid DeleteOwnVideoGameReviewRequest request){
        if(!deleteOwnVideoGameReviewUseCase.deleteOwnReview(request)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok().build();
    }


    @PostMapping
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<Void> createVideoGameReview(@RequestBody @Valid CreateVideoGameReviewRequest request){
        if(!createVideoGameReviewUseCase.createReview(request)) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/byId/{postId}")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<VideoGameReview> getRatingsCountByReview(@PathVariable(value = "postId") long postId){
        Optional<VideoGameReview> review = getVideoGameReviewByIdUseCase.getReviewById(postId);
        if(review.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(review.get());
    }
}
