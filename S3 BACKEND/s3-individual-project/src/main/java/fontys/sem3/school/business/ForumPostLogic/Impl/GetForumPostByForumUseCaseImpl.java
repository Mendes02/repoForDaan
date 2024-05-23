package fontys.sem3.school.business.ForumPostLogic.Impl;

import fontys.sem3.school.business.ForumPostLogic.GetForumPostByForumUseCase;
import fontys.sem3.school.business.impl.converters.ForumPostConverter;
import fontys.sem3.school.domain.ForumPost;
import fontys.sem3.school.persistence.ForumPostRepo;
import fontys.sem3.school.persistence.ForumRepo;
import fontys.sem3.school.persistence.entity.ForumEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetForumPostByForumUseCaseImpl implements GetForumPostByForumUseCase {
    ForumPostRepo repo;
    ForumRepo forumRepo;
    ForumPostConverter converter;
    @Override
    @Transactional
    public ArrayList<ForumPost> getForumPostsByForum(long id) {
        Optional<ForumEntity> forum = forumRepo.findById(id);
        return converter.convertList(repo.findByForum(forum.get()));
    }
}
