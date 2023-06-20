package gr.aueb.cf.boardgamelibcf.service;

import gr.aueb.cf.boardgamelibcf.dto.BoardgameDTO;
import gr.aueb.cf.boardgamelibcf.dto.UserDTO;
import gr.aueb.cf.boardgamelibcf.model.OwnedBoardgame;
import gr.aueb.cf.boardgamelibcf.service.exceptions.BggBoardgameNotFoundException;
import gr.aueb.cf.boardgamelibcf.service.exceptions.BoardgameAlreadyOwnedException;
import gr.aueb.cf.boardgamelibcf.service.exceptions.EntityNotFoundException;

import java.util.List;

public interface IOwnedBoardgameService {
    OwnedBoardgame insertOwnedBoardgame(String username, BoardgameDTO boardgameDTO)
            throws EntityNotFoundException;
    OwnedBoardgame insertOwnedBoardgameFromBgg(String username, String bggId)
            throws BoardgameAlreadyOwnedException, EntityNotFoundException, BggBoardgameNotFoundException;
    void deleteOwnedBoardgame(Long ownedBoardgameId) throws EntityNotFoundException;
    OwnedBoardgame toggleFavorite(Long ownedBoardgameId) throws EntityNotFoundException;
    List<OwnedBoardgame> getAllOwnedBoardgamesOfUser(String username) throws EntityNotFoundException;
    OwnedBoardgame getOwnedBoardgameById(Long id) throws EntityNotFoundException;
    OwnedBoardgame suggestOwnedBoardgameByCategory(String username, String category) throws EntityNotFoundException;
}
