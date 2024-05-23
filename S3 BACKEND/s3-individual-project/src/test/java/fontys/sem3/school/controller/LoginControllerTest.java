package fontys.sem3.school.controller;

import static org.junit.jupiter.api.Assertions.*;

import fontys.sem3.school.business.LoginLogic.LoginUseCase;
import fontys.sem3.school.business.exception.BadCredentialsException;
import fontys.sem3.school.configuration.security.token.AccessTokenEncoder;
import fontys.sem3.school.domain.LoginRequest;
import fontys.sem3.school.domain.LoginResponse;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private UserRepoFr userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    private LoginUseCase.LoginUseCaseImpl loginUseCase;

    @Test
    public void testLogin_Successful() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        String encodedPassword = "encodedPassword";
        String expectedAccessToken = "mockedAccessToken";

        LoginRequest loginRequest = new LoginRequest(email, password);
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword(encodedPassword);

        when(userRepository.findByEmail(email)).thenReturn(userEntity);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(accessTokenEncoder.encode(any())).thenReturn(expectedAccessToken);

        // Act
        LoginResponse loginResponse = loginUseCase.login(loginRequest);

        // Assert
        assertNotNull(loginResponse, "LoginResponse should not be null");
        assertEquals(expectedAccessToken, loginResponse.getAccessToken(), "Access token should match expected value");
        verify(userRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
        verify(accessTokenEncoder, times(1)).encode(any());
    }

    @Test
    public void testLogin_InvalidCredentials() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        String encodedPassword = "encodedPassword";

        LoginRequest loginRequest = new LoginRequest(email, password);
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword(encodedPassword);

        when(userRepository.findByEmail(email)).thenReturn(userEntity);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        assertThrows(fontys.sem3.school.business.exception.BadCredentialsException.class, () -> loginUseCase.login(loginRequest));
        verify(userRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
    }

    @Test
    public void testLogin_UserNotFound() {
        String email = "nonexistent@example.com";
        String password = "password123";

        LoginRequest loginRequest = new LoginRequest(email, password);
        when(userRepository.findByEmail(email)).thenReturn(null);
        
        assertThrows(BadCredentialsException.class, () -> loginUseCase.login(loginRequest));
        verify(userRepository, times(1)).findByEmail(email);
        verifyNoMoreInteractions(passwordEncoder);
    }
}