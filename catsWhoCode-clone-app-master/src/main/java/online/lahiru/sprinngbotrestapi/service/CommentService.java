package online.lahiru.sprinngbotrestapi.service;

import online.lahiru.sprinngbotrestapi.payload.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(long postId,CommentDTO commentDTO);

    List<CommentDTO> getCommentsByPostId(long postId);

    CommentDTO getCommentById(Long postId,Long commentId);

    CommentDTO updateComment(Long postId,long commentId, CommentDTO commentRequest);

    void deletePost(Long postId, Long commentId);
}
