package fontys.sem3.school.controller;

import fontys.sem3.school.business.ForumLogic.GetForumUseCase;
import fontys.sem3.school.business.ForumLogic.GetTopForumsUseCase;
import fontys.sem3.school.persistence.entity.ForumEntity;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/forum")
public class ForumController {
    GetForumUseCase getForumUseCase;
    GetTopForumsUseCase getTopForumsUseCase;
    @GetMapping("{id}")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<Optional<ForumEntity>> getForum(@PathVariable(value = "id")long id){
        Optional<ForumEntity> forum = getForumUseCase.getForum(id);
        if(forum.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(forum);
    }
    @GetMapping("/topForums")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<List<ForumEntity>> getTopForums(){
        List<ForumEntity> forums = getTopForumsUseCase.getTopForums();
        if(forums.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(forums);
    }
}
