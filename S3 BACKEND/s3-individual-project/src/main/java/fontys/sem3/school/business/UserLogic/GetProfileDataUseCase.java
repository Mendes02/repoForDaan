package fontys.sem3.school.business.UserLogic;

import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.domain.ProfileDataResponse;

import java.util.Optional;

public interface GetProfileDataUseCase {
    Optional<ProfileDataResponse> getProfileData(long id) throws UserDoesNotExistException;
}
