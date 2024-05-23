package fontys.sem3.school.controller;

import fontys.sem3.school.business.UserLogic.CreateUserUseCase;
import fontys.sem3.school.business.UserLogic.GetProfileDataUseCase;
import fontys.sem3.school.business.UserLogic.GetUserUseCase;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.domain.CreateUserRequest;
import fontys.sem3.school.domain.CreateUserResponse;
import fontys.sem3.school.domain.ProfileDataResponse;
import fontys.sem3.school.domain.User;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final GetUserUseCase getUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    //private final UpdateUserUseCase updateUserUseCase;
    private final GetProfileDataUseCase getProfileDataUseCase;

    @GetMapping("/{id}")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<User> getUser(@PathVariable(value = "id")final long id){
        final Optional<User> foundUser = getUserUseCase.getUser(id);
        if(foundUser.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(foundUser.get());
    }
    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request){
        CreateUserResponse response = createUserUseCase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    /*@PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UpdateUserRequest request){
        updateUserUseCase.updateUser(request);
        return ResponseEntity.noContent().build();
    }*/

    @GetMapping("/profileData/{id}")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<ProfileDataResponse> getProfileData(@PathVariable(value = "id")long id){
        try {
            Optional<ProfileDataResponse> response = getProfileDataUseCase.getProfileData(id);
            if (response.isEmpty()) return ResponseEntity.notFound().build();
            return ResponseEntity.ok().body(response.get());
        }
        catch (UserDoesNotExistException ex){
            return ResponseEntity.notFound().build();
        }
    }
}
