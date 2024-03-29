package gr.aueb.cf.boardgamelibcf.repository;

import gr.aueb.cf.boardgamelibcf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByUsername(String username);
}
