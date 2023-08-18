package online.lahiru.sprinngbotrestapi.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import online.lahiru.sprinngbotrestapi.entity.Post;

public interface PostRepository extends JpaRepository<Post,Long> {

	Optional<List<Post>> findByCategory(String category);
	Optional<List<Post>> findByCreatedAtBetween(Date createdFrom, Date createdTo);
	Optional<Post> findByTitle(String title);
}
