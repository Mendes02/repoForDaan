package fontys.sem3.school.business.FriendshipLogic.Impl;

import fontys.sem3.school.business.FriendshipLogic.GetFriendsFromUserUseCase;
import fontys.sem3.school.business.exception.BadCredentialsException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.business.impl.converters.UserConverter;
import fontys.sem3.school.configuration.security.auth.AuthenticationService;
import fontys.sem3.school.domain.User;
import fontys.sem3.school.persistence.FriendshipRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.FriendshipEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetFriendsFromUserUseCaseImpl implements GetFriendsFromUserUseCase {
    UserRepoFr userRepo;
    FriendshipRepo friendshipRepo;
    AuthenticationService authenticationService;
    @Override
    @Transactional
    public ArrayList<User> getFriendsFromUser(long id) throws UserDoesNotExistException, BadCredentialsException {
        if(authenticationService.getAuthenticatedUserId() != id) throw new BadCredentialsException();
        Optional<UserEntity> foundUser = userRepo.findById(id);
        if(foundUser.isEmpty()) throw new UserDoesNotExistException();
        List<FriendshipEntity> friendships = friendshipRepo.findAllByAcceptedByOrRequestedBy(foundUser.get(),foundUser.get());
        return getFriendsOutOfFriendships(friendships,foundUser.get());
    }

    public ArrayList<User> getFriendsOutOfFriendships(List<FriendshipEntity> friendships, UserEntity requestingUser){
        ArrayList<User> listOfFriends = new ArrayList<>();
        for(FriendshipEntity friendship : friendships){
            if(requestingUser == friendship.getRequestedBy()) listOfFriends.add(UserConverter.convert(friendship.getAcceptedBy()));
            else{
                listOfFriends.add(UserConverter.convert(friendship.getRequestedBy()));
            }
        }
        return listOfFriends;
    }
}
