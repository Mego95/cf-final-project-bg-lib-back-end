package gr.aueb.cf.boardgamelibcf.service;

import gr.aueb.cf.boardgamelibcf.dto.BoardgameDTO;
import gr.aueb.cf.boardgamelibcf.model.Boardgame;
import gr.aueb.cf.boardgamelibcf.model.Category;
import gr.aueb.cf.boardgamelibcf.model.OwnedBoardgame;
import gr.aueb.cf.boardgamelibcf.model.User;
import gr.aueb.cf.boardgamelibcf.repository.BoardgameRepository;
import gr.aueb.cf.boardgamelibcf.repository.CategoryRepository;
import gr.aueb.cf.boardgamelibcf.repository.OwnedBoardgameRepository;
import gr.aueb.cf.boardgamelibcf.repository.UserRepository;
import gr.aueb.cf.boardgamelibcf.service.exceptions.BggBoardgameNotFoundException;
import gr.aueb.cf.boardgamelibcf.service.exceptions.BoardgameAlreadyOwnedException;
import gr.aueb.cf.boardgamelibcf.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OwnedBoardgameServiceImpl implements IOwnedBoardgameService{

    private final OwnedBoardgameRepository ownedBoardgameRepository;
    private final UserRepository userRepository;
    private final BoardgameRepository boardgameRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public OwnedBoardgameServiceImpl(OwnedBoardgameRepository ownedBoardgameRepository, UserRepository userRepository,
                                     BoardgameRepository boardgameRepository, CategoryRepository categoryRepository) {
        this.ownedBoardgameRepository = ownedBoardgameRepository;
        this.userRepository = userRepository;
        this.boardgameRepository = boardgameRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public OwnedBoardgame insertOwnedBoardgame(String username, BoardgameDTO boardgameDTO)
            throws EntityNotFoundException {
        User user = userRepository.getByUsername(username);
        if (user == null) throw new EntityNotFoundException(username);

        Boardgame boardgame = mapBoardgame(boardgameDTO);

        return addBoardgameToOwnedBoardgamesOfUser(user, boardgame, boardgameDTO);
    }

    @Transactional
    @Override
    public OwnedBoardgame insertOwnedBoardgameFromBgg(String username, String bggId)
            throws BoardgameAlreadyOwnedException, EntityNotFoundException, BggBoardgameNotFoundException {
        User user = userRepository.getByUsername(username);
        if (user == null) throw new EntityNotFoundException(username);

        Boardgame boardgame = boardgameRepository.getByBggId(bggId);

        if (boardgame != null) {
            if (!ownedBoardgameRepository.ownedBoardgameExists(user.getId(), boardgame.getId())) {
                OwnedBoardgame ownedBoardgame = new OwnedBoardgame();
                ownedBoardgame.setBoardgame(boardgame);
                ownedBoardgame.setFavorite(false);
                user.addOwnedBoardgame(ownedBoardgame);
                userRepository.save(user);
                return ownedBoardgameRepository.save(ownedBoardgame);
            } else {
                throw new BoardgameAlreadyOwnedException(user.getId(), boardgame.getId());
            }
        } else {
            String url = "http://localhost:3000/api/boardgame/findboardgame/" + bggId;
            RestTemplate restTemplate = new RestTemplate();
            BoardgameDTO boardgameDTO = restTemplate.exchange(url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<BoardgameDTO>(){}).getBody();
            if (boardgameDTO == null) throw new BggBoardgameNotFoundException(bggId);

            boardgame = mapBoardgame(boardgameDTO);
            boardgame.setBggId(bggId);
            return addBoardgameToOwnedBoardgamesOfUser(user, boardgame, boardgameDTO);
        }

    }

    @Transactional
    @Override
    public void deleteOwnedBoardgame(Long ownedBoardgameId) throws EntityNotFoundException {
        Optional<OwnedBoardgame> ownedBoardgameToDelete = ownedBoardgameRepository.findById(ownedBoardgameId);
        if (ownedBoardgameToDelete.isEmpty()) throw new EntityNotFoundException(OwnedBoardgame.class, ownedBoardgameId);
        ownedBoardgameRepository.deleteById(ownedBoardgameId);
    }

    @Transactional
    @Override
    public OwnedBoardgame toggleFavorite(Long ownedBoardgameId) throws EntityNotFoundException {
        Optional<OwnedBoardgame> ownedBoardgame = ownedBoardgameRepository.findById(ownedBoardgameId);

        if (ownedBoardgame.isEmpty()) throw new EntityNotFoundException(OwnedBoardgame.class, ownedBoardgameId);

        OwnedBoardgame updatedOwnedBoardgame = ownedBoardgame.get();
        updatedOwnedBoardgame.setFavorite(!ownedBoardgame.get().isFavorite());

        return ownedBoardgameRepository.save(updatedOwnedBoardgame);
    }

    @Transactional
    @Override
    public List<OwnedBoardgame> getAllOwnedBoardgamesOfUser(String username) throws EntityNotFoundException {
        User user = userRepository.getByUsername(username);
        if (user == null) throw new EntityNotFoundException(username);

        return user.getOwnedBoardgames();
    }

    @Transactional
    @Override
    public OwnedBoardgame getOwnedBoardgameById(Long id) throws EntityNotFoundException {
        Optional<OwnedBoardgame> boardgame = ownedBoardgameRepository.findById(id);
        if (boardgame.isEmpty()) throw new EntityNotFoundException(OwnedBoardgame.class, id);
        return boardgame.get();
    }

    @Override
    public OwnedBoardgame suggestOwnedBoardgameByCategory(String username, String category)
            throws EntityNotFoundException {
        //TODO
        return null;
    }

    private Boardgame mapBoardgame(BoardgameDTO dto) {
        Boardgame boardgame = new Boardgame();
        boardgame.setName(dto.getBoardgameName());
        boardgame.setMinPlayers(dto.getMinPlayers());
        boardgame.setMaxPlayers(dto.getMaxPlayers());
        boardgame.setMinPlayingTime(dto.getMinPlayingTime());
        boardgame.setMaxPlayingTime(dto.getMaxPlayingTime());
        boardgame.setComplexityWeight(dto.getComplexityWeight());
        return boardgame;
    }

    private OwnedBoardgame addBoardgameToOwnedBoardgamesOfUser(User user, Boardgame boardgame, BoardgameDTO boardgameDTO) {
        insertCategories(boardgame, boardgameDTO);
        boardgameRepository.save(boardgame);
        OwnedBoardgame ownedBoardgame = new OwnedBoardgame();
        ownedBoardgame.setBoardgame(boardgame);
        ownedBoardgame.setFavorite(false);
        user.addOwnedBoardgame(ownedBoardgame);
        userRepository.save(user);
        return ownedBoardgameRepository.save(ownedBoardgame);
    }

    private void insertCategories(Boardgame boardgame, BoardgameDTO boardgameDTO) {
        for (String categoryTitle : boardgameDTO.getCategories()) {
            Category category = categoryRepository.getByCategoryTitle(categoryTitle);
            if (category == null) {
                category = new Category();
                category.setCategoryTitle(categoryTitle);
                category.addBoardgame(boardgame);
                categoryRepository.save(category);
            } else {
                category.addBoardgame(boardgame);
            }
        }
    }
}
