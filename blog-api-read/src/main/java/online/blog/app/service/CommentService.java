package online.blog.app.service;

import java.util.List;

import online.blog.app.payload.CommentDTO;

public interface CommentService {

    List<CommentDTO> getCommentsByPostId(long postId);

    CommentDTO getCommentById(Long postId,Long commentId);
}
