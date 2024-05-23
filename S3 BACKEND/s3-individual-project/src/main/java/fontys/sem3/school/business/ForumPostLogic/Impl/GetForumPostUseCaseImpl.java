package fontys.sem3.school.business.ForumPostLogic.Impl;

import fontys.sem3.school.business.ForumPostLogic.GetForumPostUseCase;
import fontys.sem3.school.business.impl.converters.ForumPostConverter;
import fontys.sem3.school.domain.ForumPost;
import fontys.sem3.school.persistence.ForumPostRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetForumPostUseCaseImpl implements GetForumPostUseCase {
    ForumPostRepo repo;
    ForumPostConverter converter;
    @Override
    @Transactional
    public Optional<ForumPost> getForumPost(long id) {
        return repo.findById(id).map(converter::convert);
    }
}
