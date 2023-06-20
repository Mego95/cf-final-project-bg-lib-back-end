package gr.aueb.cf.boardgamelibcf.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CATEGORIES")
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Column(name = "CATEGORY_TITLE", nullable = false, unique = true)
    private String categoryTitle;

    @ManyToMany(mappedBy = "categories")
    private List<Boardgame> boardgames = new ArrayList<>();

    public Category() {}

    public Category(Long id, String categoryTitle) {
        this.id = id;
        this.categoryTitle = categoryTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public List<Boardgame> getBoardgames() {
        return boardgames;
    }

    public void setBoardgames(List<Boardgame> boardgames) {
        this.boardgames = boardgames;
    }

    public void addBoardgame(Boardgame boardgame) {
        this.boardgames.add(boardgame);
        for (Category category : boardgame.getCategories()) {
            if (category == this) {
                return;
            }
        }
        boardgame.addCategory(this);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryTitle='" + categoryTitle + '\'' +
                ", boardgames=" + boardgames +
                '}';
    }
}
