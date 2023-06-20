package gr.aueb.cf.boardgamelibcf.service.exceptions;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String username) {
        super("User with username " + username + " already exists");
    }
}
