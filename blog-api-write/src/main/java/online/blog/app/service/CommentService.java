package online.blog.app.service;

import java.util.List;

import online.blog.app.payload.CommentDTO;

public interface CommentService {
    CommentDTO createComment(long postId,CommentDTO commentDTO);

    CommentDTO updateComment(Long postId,long commentId, CommentDTO commentRequest);

    void deletePost(Long postId, Long commentId);
}
