package fontys.sem3.school.business.ForumPostLogic.Impl;

import fontys.sem3.school.business.ForumPostLogic.DeleteForumPostUseCase;
import fontys.sem3.school.persistence.ForumPostRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class DeleteForumPostUseCaseImpl implements DeleteForumPostUseCase {
    ForumPostRepo repo;
    @Override
    @Transactional
    public boolean deleteForumPost(long id) {
        if(repo.findById(id).isEmpty()) return false;
        repo.deleteById(id);
        return true;
    }
}
