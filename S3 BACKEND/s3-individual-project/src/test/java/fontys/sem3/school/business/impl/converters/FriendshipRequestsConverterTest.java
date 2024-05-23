package fontys.sem3.school.business.impl.converters;
import static org.junit.jupiter.api.Assertions.*;

import fontys.sem3.school.domain.FriendshipRequest;
import fontys.sem3.school.persistence.entity.FriendshipRequestEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FriendshipRequestsConverterTest {

    @Test
    public void convert_ShouldConvertEntityToDto() {
        FriendshipRequestEntity entity = new FriendshipRequestEntity();
        entity.setId(1L);
        entity.setRequester(new UserEntity()); // Assuming existence of UserEntity
        entity.setRequested(new UserEntity());

        // Assuming UserConverter is working correctly or is mocked appropriately
        FriendshipRequest result = FriendshipRequestsConverter.convert(entity);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        // Additional assertions for requester and requested
    }

}

