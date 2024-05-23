package fontys.sem3.school.business.ForumLogic.Impl;

import fontys.sem3.school.business.ForumLogic.GetForumUseCase;
import fontys.sem3.school.persistence.ForumRepo;
import fontys.sem3.school.persistence.entity.ForumEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetForumUseCaseImpl implements GetForumUseCase {
    ForumRepo repo;
    public Optional<ForumEntity> getForum(long id){
        return repo.findById(id);
    }
}
