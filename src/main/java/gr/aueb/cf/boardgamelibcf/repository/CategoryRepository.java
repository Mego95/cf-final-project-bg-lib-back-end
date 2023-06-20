package gr.aueb.cf.boardgamelibcf.repository;

import gr.aueb.cf.boardgamelibcf.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category getByCategoryTitle(String categoryTitle);
}
