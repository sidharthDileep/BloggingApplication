package online.blog.app.service;

import java.util.List;

import online.blog.app.payload.CommentDTO;

public interface CommentService {
    CommentDTO createComment(long postId,CommentDTO commentDTO);

    List<CommentDTO> getCommentsByPostId(long postId);

    CommentDTO getCommentById(Long postId,Long commentId);

    CommentDTO updateComment(Long postId,long commentId, CommentDTO commentRequest);

    void deletePost(Long postId, Long commentId);
}
