package gr.aueb.cf.boardgamelibcf.dto;

import java.util.List;

public class OwnedBoardgameListDTO {
    List<OwnedBoardgameDTO> boardgames;

    public OwnedBoardgameListDTO () {}

    public OwnedBoardgameListDTO(List<OwnedBoardgameDTO> boardgames) {
        this.boardgames = boardgames;
    }

    public List<OwnedBoardgameDTO> getBoardgames() {
        return boardgames;
    }

    public void setBoardgames(List<OwnedBoardgameDTO> boardgames) {
        this.boardgames = boardgames;
    }
}
