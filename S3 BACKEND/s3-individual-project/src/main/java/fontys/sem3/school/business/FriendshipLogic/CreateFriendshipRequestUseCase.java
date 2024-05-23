package fontys.sem3.school.business.FriendshipLogic;

import fontys.sem3.school.business.exception.BadCredentialsException;
import fontys.sem3.school.business.exception.FriendshipAlreadyExistsException;
import fontys.sem3.school.business.exception.FriendshipRequestAlreadyExistsException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;

public interface CreateFriendshipRequestUseCase {
    void createFriendshipRequest(long requesterId, long requestedId) throws FriendshipAlreadyExistsException, UserDoesNotExistException, FriendshipRequestAlreadyExistsException, BadCredentialsException;
}
