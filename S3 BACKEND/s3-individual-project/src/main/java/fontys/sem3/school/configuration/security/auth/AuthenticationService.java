package fontys.sem3.school.configuration.security.auth;

import fontys.sem3.school.configuration.security.token.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        if (!(authentication.getDetails() instanceof AccessToken)) {
            throw new IllegalStateException("Authentication details are not an instance of AccessToken");
        }

        AccessToken accessToken = (AccessToken) authentication.getDetails();
        return accessToken.getUserId();
    }
    public boolean userIsAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        if (!(authentication.getDetails() instanceof AccessToken)) {
            throw new IllegalStateException("Authentication details are not an instance of AccessToken");
        }

        AccessToken accessToken = (AccessToken) authentication.getDetails();
        if(accessToken.hasRole("Moderator")) return true;
        return false;
    }
}
