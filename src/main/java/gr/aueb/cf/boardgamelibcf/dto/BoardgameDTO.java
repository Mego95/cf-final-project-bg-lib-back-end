package gr.aueb.cf.boardgamelibcf.dto;

import java.util.ArrayList;
import java.util.List;

public class BoardgameDTO {
    private Long id;
    private String bggId;
    private String boardgameName;
    private Integer minPlayers;
    private Integer maxPlayers;
    private Integer minPlayingTime;
    private Integer maxPlayingTime;
    private Double complexityWeight;
    private List<String> categories = new ArrayList<>();

    public BoardgameDTO () {}

    public BoardgameDTO(Long id, String bggId, String boardgameName, Integer minPlayers, Integer maxPlayers, Integer minPlayingTime,
                        Integer maxPlayingTime, Double complexityWeight, List<String> categories) {
        this.id = id;
        this.bggId = bggId;
        this.boardgameName = boardgameName;
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

    public String getBggId() {
        return bggId;
    }

    public void setBggId(String bggId) {
        this.bggId = bggId;
    }

    public String getBoardgameName() {
        return boardgameName;
    }

    public void setBoardgameName(String boardgameName) {
        this.boardgameName = boardgameName;
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
}
