package fontys.sem3.school.persistence;

import fontys.sem3.school.persistence.entity.FriendshipRequestEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendshipRequestsRepo extends JpaRepository<FriendshipRequestEntity,Long> {
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FriendshipRequestEntity f WHERE (f.requester = :requester AND f.requested = :requested) OR (f.requester = :requested AND f.requested = :requester)")
    boolean existsByRequesterAndRequested(@Param("requester") UserEntity requester, @Param("requested") UserEntity requested);

    List<FriendshipRequestEntity> findAllByRequested(UserEntity user);
}
