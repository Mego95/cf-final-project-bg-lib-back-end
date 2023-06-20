package gr.aueb.cf.boardgamelibcf.service;

import gr.aueb.cf.boardgamelibcf.dto.BoardgameDTO;
import gr.aueb.cf.boardgamelibcf.model.Boardgame;
import gr.aueb.cf.boardgamelibcf.model.Category;
import gr.aueb.cf.boardgamelibcf.repository.BoardgameRepository;
import gr.aueb.cf.boardgamelibcf.repository.CategoryRepository;
import gr.aueb.cf.boardgamelibcf.service.exceptions.BggBoardgameNotFoundException;
import gr.aueb.cf.boardgamelibcf.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardgameServiceImpl implements IBoardgameService{

    private final BoardgameRepository boardgameRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BoardgameServiceImpl(BoardgameRepository boardgameRepository, CategoryRepository categoryRepository) {
        this.boardgameRepository = boardgameRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public Boardgame insertBoardgame(BoardgameDTO boardgameDTO) {
        Boardgame boardgameToBeInserted = convertToBoardgameFromBgg(boardgameDTO);

        return boardgameRepository.save(boardgameToBeInserted);
    }

    @Override
    public Boardgame getBoardgameById(Long id) throws EntityNotFoundException {
        Optional<Boardgame> boardgame = boardgameRepository.findById(id);
        if (boardgame.isEmpty()) throw new EntityNotFoundException(Boardgame.class, id);
        return boardgame.get();
    }

    @Override
    public Boardgame getBoardgameByBggId(String bggId) throws BggBoardgameNotFoundException {
        Boardgame boardgame = boardgameRepository.getByBggId(bggId);
        if (boardgame == null) throw new BggBoardgameNotFoundException(bggId);
        return boardgame;
    }

    private Boardgame convertToBoardgameFromBgg(BoardgameDTO dto) {
        Boardgame boardgame = new Boardgame();

        boardgame.setBggId(dto.getBggId());
        boardgame.setName(dto.getBoardgameName());
        boardgame.setMinPlayers(dto.getMinPlayers());
        boardgame.setMaxPlayers(dto.getMaxPlayers());
        boardgame.setMinPlayingTime(dto.getMinPlayingTime());
        boardgame.setMaxPlayingTime(dto.getMaxPlayingTime());
        boardgame.setComplexityWeight(dto.getComplexityWeight());
        boardgame.setCategories(createCategoriesFromBoardgameDTO(dto));

        return boardgame;
    }

    private List<Category> createCategoriesFromBoardgameDTO(BoardgameDTO dto) {
        List<Category> categories = new ArrayList<>();
        for (String categoryTitle : dto.getCategories()) {
            Category category = categoryRepository.getByCategoryTitle(categoryTitle);
            if (category == null) {
                Category newCategory = new Category();
                newCategory.setCategoryTitle(categoryTitle);
                categories.add(newCategory);
            } else {
                categories.add(category);
            }
        }
        return categories;
    }
}
