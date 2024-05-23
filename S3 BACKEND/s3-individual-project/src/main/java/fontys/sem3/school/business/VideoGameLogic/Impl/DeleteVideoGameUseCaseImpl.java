package fontys.sem3.school.business.VideoGameLogic.Impl;

import fontys.sem3.school.business.VideoGameLogic.DeleteVideoGameUseCase;
import fontys.sem3.school.persistence.VideoGameRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteVideoGameUseCaseImpl implements DeleteVideoGameUseCase {
    VideoGameRepo repo;

    @Transactional
    public void deleteVideoGame(long id){
        repo.deleteById(id);
    }
}
