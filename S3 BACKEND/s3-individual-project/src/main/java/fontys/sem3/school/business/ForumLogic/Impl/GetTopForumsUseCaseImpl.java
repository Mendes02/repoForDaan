package fontys.sem3.school.business.ForumLogic.Impl;

import fontys.sem3.school.business.ForumLogic.GetTopForumsUseCase;
import fontys.sem3.school.persistence.ForumRepo;
import fontys.sem3.school.persistence.entity.ForumEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class GetTopForumsUseCaseImpl implements GetTopForumsUseCase {
    ForumRepo repo;

    @Override
    public List<ForumEntity> getTopForums() {
        return repo.findAll();
    }
}
