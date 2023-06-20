package gr.aueb.cf.boardgamelibcf.service.exceptions;

public class EntityNotFoundException extends Exception{
    public EntityNotFoundException(Class<?> entityClass, Long id) {
        super("Entity " + entityClass.getSimpleName() + " with id " + id + " does not exist");
    }

    public EntityNotFoundException(String username) {
        super("User with username " + username + " was not found");
    }
}
