package fontys.sem3.school.business.FriendshipLogic;

import fontys.sem3.school.business.exception.BadCredentialsException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.domain.FriendshipRequest;

import java.util.ArrayList;

public interface GetRequestsToUserUseCase {
    ArrayList<FriendshipRequest> get(long id) throws UserDoesNotExistException, BadCredentialsException;
}
