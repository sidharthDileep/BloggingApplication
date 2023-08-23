package online.lahiru.sprinngbotrestapi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import online.lahiru.sprinngbotrestapi.entity.Comment;


public interface CommentRepository2 extends MongoRepository<Comment,Long> {
     List<Comment> findByPostId(long postId);
}
