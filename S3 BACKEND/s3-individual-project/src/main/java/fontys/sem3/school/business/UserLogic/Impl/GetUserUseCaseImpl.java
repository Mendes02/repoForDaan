package fontys.sem3.school.business.UserLogic.Impl;

import fontys.sem3.school.business.UserLogic.GetUserUseCase;
import fontys.sem3.school.business.impl.converters.UserConverter;
import fontys.sem3.school.domain.User;
import fontys.sem3.school.persistence.UserRepoFr;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {
    private UserRepoFr repo;
    @Override
    public Optional<User> getUser(long userId){
        return repo.findById(userId).map(UserConverter::convert);
    }
}
