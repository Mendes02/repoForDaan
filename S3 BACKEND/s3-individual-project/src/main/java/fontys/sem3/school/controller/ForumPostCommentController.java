package fontys.sem3.school.controller;

import fontys.sem3.school.business.ForumPostCommentLogic.CreateForumPostCommentUseCase;
import fontys.sem3.school.business.ForumPostCommentLogic.GetCommentsFromPostUseCase;
import fontys.sem3.school.business.exception.ForumPostDoesNotExistException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.domain.CreateForumPostCommentRequest;
import fontys.sem3.school.domain.ForumPostComment;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/forumPostComment")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class ForumPostCommentController {
    GetCommentsFromPostUseCase getCommentsFromPostUseCase;
    CreateForumPostCommentUseCase createForumPostCommentUseCase;
    @GetMapping("/{postId}")
    @RolesAllowed({"Default","Moderator"})
    ResponseEntity<List<ForumPostComment>> getCommentsFromPost(@PathVariable(value = "postId") long postId){
        try{
            return ResponseEntity.ok().body(getCommentsFromPostUseCase.getComments(postId));
        }
        catch(ForumPostDoesNotExistException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @RolesAllowed({"Default","Moderator"})
    ResponseEntity<Void> createForumPostComment(@RequestBody @Valid CreateForumPostCommentRequest request){
        try {
            createForumPostCommentUseCase.createComment(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch(UserDoesNotExistException ex){
            return ResponseEntity.badRequest().build();
        }
        catch(ForumPostDoesNotExistException ex){
            return ResponseEntity.notFound().build();
        }
    }
}
