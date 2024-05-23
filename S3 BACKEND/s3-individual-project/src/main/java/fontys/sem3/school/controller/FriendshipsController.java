package fontys.sem3.school.controller;

import fontys.sem3.school.business.FriendshipLogic.CreateFriendshipRequestUseCase;
import fontys.sem3.school.business.FriendshipLogic.GetFriendsFromUserUseCase;
import fontys.sem3.school.business.FriendshipLogic.GetRequestsToUserUseCase;
import fontys.sem3.school.business.FriendshipLogic.ReplyToFriendshipRequestUseCase;
import fontys.sem3.school.business.exception.*;
import fontys.sem3.school.domain.FriendshipRequest;
import fontys.sem3.school.domain.ReplyToFriendshipRequestRequest;
import fontys.sem3.school.domain.User;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/friendships")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class FriendshipsController {
    private final CreateFriendshipRequestUseCase createFriendshipRequestUseCase;
    private final GetFriendsFromUserUseCase getFriendsFromUserUseCase;
    private final ReplyToFriendshipRequestUseCase replyToFriendshipRequestUseCase;
    private final GetRequestsToUserUseCase getRequestsToUserUseCase;
    @PostMapping("/create/{requesterId}/{requestedId}")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<Void> createFriendshipRequest(@PathVariable(value = "requesterId")long requesterId, @PathVariable(value ="requestedId") long requestedId){
        try{
            createFriendshipRequestUseCase.createFriendshipRequest(requesterId,requestedId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch(BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        catch(UserDoesNotExistException ex){
            return ResponseEntity.notFound().build();
        }
        catch(FriendshipAlreadyExistsException | FriendshipRequestAlreadyExistsException ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<Void> replyToFriendshipRequest(@RequestBody @Valid ReplyToFriendshipRequestRequest request){
        try{
            replyToFriendshipRequestUseCase.reply(request);
            return ResponseEntity.ok().build();
        }
        catch(BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        catch(FriendshipRequestDoesNotExistException ex){
            return ResponseEntity.notFound().build();
        }
        catch(FriendshipAlreadyExistsException ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/requests/{id}")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<ArrayList<FriendshipRequest>> getRequestsToUser(@PathVariable(value = "id")long id){
        try{
            ArrayList<FriendshipRequest> requests = getRequestsToUserUseCase.get(id);
            return ResponseEntity.ok().body(requests);
        }
        catch(UserDoesNotExistException ex){
            return ResponseEntity.notFound().build();
        }
        catch(BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<ArrayList<User>> getFriendsFromUser(@PathVariable(value = "id")long id){
        try{
            ArrayList<User> friends = getFriendsFromUserUseCase.getFriendsFromUser(id);
            return ResponseEntity.ok().body(friends);
        }
        catch (BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        catch(UserDoesNotExistException ex){
            return ResponseEntity.notFound().build();
        }
    }

}
