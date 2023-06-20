package gr.aueb.cf.boardgamelibcf.dto;

import java.util.ArrayList;
import java.util.List;

public class OwnedBoardgameDTO {
    private Long id;
    private Long userId;
    private String username;
    private String boardgameName;
    private String bggId;
    private Integer minPlayers;
    private Integer maxPlayers;
    private Integer minPlayingTime;
    private Integer maxPlayingTime;
    private Double complexityWeight;
    private List<String> categories = new ArrayList<>();
    private boolean isFavorite;

    public OwnedBoardgameDTO() {}

    public OwnedBoardgameDTO(Long id, Long userId, String username, String boardgameName, String bggId,
                             Integer minPlayers, Integer maxPlayers, Integer minPlayingTime, Integer maxPlayingTime,
                             Double complexityWeight, List<String> categories) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.boardgameName = boardgameName;
        this.bggId = bggId;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.minPlayingTime = minPlayingTime;
        this.maxPlayingTime = maxPlayingTime;
        this.complexityWeight = complexityWeight;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBoardgameName() {
        return boardgameName;
    }

    public void setBoardgameName(String boardgameName) {
        this.boardgameName = boardgameName;
    }

    public String getBggId() {
        return bggId;
    }

    public void setBggId(String bggId) {
        this.bggId = bggId;
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

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
