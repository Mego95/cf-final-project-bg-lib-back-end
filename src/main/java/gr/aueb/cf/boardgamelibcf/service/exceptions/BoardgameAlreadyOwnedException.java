package gr.aueb.cf.boardgamelibcf.service.exceptions;

public class BoardgameAlreadyOwnedException extends Exception{
    public BoardgameAlreadyOwnedException(Long userId, Long boardgameId) {
        super("User with id " + userId + " already owns boardgame with id " + boardgameId);
    }
}
