package online.lahiru.sprinngbotrestapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import online.lahiru.sprinngbotrestapi.entity.Comment;
import online.lahiru.sprinngbotrestapi.entity.Post;
import online.lahiru.sprinngbotrestapi.entity.Role;
import online.lahiru.sprinngbotrestapi.exception.BlogAPIException;
import online.lahiru.sprinngbotrestapi.exception.ResourceNotFoundException;
import online.lahiru.sprinngbotrestapi.payload.CommentDTO;
import online.lahiru.sprinngbotrestapi.repository.CommentRepository2;
import online.lahiru.sprinngbotrestapi.repository.PostRepository2;
import online.lahiru.sprinngbotrestapi.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    //private CommentRepository commentRepository;
    private CommentRepository2 commentRepository2;
    //private PostRepository postRepository;
    private PostRepository2 postRepository2;
    
    @Autowired
	private SequenceGeneratorService service;

    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository2 commentRepository, PostRepository2 postRepository,ModelMapper mapper) {
        this.commentRepository2 = commentRepository;
        this.postRepository2 = postRepository;
        this.mapper=mapper;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = mapToEntity(commentDTO);

        Post post = postRepository2.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        Comment newComment = commentRepository2.save(comment);
        
        newComment.setId((long) service.getSequenceNumber(Comment.SEQUENCE_NAME));


        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository2.findByPostId(postId);
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());

    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Post post = postRepository2.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment=commentRepository2.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belongs to the post");
        }
        return mapToDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long postId, long commentId, CommentDTO commentRequest) {

        Post post = postRepository2.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment=commentRepository2.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id", commentId));


        if(!comment.getPost().getId().equals(post.getId())){
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belongs to the post");

        }
        comment.setName(commentRequest.getName());
        comment.setEmial(commentRequest.getEmial());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository2.save(comment);
        return mapToDTO(updatedComment);


    }

    @Override
    public void deletePost(Long postId, Long commentId) {
        Post post = postRepository2.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment=commentRepository2.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw  new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belongs to the post");

        }
        commentRepository2.delete(comment);

    }

    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);
//
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setEmial(comment.getEmial());
//        commentDTO.setBody(comment.getBody());

        return commentDTO;
    }

    private Comment mapToEntity(CommentDTO commentDTO) {

        Comment comment = mapper.map(commentDTO, Comment.class);
//
//        Comment comment = new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setName(commentDTO.getName());
//        comment.setEmial(commentDTO.getEmial());
//        comment.setBody(commentDTO.getBody());

        return comment;
    }
}
