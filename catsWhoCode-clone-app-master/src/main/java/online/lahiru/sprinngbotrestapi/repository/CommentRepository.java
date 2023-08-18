package online.lahiru.sprinngbotrestapi.repository;

import online.lahiru.sprinngbotrestapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Long> {
     List<Comment> findByPostId(long postId);
}
