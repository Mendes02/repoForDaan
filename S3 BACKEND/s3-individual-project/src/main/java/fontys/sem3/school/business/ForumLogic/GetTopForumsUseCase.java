package fontys.sem3.school.business.ForumLogic;

import fontys.sem3.school.persistence.entity.ForumEntity;

import java.util.List;


public interface GetTopForumsUseCase {
    List<ForumEntity> getTopForums();
}
