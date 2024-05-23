package fontys.sem3.school.business.FriendshipLogic.Impl;

import fontys.sem3.school.business.FriendshipLogic.CreateFriendshipRequestUseCase;
import fontys.sem3.school.business.exception.BadCredentialsException;
import fontys.sem3.school.business.exception.FriendshipAlreadyExistsException;
import fontys.sem3.school.business.exception.FriendshipRequestAlreadyExistsException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.persistence.FriendshipRepo;
import fontys.sem3.school.persistence.FriendshipRequestsRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.FriendshipRequestEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateFriendshipRequestUseCaseImpl implements CreateFriendshipRequestUseCase {
    UserRepoFr userRepo;
    FriendshipRepo friendshipRepo;
    FriendshipRequestsRepo friendshipRequestsRepo;
    AuthenticationService authenticationService;
    @Override
    @Transactional
    public void createFriendshipRequest(long requesterId, long requestedId) throws FriendshipAlreadyExistsException, UserDoesNotExistException, FriendshipRequestAlreadyExistsException,BadCredentialsException {
        if(authenticationService.getAuthenticatedUserId() == requesterId) {
            Optional<UserEntity> requesterEntity = userRepo.findById(requesterId);
            if (requesterEntity.isEmpty()) throw new UserDoesNotExistException();
            Optional<UserEntity> requestedEntity = userRepo.findById(requestedId);
            if (requestedEntity.isEmpty()) throw new UserDoesNotExistException();
            if (friendshipRepo.existsByUsers(requesterEntity.get(), requesterEntity.get()))
                throw new FriendshipAlreadyExistsException();
            if (friendshipRequestsRepo.existsByRequesterAndRequested(requesterEntity.get(), requestedEntity.get()))
                throw new FriendshipRequestAlreadyExistsException();
            friendshipRequestsRepo.save(new FriendshipRequestEntity(requesterEntity.get(), requestedEntity.get()));
        }
        else{
            throw new BadCredentialsException();
        }
    }
}
