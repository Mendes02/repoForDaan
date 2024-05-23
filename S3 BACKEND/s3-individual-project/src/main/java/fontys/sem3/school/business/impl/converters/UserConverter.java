package fontys.sem3.school.business.impl.converters;

import fontys.sem3.school.domain.User;
import fontys.sem3.school.persistence.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserConverter {
    private UserConverter() {
    }

    public static User convert(UserEntity user) {
        return User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public static ArrayList<User> convertList(List<UserEntity> entities){
        ArrayList<User> newList = new ArrayList<>();
        for(UserEntity entity : entities) newList.add(convert(entity));
        return newList;
    }
}
