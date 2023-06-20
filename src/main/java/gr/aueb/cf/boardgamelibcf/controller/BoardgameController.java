package gr.aueb.cf.boardgamelibcf.controller;

import gr.aueb.cf.boardgamelibcf.dto.BoardgameDTO;
import gr.aueb.cf.boardgamelibcf.dto.OwnedBoardgameDTO;
import gr.aueb.cf.boardgamelibcf.dto.OwnedBoardgameListDTO;
import gr.aueb.cf.boardgamelibcf.model.Category;
import gr.aueb.cf.boardgamelibcf.model.OwnedBoardgame;
import gr.aueb.cf.boardgamelibcf.service.IOwnedBoardgameService;
import gr.aueb.cf.boardgamelibcf.service.exceptions.BggBoardgameNotFoundException;
import gr.aueb.cf.boardgamelibcf.service.exceptions.BoardgameAlreadyOwnedException;
import gr.aueb.cf.boardgamelibcf.service.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
public class BoardgameController {

    private final IOwnedBoardgameService service;

    @Autowired
    public BoardgameController(IOwnedBoardgameService service) {
        this.service = service;
    }

    @RequestMapping(path = "/boardgames", method = RequestMethod.GET)
    public ResponseEntity<OwnedBoardgameListDTO> getBoardgamesOfUser(Principal principal) {
        List<OwnedBoardgame> ownedBoardgames;
        try {
            ownedBoardgames = service.getAllOwnedBoardgamesOfUser(principal.getName());
            List<OwnedBoardgameDTO> dtos = mapOwnedBoardgamesDTOS(ownedBoardgames);
            OwnedBoardgameListDTO boardgamesDTO = new OwnedBoardgameListDTO(dtos);
            return new ResponseEntity<>(boardgamesDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/boardgames/", method = RequestMethod.POST)
    public ResponseEntity<OwnedBoardgameDTO> insertCustomBoardgame(@RequestBody BoardgameDTO dto, Principal principal) {
        try {
            OwnedBoardgame boardgame = service.insertOwnedBoardgame(principal.getName(), dto);
            OwnedBoardgameDTO ownedBoardgameDTO = mapOwnedBoardgameDTO(boardgame);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("{id}")
                    .buildAndExpand(ownedBoardgameDTO.getId())
                    .toUri();
            return ResponseEntity.created(location).body(ownedBoardgameDTO);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(path = "/boardgames/{bggId}", method = RequestMethod.POST)
    public ResponseEntity<OwnedBoardgameDTO> insertBggBoardgame(@PathVariable("bggId") String bggId, Principal principal) {
        try {
            OwnedBoardgame boardgame = service.insertOwnedBoardgameFromBgg(principal.getName(), bggId);
            OwnedBoardgameDTO ownedBoardgameDTO = mapOwnedBoardgameDTO(boardgame);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("{id}")
                    .buildAndExpand(ownedBoardgameDTO.getId())
                    .toUri();
            return ResponseEntity.created(location).body(ownedBoardgameDTO);
        } catch (EntityNotFoundException | BggBoardgameNotFoundException e1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BoardgameAlreadyOwnedException e2) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(path = "/boardgames/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<OwnedBoardgameDTO> toggleFavoriteOwnedBoardgame(@PathVariable("id") Long id) {
        try {
            OwnedBoardgame boardgame = service.toggleFavorite(id);
            OwnedBoardgameDTO dto = mapOwnedBoardgameDTO(boardgame);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(path = "/boardgames/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<OwnedBoardgameDTO> deleteOwnedBoardgame(@PathVariable("id") Long id) {
        try {
            OwnedBoardgame ownedBoardgameToDelete = service.getOwnedBoardgameById(id);
            OwnedBoardgameDTO dto = mapOwnedBoardgameDTO(ownedBoardgameToDelete);
            service.deleteOwnedBoardgame(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    private List<OwnedBoardgameDTO> mapOwnedBoardgamesDTOS(List<OwnedBoardgame> ownedBoardgames) {
        List<OwnedBoardgameDTO> ownedBoardgameDTOS = new ArrayList<>();
        if (!ownedBoardgames.isEmpty()) {
            for (OwnedBoardgame boardgame : ownedBoardgames) {
                ownedBoardgameDTOS.add(mapOwnedBoardgameDTO(boardgame));
            }
        }
        return ownedBoardgameDTOS;
    }

    private OwnedBoardgameDTO mapOwnedBoardgameDTO(OwnedBoardgame boardgame) {
        OwnedBoardgameDTO dto = new OwnedBoardgameDTO();
        dto.setId(boardgame.getId());
        dto.setBoardgameName(boardgame.getBoardgame().getName());
        dto.setMinPlayers(boardgame.getBoardgame().getMinPlayers());
        dto.setMaxPlayers(boardgame.getBoardgame().getMaxPlayers());
        dto.setMinPlayingTime(boardgame.getBoardgame().getMinPlayingTime());
        dto.setMaxPlayingTime(boardgame.getBoardgame().getMaxPlayingTime());
        dto.setComplexityWeight(boardgame.getBoardgame().getComplexityWeight());
        dto.setFavorite(boardgame.isFavorite());
        dto.setCategories(setCategories(boardgame.getBoardgame().getCategories()));
        return dto;
    }

    private List<String> setCategories(List<Category> categories) {
        List<String> categoryTitles = new ArrayList<>();
        if (!categories.isEmpty()) {
            for (Category category : categories) {
                categoryTitles.add(category.getCategoryTitle());
            }
        }
        return  categoryTitles;
    }
}
