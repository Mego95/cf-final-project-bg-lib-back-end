package gr.aueb.cf.boardgamelibcf.service;

import gr.aueb.cf.boardgamelibcf.dto.BoardgameDTO;
import gr.aueb.cf.boardgamelibcf.model.Boardgame;
import gr.aueb.cf.boardgamelibcf.service.exceptions.BggBoardgameNotFoundException;
import gr.aueb.cf.boardgamelibcf.service.exceptions.EntityNotFoundException;

public interface IBoardgameService {
    Boardgame insertBoardgame(BoardgameDTO boardgameDTO);
    Boardgame getBoardgameById(Long id) throws EntityNotFoundException;
    Boardgame getBoardgameByBggId(String bggId) throws BggBoardgameNotFoundException;
}
