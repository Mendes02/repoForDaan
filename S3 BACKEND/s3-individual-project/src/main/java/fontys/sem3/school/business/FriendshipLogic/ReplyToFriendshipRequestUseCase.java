package fontys.sem3.school.business.FriendshipLogic;

import fontys.sem3.school.business.exception.*;
import fontys.sem3.school.domain.ReplyToFriendshipRequestRequest;

public interface ReplyToFriendshipRequestUseCase {
    void reply(ReplyToFriendshipRequestRequest request) throws  BadCredentialsException, FriendshipAlreadyExistsException, FriendshipRequestDoesNotExistException;
}
