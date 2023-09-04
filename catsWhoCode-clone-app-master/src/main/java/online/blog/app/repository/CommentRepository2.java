package online.blog.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import online.blog.app.entity.Comment;


public interface CommentRepository2 extends MongoRepository<Comment,Long> {
     List<Comment> findByPostId(long postId);
}
