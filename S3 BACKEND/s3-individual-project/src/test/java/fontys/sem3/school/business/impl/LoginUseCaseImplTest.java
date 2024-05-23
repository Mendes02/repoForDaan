package fontys.sem3.school.business.impl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginUseCaseImplTest {

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
        // Mock data
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password123");
        UserEntity mockUser = new UserEntity("test@example.com", "username", "Default", "encodedPassword");
        String expectedAccessToken = "mockedAccessToken";

        // Mock behavior for userRepository.findByEmail()
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(mockUser);

        // Mock behavior for passwordEncoder.matches()
        when(passwordEncoder.matches(loginRequest.getPassword(), mockUser.getPassword())).thenReturn(true);

        // Mock behavior for accessTokenEncoder.encode()
        when(accessTokenEncoder.encode(any())).thenReturn(expectedAccessToken);

        // Perform login operation
        LoginResponse response = loginUseCase.login(loginRequest);

        // Verify interactions and assertions
        verify(userRepository).findByEmail(loginRequest.getEmail());
        verify(passwordEncoder).matches(loginRequest.getPassword(), mockUser.getPassword());
        verify(accessTokenEncoder).encode(any());
        assertNotNull(response);
        assertEquals(expectedAccessToken, response.getAccessToken());
    }

    @Test
    public void testLogin_InvalidUser() {
        // Mock data
        LoginRequest loginRequest = new LoginRequest("nonexistent@example.com", "password123");

        // Mock behavior for userRepository.findByEmail()
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(null);

        // Perform login operation and expect BadCredentialsException
        assertThrows(BadCredentialsException.class, () -> loginUseCase.login(loginRequest));

        // Verify interactions
        verify(userRepository).findByEmail(loginRequest.getEmail());
        verifyNoInteractions(passwordEncoder);
        verifyNoInteractions(accessTokenEncoder);
    }

    @Test
    public void testLogin_InvalidPassword() {
        // Mock data
        LoginRequest loginRequest = new LoginRequest("test@example.com", "wrongPassword");
        UserEntity mockUser = new UserEntity("test@example.com", "username", "Default", "encodedPassword");

        // Mock behavior for userRepository.findByEmail()
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(mockUser);

        // Mock behavior for passwordEncoder.matches() with incorrect password
        when(passwordEncoder.matches(loginRequest.getPassword(), mockUser.getPassword())).thenReturn(false);

        // Perform login operation and expect BadCredentialsException
        assertThrows(BadCredentialsException.class, () -> loginUseCase.login(loginRequest));

        // Verify interactions
        verify(userRepository).findByEmail(loginRequest.getEmail());
        verify(passwordEncoder).matches(loginRequest.getPassword(), mockUser.getPassword());
        verifyNoInteractions(accessTokenEncoder);
    }
}
