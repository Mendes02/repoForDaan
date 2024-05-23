package fontys.sem3.school.configuration.db;
import fontys.sem3.school.persistence.UserRepoFr;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DatabaseDataInitializer {

    private UserRepoFr userRepository;
    @EventListener(ApplicationReadyEvent.class)
    public void populateDatabaseInitialDummyData() {
        //userRepository.save(UserEntity.builder().id(1).email("mendes@fhict.nl").username("Mendes").role("Default").salt("1").password("mendes").build());
    }
}
