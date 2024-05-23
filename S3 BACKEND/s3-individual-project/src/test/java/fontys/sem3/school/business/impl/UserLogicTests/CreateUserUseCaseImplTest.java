package fontys.sem3.school.business.impl.UserLogicTests;
import fontys.sem3.school.business.UserLogic.Impl.CreateUserUseCaseImpl;
import fontys.sem3.school.domain.CreateUserRequest;
import fontys.sem3.school.domain.CreateUserResponse;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseImplTest {

    @Mock
    private UserRepoFr repo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Test
    public void testCreateUser() {
        CreateUserRequest request = new CreateUserRequest("username", "email@example.com", "password123");

        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        UserEntity savedUser = UserEntity.builder()
                .id(1L)
                .username(request.getUsername())
                .email(request.getEmail())
                .role("Default")
                .password("encodedPassword")
                .build();
        when(repo.save(any(UserEntity.class))).thenReturn(savedUser);

        CreateUserResponse response = createUserUseCase.createUser(request);

        verify(passwordEncoder).encode(request.getPassword());
        verify(repo).save(any(UserEntity.class));
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }
}
