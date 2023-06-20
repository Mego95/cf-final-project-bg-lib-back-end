package gr.aueb.cf.boardgamelibcf.controller;

import gr.aueb.cf.boardgamelibcf.dto.TokenDTO;
import gr.aueb.cf.boardgamelibcf.dto.UserDTO;
import gr.aueb.cf.boardgamelibcf.model.User;
import gr.aueb.cf.boardgamelibcf.service.IUserService;
import gr.aueb.cf.boardgamelibcf.service.TokenService;
import gr.aueb.cf.boardgamelibcf.service.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class AuthController {

    private final TokenService tokenService;
    private final IUserService userService;

    public AuthController(TokenService tokenService, IUserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/token")
    public TokenDTO token(Authentication authentication) {
        String token = tokenService.generateToken(authentication);
        return new TokenDTO(200, token);
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO dto) {
        try {
            User user = userService.insertUser(dto);
            UserDTO userDTO = map(user);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(userDTO.getId())
                    .toUri();
            return ResponseEntity.created(location).body(userDTO);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private UserDTO map(User user) {
        return new UserDTO(user.getId(), user.getUsername(), "");
    }
}
