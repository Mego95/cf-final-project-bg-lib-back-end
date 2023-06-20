package gr.aueb.cf.boardgamelibcf.model;

import javax.persistence.*;

@Entity
@Table(name = "OWNED_BOARDGAMES")
public class OwnedBoardgame {
    @Id
    @Column(name = "OWNED_BOARDGAME_ID")
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "BOARDGAME_ID", nullable = false)
    private Boardgame boardgame;

    @Column(name = "FAVORITE", nullable = false)
    private boolean isFavorite;

    public OwnedBoardgame() {}

    public OwnedBoardgame(Long id, User user, Boardgame boardgame, boolean isFavorite) {
        this.id = id;
        this.user = user;
        this.boardgame = boardgame;
        this.isFavorite = isFavorite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boardgame getBoardgame() {
        return boardgame;
    }

    public void setBoardgame(Boardgame boardgame) {
        this.boardgame = boardgame;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "OwnedBoardgame{" +
                "id=" + id +
                ", user=" + user +
                ", boardgame=" + boardgame +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
