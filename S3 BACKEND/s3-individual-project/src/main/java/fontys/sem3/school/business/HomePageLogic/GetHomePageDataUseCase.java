package fontys.sem3.school.business.HomePageLogic;

import fontys.sem3.school.business.exception.BadCredentialsException;
import fontys.sem3.school.business.exception.UserDoesNotExistException;
import fontys.sem3.school.domain.HomePageDataResponse;

public interface GetHomePageDataUseCase {
    HomePageDataResponse getHomePageData(long id) throws UserDoesNotExistException, BadCredentialsException;
}
