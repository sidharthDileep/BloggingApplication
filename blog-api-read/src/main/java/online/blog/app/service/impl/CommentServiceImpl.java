package online.blog.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import online.blog.app.entity.Comment;
import online.blog.app.entity.Post;
import online.blog.app.exception.BlogAPIException;
import online.blog.app.exception.ResourceNotFoundException;
import online.blog.app.payload.CommentDTO;
import online.blog.app.payload.CommentEvent;
import online.blog.app.repository.CommentRepository2;
import online.blog.app.repository.PostRepository2;
import online.blog.app.service.CommentService;

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
    
    //@KafkaListener(topics = "comment-event-topic", groupId = "comment-event-group")
	public void processCommentEvents(CommentEvent commentEvent) {
		Comment comment = commentEvent.getComment();
		if (commentEvent.getEventType().equals("CreatePost")) {
			commentRepository2.save(comment);
		}
		else if (commentEvent.getEventType().equals("UpdateComment")) {
			Comment existingComment = commentRepository2.findById(comment.getId()).get();
			existingComment.setBody(comment.getBody());
			existingComment.setName(comment.getName());
			existingComment.setPost(comment.getPost());
			existingComment.setEmial(comment.getEmial());
			commentRepository2.save(existingComment);
		} else {
			if(commentEvent.getEventType().equals("DeleteComment")){
				commentRepository2.delete(comment);
			}
		}
	}
}
