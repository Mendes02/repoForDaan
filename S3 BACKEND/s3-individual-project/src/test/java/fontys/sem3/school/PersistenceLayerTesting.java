package fontys.sem3.school;

import fontys.sem3.school.domain.UpdateUserRequest;
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
class PersistenceLayerTesting {
	//@BeforeEach researchh
	//@Mock data annoation
	//@DataJpaTest (not sure if that's exact) research, makes it an integration test
	//@WebMvctest for controller tests
	//
	@Mock
	UserRepoFr userRepo;
	@Test
	void contextLoads() {
	}
	@Test
	void userIsCreated() {
		UserEntity userToSave = UserEntity.builder()
				.email("mendes@gmail.com")
				.username("Mendes")
				.password("mendes")
				.build();

		Mockito.when(userRepo.save(Mockito.any(UserEntity.class))).thenReturn(userToSave);

		UserEntity savedUser = userRepo.save(userToSave);

		Assertions.assertNotNull(savedUser);

		Assertions.assertEquals("mendes@gmail.com", savedUser.getEmail());
		Assertions.assertEquals("Mendes", savedUser.getUsername()); // Assuming userExists checks existence by ID
	}

	@Test
	void userIsUpdated() {
		Mockito.when(userRepo.save(Mockito.any(UserEntity.class))).thenAnswer(invocation -> {
			UserEntity user = invocation.getArgument(0);
			user.setEmail("itworked@fhict.nl");
			return user;
		});

		UserEntity user = UserEntity.builder()
				.email("mendes@fhict.nl")
				.username("Mendes")
				.password("mendes")
				.build();

		UserEntity updatedUser = userRepo.save(user);
		Assertions.assertEquals("itworked@fhict.nl", updatedUser.getEmail());
	}

	@Test
	void getCorrectUser() {
		UserEntity user = UserEntity.builder()
				.email("correct@fhict.nl")
				.username("Correct")
				.password("mendes")
				.build();

		Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

		Optional<UserEntity> optionalUser = userRepo.findById(1L);
		Assertions.assertTrue(optionalUser.isPresent());

		UserEntity foundUser = optionalUser.get();
		Assertions.assertEquals("Correct", foundUser.getUsername());
	}
}
