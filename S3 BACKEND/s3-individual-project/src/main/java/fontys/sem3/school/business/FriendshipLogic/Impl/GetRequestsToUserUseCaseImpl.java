package fontys.sem3.school.business.FriendshipLogic.Impl;

import fontys.sem3.school.business.FriendshipLogic.GetRequestsToUserUseCase;
import fontys.sem3.school.business.exception.BadCredentialsException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.business.impl.converters.FriendshipRequestsConverter;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.domain.FriendshipRequest;
import fontys.sem3.school.persistence.FriendshipRequestsRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetRequestsToUserUseCaseImpl implements GetRequestsToUserUseCase {
    UserRepoFr userRepo;
    AuthenticationService authenticationService;
    FriendshipRequestsRepo friendshipRequestsRepo;
    @Override
    @Transactional
    public ArrayList<FriendshipRequest> get(long id) throws UserDoesNotExistException, BadCredentialsException {
        if(authenticationService.getAuthenticatedUserId() != id) throw new BadCredentialsException();
        Optional<UserEntity> foundUser = userRepo.findById(id);
        if(foundUser.isEmpty()) throw new UserDoesNotExistException();
        return FriendshipRequestsConverter.convertList(friendshipRequestsRepo.findAllByRequested(foundUser.get()));
    }
}
