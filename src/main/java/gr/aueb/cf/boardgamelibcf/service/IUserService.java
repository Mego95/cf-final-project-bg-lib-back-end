package gr.aueb.cf.boardgamelibcf.service;

import gr.aueb.cf.boardgamelibcf.dto.UserDTO;
import gr.aueb.cf.boardgamelibcf.model.User;
import gr.aueb.cf.boardgamelibcf.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.boardgamelibcf.service.exceptions.UserAlreadyExistsException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService{
    //
    User insertUser(UserDTO userDTO) throws UserAlreadyExistsException;
    User getUserById(Long id) throws EntityNotFoundException;
    User getUserByUsername(String username) throws EntityNotFoundException;
}
