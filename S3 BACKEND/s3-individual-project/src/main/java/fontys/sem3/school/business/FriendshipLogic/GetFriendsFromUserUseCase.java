package fontys.sem3.school.business.FriendshipLogic;

import fontys.sem3.school.business.exception.BadCredentialsException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.domain.User;

import java.util.ArrayList;

public interface GetFriendsFromUserUseCase {
    ArrayList<User> getFriendsFromUser(long id) throws UserDoesNotExistException, BadCredentialsException;
}
