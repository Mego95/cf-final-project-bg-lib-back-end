package gr.aueb.cf.boardgamelibcf.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User implements UserDetails {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<OwnedBoardgame> ownedBoardgames = new ArrayList<>();

    public User() {}

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<OwnedBoardgame> getOwnedBoardgames() {
        return ownedBoardgames;
    }

    public void setOwnedBoardgames(List<OwnedBoardgame> ownedBoardgames) {
        this.ownedBoardgames = ownedBoardgames;
    }

    public void addOwnedBoardgame(OwnedBoardgame ownedBoardgame) {
        ownedBoardgame.setUser(this);
        this.ownedBoardgames.add(ownedBoardgame);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ownedBoardgames=" + ownedBoardgames +
                '}';
    }
}
