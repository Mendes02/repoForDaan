package fontys.sem3.school.controller;

import fontys.sem3.school.business.ForumPostLogic.*;
import fontys.sem3.school.domain.CreateForumPostRequest;
import fontys.sem3.school.domain.DeleteOwnForumPostRequest;
import fontys.sem3.school.domain.ForumPost;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/forumPost")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class ForumPostController {
    private final GetForumPostUseCase getForumPostUseCase;
    private final GetForumPostByForumUseCase getForumPostByForumUseCase;
    private final GetForumPostsByUserUseCase getForumPostsByUserUseCase;
    private final CreateForumPostUseCase createForumPostUseCase;
    private final DeleteForumPostUseCase deleteForumPostUseCase;
    private final DeleteOwnForumPostUseCase deleteOwnForumPostUseCase;

    @GetMapping("{id}")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<ForumPost> getForumPost(@PathVariable(value = "id")long id){
        Optional<ForumPost> post = getForumPostUseCase.getForumPost(id);
        if(post.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(post.get());
    }
    @GetMapping("/byForum/{forumId}")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<ArrayList<ForumPost>> getPostsByForum(@PathVariable(value = "forumId")long id){
        ArrayList<ForumPost> posts = getForumPostByForumUseCase.getForumPostsByForum(id);
        if(posts.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(posts);
    }
    @GetMapping("/byUser/{userId}")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<ArrayList<ForumPost>> getPostsByUser(@PathVariable(value = "userId")long id){
        ArrayList<ForumPost> posts = getForumPostsByUserUseCase.getForumPostsByUser(id);
        if(posts.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(posts);
    }
    @PostMapping
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<Void> createForumPost(@RequestBody @Valid CreateForumPostRequest request){
        if(!createForumPostUseCase.createForumPost(request)) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}")
    @RolesAllowed("Moderator")
    public ResponseEntity<Void> deleteForumPost(@PathVariable(value = "id")long id){
        if(!deleteForumPostUseCase.deleteForumPost(id)) return ResponseEntity.badRequest().build();
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/ownPost")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<Void> deleteOwnForumPost(@RequestBody @Valid DeleteOwnForumPostRequest request){
        if(deleteOwnForumPostUseCase.deleteOwnForumPost(request)) return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        return ResponseEntity.badRequest().build();

    }
}
