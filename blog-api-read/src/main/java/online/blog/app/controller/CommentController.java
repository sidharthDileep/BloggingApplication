package online.blog.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
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
    
}
