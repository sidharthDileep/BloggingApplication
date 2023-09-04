package online.blog.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.blog.app.payload.CommentDTO;
import online.blog.app.service.CommentService;


@RestController
@RequestMapping("/api/v1")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@Valid @PathVariable(value = "postId") long postId, @RequestBody
    CommentDTO commentDTO) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDTO> getCommenntsByPostId(@PathVariable(value = "postId") long postId) {

        return commentService.getCommentsByPostId(postId);

    }


    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(value = "postId") Long postId, @PathVariable(value = "id") Long commentId) {
        CommentDTO commentById = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentById, HttpStatus.OK);

    }
    
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(@Valid @PathVariable(value = "postId") Long postId, @PathVariable(value = "id") Long commentId, @RequestBody CommentDTO commentDTO) {

        CommentDTO updatedComment = commentService.updateComment(postId, commentId, commentDTO);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);

    }
    
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,@PathVariable(value = "id") Long commentId) {
        commentService.deletePost(postId, commentId);
        return new ResponseEntity<>("Commmet deleted successfully", HttpStatus.OK);
    }
}
