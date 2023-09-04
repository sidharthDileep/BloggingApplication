package online.blog.app.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import online.blog.app.entity.Post;

public interface PostRepository2 extends MongoRepository<Post,Long> {

	Optional<List<Post>> findByCategory(String category);
	Optional<List<Post>> findByCreatedAtBetween(LocalDateTime createdFrom, LocalDateTime createdTo);
	Optional<Post> findByTitle(String title);
	void deleteByUser(String user);
}
