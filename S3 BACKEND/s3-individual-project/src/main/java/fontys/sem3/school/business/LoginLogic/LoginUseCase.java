package fontys.sem3.school.business.LoginLogic;

import fontys.sem3.school.business.exception.BadCredentialsException;
import fontys.sem3.school.configuration.security.token.AccessTokenEncoder;
import fontys.sem3.school.configuration.security.token.impl.AccessTokenImpl;
import fontys.sem3.school.domain.LoginRequest;
import fontys.sem3.school.domain.LoginResponse;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public interface LoginUseCase {
    LoginResponse login(LoginRequest loginRequest);

    @Service
    @RequiredArgsConstructor
    class LoginUseCaseImpl implements LoginUseCase {
        private final UserRepoFr userRepository;
        private final PasswordEncoder passwordEncoder;
        private final AccessTokenEncoder accessTokenEncoder;

        @Override
        public LoginResponse login(LoginRequest loginRequest) {
            UserEntity user = userRepository.findByEmail(loginRequest.getEmail());
            if (user == null) {
                throw new BadCredentialsException();
            }

            if (!matchesPassword(loginRequest.getPassword(), user.getPassword())) {
                throw new BadCredentialsException();
            }

            String accessToken = generateAccessToken(user);
            return LoginResponse.builder().accessToken(accessToken).build();
        }

        private boolean matchesPassword(String rawPassword, String encodedPassword) {
            return passwordEncoder.matches(rawPassword, encodedPassword);
        }

        private String generateAccessToken(UserEntity user) {
            Long userId = user != null ? user.getId() : null;
            List<String> roles = new ArrayList<>();
            if (user.getRole() != null) {
                roles.add(user.getRole());
            }
            else{
                roles.add("Default");
            }
            return accessTokenEncoder.encode(new AccessTokenImpl(user.getEmail(), userId, roles));
        }
    }
}
