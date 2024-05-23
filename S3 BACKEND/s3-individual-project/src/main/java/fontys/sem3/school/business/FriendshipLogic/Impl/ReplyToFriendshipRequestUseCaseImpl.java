package fontys.sem3.school.business.FriendshipLogic.Impl;

import fontys.sem3.school.business.FriendshipLogic.ReplyToFriendshipRequestUseCase;
import fontys.sem3.school.business.exception.*;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.domain.ReplyToFriendshipRequestRequest;
import fontys.sem3.school.persistence.FriendshipRepo;
import fontys.sem3.school.persistence.FriendshipRequestsRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.FriendshipEntity;
import fontys.sem3.school.persistence.entity.FriendshipRequestEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ReplyToFriendshipRequestUseCaseImpl implements ReplyToFriendshipRequestUseCase {
    UserRepoFr userRepo;
    FriendshipRequestsRepo friendshipRequestsRepo;
    FriendshipRepo friendshipRepo;
    AuthenticationService authenticationService;
    @Override
    @Transactional
    public void reply(ReplyToFriendshipRequestRequest request) throws  BadCredentialsException, FriendshipAlreadyExistsException, FriendshipRequestDoesNotExistException {
        Optional<FriendshipRequestEntity> foundRequest = friendshipRequestsRepo.findById(request.getFriendshipRequestId());
        if(foundRequest.isEmpty()) throw new FriendshipRequestDoesNotExistException();
        if(authenticationService.getAuthenticatedUserId() != foundRequest.get().getRequested().getId()) throw new BadCredentialsException();
        if(friendshipRepo.existsByUsers(foundRequest.get().getRequested(),foundRequest.get().getRequester())) throw new FriendshipAlreadyExistsException();
        friendshipRequestsRepo.delete(foundRequest.get());
        if(!request.isReplyValue()) return;
        friendshipRepo.save(new FriendshipEntity(foundRequest.get().getRequester(),foundRequest.get().getRequested()));
    }
}
