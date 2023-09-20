package online.blog.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import online.blog.app.entity.Comment;


public interface CommentRepository2 extends JpaRepository<Comment,Long> {
     List<Comment> findByPostId(long postId);
}
