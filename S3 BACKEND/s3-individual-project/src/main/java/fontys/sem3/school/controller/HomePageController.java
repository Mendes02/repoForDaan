package fontys.sem3.school.controller;

import fontys.sem3.school.business.HomePageLogic.GetHomePageDataUseCase;
import fontys.sem3.school.business.exception.BadCredentialsException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.domain.HomePageDataResponse;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/homePage")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class HomePageController {
    private final GetHomePageDataUseCase getHomePageDataUseCase;
    @GetMapping("/{id}")
    @RolesAllowed({"Default","Moderator"})
    public ResponseEntity<HomePageDataResponse> getHomePageData(@PathVariable(value = "id")long id){
        try {
            HomePageDataResponse response = getHomePageDataUseCase.getHomePageData(id);
            return ResponseEntity.ok().body(response);
        }
        catch(BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        catch (UserDoesNotExistException ex){
            return ResponseEntity.notFound().build();
        }
    }
}
