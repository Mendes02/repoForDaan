package fontys.sem3.school.business.impl.converters;

import fontys.sem3.school.domain.FriendshipRequest;
import fontys.sem3.school.persistence.entity.FriendshipRequestEntity;

import java.util.ArrayList;
import java.util.List;

public class FriendshipRequestsConverter {

    public static FriendshipRequest convert(FriendshipRequestEntity entity){
        return FriendshipRequest.builder()
                .id(entity.getId())
                .requester(UserConverter.convert(entity.getRequester()))
                .requested(UserConverter.convert(entity.getRequested()))
                .build();
    }

    public static ArrayList<FriendshipRequest> convertList(List<FriendshipRequestEntity> entities){
        ArrayList<FriendshipRequest> requests = new ArrayList<>();
        for(FriendshipRequestEntity entity : entities) requests.add(convert(entity));
        return requests;
    }
}
