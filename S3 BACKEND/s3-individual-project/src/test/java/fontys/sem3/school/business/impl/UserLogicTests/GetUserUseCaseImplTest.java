package fontys.sem3.school.business.impl.UserLogicTests;

import fontys.sem3.school.business.UserLogic.GetUserUseCase;
import fontys.sem3.school.business.UserLogic.Impl.GetUserUseCaseImpl;
import fontys.sem3.school.domain.User;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GetUserUseCaseImplTest {
    @Mock
    private UserRepoFr repo;
    @Test
    void getUser() {
        UserEntity mendes = UserEntity.builder()
                .id(1)
                .email("mendes@gmail.com")
                .username("Mendes")
                .password("mendes")
                .build();

        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(mendes));

        GetUserUseCase useCase = new GetUserUseCaseImpl(repo);
        Optional<User> user = useCase.getUser(1);

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(mendes.getId(), user.get().getId());
    }
}