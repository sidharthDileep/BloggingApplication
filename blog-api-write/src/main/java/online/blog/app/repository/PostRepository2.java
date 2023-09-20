package online.blog.app.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import online.blog.app.entity.Post;

public interface PostRepository2 extends JpaRepository<Post,Long> {

	Optional<List<Post>> findByCategory(String category);
	Optional<List<Post>> findByCreatedAtBetween(LocalDateTime createdFrom, LocalDateTime createdTo);
	Optional<Post> findByTitle(String title);
	Optional<Post> deleteByUser(String user);
}
