package fontys.sem3.school.persistence;

import fontys.sem3.school.persistence.entity.VideoGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VideoGameRepo extends JpaRepository<VideoGameEntity,Long> {
     Optional<VideoGameEntity> findById(long id);
     VideoGameEntity findByName(String name);

     void deleteById(long id);

     @Query(value = "SELECT vg.id, vg.name, vg.publisher, vg.released, COUNT(fp.id) AS postCount " +
             "FROM videogame vg " +
             "JOIN forum f ON f.videogame = vg.id " +
             "JOIN forum_post fp ON fp.forum = f.id " +
             "WHERE fp.creation_date > CURRENT_DATE - INTERVAL 1 DAY " +
             "GROUP BY vg.id, vg.name, vg.publisher, vg.released " +
             "ORDER BY postCount DESC",
             nativeQuery = true)
     List<Object[]> findTrendingVideoGames();

     List<VideoGameEntity> findByNameContainingAndGenre(String name, String genre);

     List<VideoGameEntity> findByNameContaining(String name);

     List<VideoGameEntity> findByGenre(String genre);
}
