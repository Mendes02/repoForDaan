package fontys.sem3.school.controller;

import fontys.sem3.school.business.UserLogic.CreateUserUseCase;
import fontys.sem3.school.business.UserLogic.GetUserUseCase;
import fontys.sem3.school.domain.CreateUserRequest;
import fontys.sem3.school.domain.CreateUserResponse;
import fontys.sem3.school.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private GetUserUseCase getUserUseCase;

    @Mock
    private CreateUserUseCase createUserUseCase;

    @InjectMocks
    private UserController userController;

    @Test
    public void testGetUser_Successful() {
        // Arrange
        long userId = 1L;
        User user = new User( 1, "test@example.com","Mendes","Default");

        when(getUserUseCase.getUser(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<User> responseEntity = userController.getUser(userId);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
        verify(getUserUseCase, times(1)).getUser(userId);
    }

    @Test
    public void testGetUser_UserNotFound() {
        // Arrange
        long userId = 2L;

        when(getUserUseCase.getUser(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> responseEntity = userController.getUser(userId);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(getUserUseCase, times(1)).getUser(userId);
    }

    @Test
    public void testCreateUser_Successful() {
        // Arrange
        CreateUserRequest createUserRequest = new CreateUserRequest("TestUser", "test@example.com", "password123");
        CreateUserResponse createUserResponse = new CreateUserResponse(1);

        when(createUserUseCase.createUser(createUserRequest)).thenReturn(createUserResponse);

        // Act
        ResponseEntity<CreateUserResponse> responseEntity = userController.createUser(createUserRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createUserResponse, responseEntity.getBody());
        verify(createUserUseCase, times(1)).createUser(createUserRequest);
    }
}