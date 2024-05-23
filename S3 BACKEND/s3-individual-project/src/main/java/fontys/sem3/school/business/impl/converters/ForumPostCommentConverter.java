package fontys.sem3.school.business.impl.converters;

import fontys.sem3.school.domain.ForumPostComment;
import fontys.sem3.school.persistence.entity.ForumPostCommentEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ForumPostCommentConverter {
    ForumPostConverter forumPostConverter;
    public ForumPostComment convert(ForumPostCommentEntity entity){
        return ForumPostComment.builder()
                .id(entity.getId())
                .forumPost(forumPostConverter.convert(entity.getForumPost()))
                .submittedBy(UserConverter.convert(entity.getSubmittedBy()))
                .commentContent(entity.getCommentContent())
                .publishedDate(entity.getPublishedDate())
                .build();
    }


    public List<ForumPostComment> convertList(List<ForumPostCommentEntity> entities){
        return entities.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
