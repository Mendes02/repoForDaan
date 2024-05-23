package fontys.sem3.school.business.impl.UserLogicTests;
import fontys.sem3.school.business.impl.converters.UserConverter;
import fontys.sem3.school.domain.User;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserConverterTest {

    @Test
    public void testConvert() {
        UserEntity userEntity = new UserEntity(1L, "test@example.com", "username", "Default", "password");

        User convertedUser = UserConverter.convert(userEntity);

        assertNotNull(convertedUser);
        assertEquals(userEntity.getId(), convertedUser.getId());
        assertEquals(userEntity.getEmail(), convertedUser.getEmail());
        assertEquals(userEntity.getUsername(), convertedUser.getUsername());
        assertEquals(userEntity.getRole(), convertedUser.getRole());
    }
}
