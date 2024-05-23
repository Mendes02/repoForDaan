package fontys.sem3.school.business.ForumPostLogic.Impl;

import fontys.sem3.school.business.ForumPostLogic.GetForumPostsByUserUseCase;
import fontys.sem3.school.business.impl.converters.ForumPostConverter;
import fontys.sem3.school.domain.ForumPost;
import fontys.sem3.school.persistence.ForumPostRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GetForumPostsByUserUseCaseImpl implements GetForumPostsByUserUseCase {
    ForumPostRepo forumPostRepo;
    UserRepoFr userRepo;
    ForumPostConverter converter;
    @Override
    @Transactional
    public ArrayList<ForumPost> getForumPostsByUser(long id) {
        Optional<UserEntity> user = userRepo.findById(id);
        return converter.convertList(forumPostRepo.findByPostedBy(user.get()));
    }
}
