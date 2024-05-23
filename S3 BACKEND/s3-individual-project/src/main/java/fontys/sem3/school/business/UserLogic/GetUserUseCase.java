package fontys.sem3.school.business.UserLogic;

import fontys.sem3.school.domain.User;

import java.util.Optional;

public interface GetUserUseCase {
    Optional<User> getUser(long userId);
}
