package gr.aueb.cf.boardgamelibcf.service.exceptions;

public class BggBoardgameNotFoundException extends Exception{
    public BggBoardgameNotFoundException(String bggId) {
        super("Boardgame with BGG ID " + bggId + " is not found");
    }
}
