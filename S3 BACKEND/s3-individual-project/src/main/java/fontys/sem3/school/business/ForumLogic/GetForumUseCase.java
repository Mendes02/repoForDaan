package fontys.sem3.school.business.ForumLogic;

import fontys.sem3.school.persistence.entity.ForumEntity;

import java.util.Optional;

public interface GetForumUseCase {
    public Optional<ForumEntity> getForum(long id);
}
