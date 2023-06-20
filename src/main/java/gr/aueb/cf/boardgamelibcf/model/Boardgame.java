package gr.aueb.cf.boardgamelibcf.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOARDGAMES")
public class Boardgame {
    @Id
    @GeneratedValue
    @Column(name = "BOARDGAME_ID")
    private Long id;

    @Column(name = "BGG_ID", unique = true, nullable = true)
    private String bggId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "MIN_PLAYERS")
    private Integer minPlayers;

    @Column(name = "MAX_PLAYERS")
    private Integer maxPlayers;

    @Column(name = "MIN_PLAYING_TIME")
    private Integer minPlayingTime;

    @Column(name = "MAX_PLAYING_TIME")
    private Integer maxPlayingTime;

    @Column(name = "COMPLEXITY_WEIGHT")
    private Double complexityWeight;

    @OneToMany(mappedBy = "boardgame")
    private List<OwnedBoardgame> ownedBoardgames = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "BOARDGAMES_CATEGORIES",
            joinColumns = @JoinColumn(name = "BOARDGAME_ID"),
            inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID")
    )
    private List<Category> categories = new ArrayList<>();

    public Boardgame() {}

    public Boardgame(Long id, String bggId, String name, Integer minPlayers, Integer maxPlayers, Integer minPlayingTime,
                     Integer maxPlayingTime, Double complexityWeight) {
        this.id = id;
        this.bggId = bggId;
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.minPlayingTime = minPlayingTime;
        this.maxPlayingTime = maxPlayingTime;
        this.complexityWeight = complexityWeight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBggId() {
        return bggId;
    }

    public void setBggId(String bggId) {
        this.bggId = bggId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(Integer minPlayers) {
        this.minPlayers = minPlayers;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Integer getMinPlayingTime() {
        return minPlayingTime;
    }

    public void setMinPlayingTime(Integer minPlayingTime) {
        this.minPlayingTime = minPlayingTime;
    }

    public Integer getMaxPlayingTime() {
        return maxPlayingTime;
    }

    public void setMaxPlayingTime(Integer maxPlayingTime) {
        this.maxPlayingTime = maxPlayingTime;
    }

    public Double getComplexityWeight() {
        return complexityWeight;
    }

    public void setComplexityWeight(Double complexityWeight) {
        this.complexityWeight = complexityWeight;
    }

    public List<OwnedBoardgame> getOwnedBoardgames() {
        return ownedBoardgames;
    }

    public void setOwnedBoardgames(List<OwnedBoardgame> ownedBoardgames) {
        this.ownedBoardgames = ownedBoardgames;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
        for (Boardgame boardgame : category.getBoardgames()) {
            if (boardgame == this) {
                return;
            }
        }
        category.addBoardgame(this);
    }

    @Override
    public String toString() {
        return "Boardgame{" +
                "id=" + id +
                ", bggId='" + bggId + '\'' +
                ", name='" + name + '\'' +
                ", minPlayers=" + minPlayers +
                ", maxPlayers=" + maxPlayers +
                ", minPlayingTime=" + minPlayingTime +
                ", maxPlayingTime=" + maxPlayingTime +
                ", complexityWeight=" + complexityWeight +
                ", ownedBoardgames=" + ownedBoardgames +
                ", categories=" + categories +
                '}';
    }
}
