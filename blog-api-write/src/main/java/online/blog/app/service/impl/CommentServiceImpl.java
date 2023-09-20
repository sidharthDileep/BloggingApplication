package online.blog.app.service.impl;

import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
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

	// private CommentRepository commentRepository;
	private CommentRepository2 commentRepository2;
	// private PostRepository postRepository;
	private PostRepository2 postRepository2;

//    @Autowired
//	private SequenceGeneratorService service;

	private ModelMapper mapper;

//	@Autowired
//	private KafkaTemplate<String, Object> template;

	public CommentServiceImpl(CommentRepository2 commentRepository, PostRepository2 postRepository,
			ModelMapper mapper) {
		this.commentRepository2 = commentRepository;
		this.postRepository2 = postRepository;
		this.mapper = mapper;
	}

	@Override
	public CommentDTO createComment(long postId, CommentDTO commentDTO) {
		Comment comment = mapToEntity(commentDTO);

		Post post = postRepository2.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		comment.setPost(post);
		Comment newComment = commentRepository2.save(comment);

		// newComment.setId((long) service.getSequenceNumber(Comment.SEQUENCE_NAME));
		
		CommentEvent event=new CommentEvent("CreateComment", newComment);
	       
//        try {
//            CompletableFuture<SendResult<String, Object>> future =  (CompletableFuture<SendResult<String, Object>>) template.send("post-event-topic", event);
//            future.whenComplete((result, ex) -> {
//                if (ex == null) {
//                    System.out.println("Sent message=[" + event.toString() +
//                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
//                } else {
//                    System.out.println("Unable to send message=[" +
//                    		event.toString() + "] due to : " + ex.getMessage());
//                }
//            });
//
//        } catch (Exception ex) {
//            System.out.println("ERROR : "+ ex.getMessage());
//        }

		return mapToDTO(newComment);
	}

	@Override
	public CommentDTO updateComment(Long postId, long commentId, CommentDTO commentRequest) {

		Post post = postRepository2.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = commentRepository2.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to the post");

		}
		comment.setName(commentRequest.getName());
		comment.setEmial(commentRequest.getEmial());
		comment.setBody(commentRequest.getBody());

		Comment updatedComment = commentRepository2.save(comment);
		
		CommentEvent event=new CommentEvent("UpdateComment", updatedComment);
	       
//        try {
//            CompletableFuture<SendResult<String, Object>> future =  (CompletableFuture<SendResult<String, Object>>) template.send("post-event-topic", event);
//            future.whenComplete((result, ex) -> {
//                if (ex == null) {
//                    System.out.println("Sent message=[" + event.toString() +
//                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
//                } else {
//                    System.out.println("Unable to send message=[" +
//                    		event.toString() + "] due to : " + ex.getMessage());
//                }
//            });
//
//        } catch (Exception ex) {
//            System.out.println("ERROR : "+ ex.getMessage());
//        }
        
		return mapToDTO(updatedComment);

	}

	@Override
	public void deletePost(Long postId, Long commentId) {
		Post post = postRepository2.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = commentRepository2.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to the post");

		}
		commentRepository2.delete(comment);
		
		CommentEvent event=new CommentEvent("DeleteComment", comment);
	       
//        try {
//            CompletableFuture<SendResult<String, Object>> future =  (CompletableFuture<SendResult<String, Object>>) template.send("post-event-topic", event);
//            future.whenComplete((result, ex) -> {
//                if (ex == null) {
//                    System.out.println("Sent message=[" + event.toString() +
//                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
//                } else {
//                    System.out.println("Unable to send message=[" +
//                    		event.toString() + "] due to : " + ex.getMessage());
//                }
//            });
//
//        } catch (Exception ex) {
//            System.out.println("ERROR : "+ ex.getMessage());
//        }

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
