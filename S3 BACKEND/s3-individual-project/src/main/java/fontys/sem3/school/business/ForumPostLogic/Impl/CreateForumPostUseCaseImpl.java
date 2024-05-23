package fontys.sem3.school.business.ForumPostLogic.Impl;

import fontys.sem3.school.business.ForumPostLogic.CreateForumPostUseCase;
import fontys.sem3.school.domain.CreateForumPostRequest;
import fontys.sem3.school.persistence.ForumPostRepo;
import fontys.sem3.school.persistence.ForumRepo;
import fontys.sem3.school.persistence.UserRepoFr;
import fontys.sem3.school.persistence.entity.ForumEntity;
import fontys.sem3.school.persistence.entity.ForumPostEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateForumPostUseCaseImpl implements CreateForumPostUseCase {
    UserRepoFr userRepo;
    ForumRepo forumRepo;
    ForumPostRepo forumPostRepo;
    @Override
    @Transactional
    public boolean createForumPost(CreateForumPostRequest request) {
        Optional<UserEntity> submittedBy = userRepo.findById(request.getSubmittedById());
        Optional<ForumEntity> forum = forumRepo.findById(request.getForumId());
        if(submittedBy.isEmpty() || forum.isEmpty()) return false;
        forumPostRepo.save(new ForumPostEntity(forum.get(),submittedBy.get(), request.getPostContent(), LocalDate.now()));
        return true;

    }
}
