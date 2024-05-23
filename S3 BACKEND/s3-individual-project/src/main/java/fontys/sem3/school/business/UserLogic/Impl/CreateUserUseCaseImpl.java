package fontys.sem3.school.business.UserLogic.Impl;

import fontys.sem3.school.business.UserLogic.CreateUserUseCase;
import fontys.sem3.school.domain.CreateUserRequest;
import fontys.sem3.school.domain.CreateUserResponse;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserRepoFr repo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request){
        UserEntity userEntity = saveNewUser(request);
        return CreateUserResponse.builder()
                .id(userEntity.getId())
                .build();
    }
    private UserEntity saveNewUser(CreateUserRequest request){
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .role("Default")
                .password(encodedPassword)
                .build();
        return repo.save(user);
    }
}
