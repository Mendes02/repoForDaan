package fontys.sem3.school.persistence;

import fontys.sem3.school.persistence.entity.FriendshipEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendshipRepo extends JpaRepository<FriendshipEntity,Long> {
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FriendshipEntity f WHERE (f.requestedBy = :user1 AND f.acceptedBy = :user2) OR (f.requestedBy = :user2 AND f.acceptedBy = :user1)")
    boolean existsByUsers(@Param("user1") UserEntity user1, @Param("user2") UserEntity user2);

    Integer countAllByAcceptedByOrRequestedBy(UserEntity user1, UserEntity user2);

    List<FriendshipEntity> findAllByAcceptedByOrRequestedBy(UserEntity entity,UserEntity entity2);

}

