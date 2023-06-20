package gr.aueb.cf.boardgamelibcf.repository;

import gr.aueb.cf.boardgamelibcf.model.OwnedBoardgame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnedBoardgameRepository extends JpaRepository<OwnedBoardgame, Long> {
    @Query("SELECT count(*) > 0 FROM OwnedBoardgame ob WHERE ob.user.id = ?1 AND ob.boardgame.id = ?2")
    boolean ownedBoardgameExists(Long userId, Long boardgameId);
}
