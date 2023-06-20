package gr.aueb.cf.boardgamelibcf.service;

import gr.aueb.cf.boardgamelibcf.dto.UserDTO;
import gr.aueb.cf.boardgamelibcf.model.User;
import gr.aueb.cf.boardgamelibcf.repository.UserRepository;
import gr.aueb.cf.boardgamelibcf.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.boardgamelibcf.service.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public User insertUser(UserDTO userDTO) throws UserAlreadyExistsException {
        if (userRepository.getByUsername(userDTO.getUsername()) != null) {
            throw new UserAlreadyExistsException(userDTO.getUsername());
        }

        User userToInsert = convertFromDTO(userDTO);
        return userRepository.save(userToInsert);
    }

    @Override
    public User getUserById(Long id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new EntityNotFoundException(User.class, id);
        return user.get();
    }

    @Override
    public User getUserByUsername(String username) throws EntityNotFoundException{
        User user = userRepository.getByUsername(username);
        if (user == null) throw new EntityNotFoundException(username);
        return user;
    }

    private User convertFromDTO(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUsername(username);
        if (user == null) throw new UsernameNotFoundException("Invalid username or password");
        //TODO
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("read"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), authorities);
    }
}
