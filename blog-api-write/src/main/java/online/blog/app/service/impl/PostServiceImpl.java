package online.blog.app.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import online.blog.app.entity.Post;
import online.blog.app.exception.ResourceNotFoundException;
import online.blog.app.payload.PostDTO;
import online.blog.app.payload.PostEvent;
import online.blog.app.repository.PostRepository2;
import online.blog.app.repository.UserRepository2;
import online.blog.app.service.PostService;

import java.util.concurrent.CompletableFuture;

@Service
public class PostServiceImpl implements PostService {
	
//	@Autowired
//	@Qualifier("producer-1")
//    private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private UserRepository2 userRepository;
	
	String topic = "myTopic";
	
	//@Autowired
	//@Qualifier("producer-2")
    //private KafkaTemplate<String, Object> kafkaTemplate;
	
	 @Autowired
	 private KafkaTemplate<String,Object> template;
	
//	@Autowired
//	private SequenceGeneratorService service;

	//private PostRepository postRepository;
	
	private PostRepository2 postRepository2;
	private ModelMapper mapper;

	public PostServiceImpl(PostRepository2 postRepository, ModelMapper modelMapper) {
		this.postRepository2 = postRepository;
		this.mapper = modelMapper;
	}

	// convert entity to DTO
	private PostDTO mapToDTO(Post post) {
		// PostDTO postDTO = mapper.map(post, PostDTO.class);

		PostDTO postDTO = new PostDTO();
		postDTO.setId(post.getId());
		postDTO.setCategory(post.getCategory());
		postDTO.setTitle(post.getTitle());
		postDTO.setDescription(post.getDescription());
		postDTO.setContent(post.getContent());

		return postDTO;
	}

	// convert dto to entity
	private Post mapToEntiy(PostDTO postDTO) {
		// Post post = mapper.map(postDTO, Post.class);
		Post post = new Post();
		post.setTitle(postDTO.getTitle());
		post.setCategory(postDTO.getCategory());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());
		return post;
	}

	@Override
	public PostDTO createPost(PostDTO postDTO) {
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		//System.out.println("UserName : " + username);
		
		Post post = mapToEntiy(postDTO);
		post.setUser(username);
		//post.setId((long) service.getSequenceNumber(Post.SEQUENCE_NAME));
		
		LocalDateTime date = LocalDate.now().atStartOfDay();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		date.format(formatter);
		
		post.setCreatedAt(date);
		
		Post newPost = postRepository2.save(post);
		
        PostEvent event=new PostEvent("CreatePost", newPost);
       
        try {
            ListenableFuture<SendResult<String, Object>> future =  template.send("post-event-topic", event);
            future.completable().whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + event.toString() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                    		event.toString() + "] due to : " + ex.getMessage());
                }
            });

        } catch (Exception ex) {
            System.out.println("ERROR : "+ ex.getMessage());
        }

		// convert entity to DTO
		PostDTO postResponse = mapToDTO(newPost);
		//kafkaTemplate.send(topic, "Post request Succesful -> " + newPost);

		return postResponse;
	}


	@Override
	public PostDTO updatePost(PostDTO postDTO, long id) {
		// get post by id from the database
		Post post = postRepository2.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(post.getContent());

		Post updatedPost = postRepository2.save(post);
		
		PostEvent event=new PostEvent("UpdatePost", updatedPost);
		
		try {
            ListenableFuture<SendResult<String, Object>> future =   template.send("post-event-topic", event);
            future.completable().whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + event.toString() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                    		event.toString() + "] due to : " + ex.getMessage());
                }
            });

        } catch (Exception ex) {
            System.out.println("ERROR : "+ ex.getMessage());
        }

		return mapToDTO(updatedPost);

	}

	@Override
	public void deletePostById(long id) {
		// get post by id from the database
		Post post = postRepository2.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		postRepository2.delete(post);
		
		PostEvent event=new PostEvent("DeletePost", post);
		
		try {
            ListenableFuture<SendResult<String, Object>> future =  template.send("post-event-topic", event);
            future.completable().whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + event.toString() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                    		event.toString() + "] due to : " + ex.getMessage());
                }
            });

        } catch (Exception ex) {
            System.out.println("ERROR : "+ ex.getMessage());
        }


	}

	@Override
	public void deletePostByName(String title) {
		Optional<Post> post = Optional.ofNullable(postRepository2.findByTitle(title).orElseThrow(() -> new ResourceNotFoundException("post", title, 0)));
		postRepository2.delete(post.get());
		
		PostEvent event=new PostEvent("DeletePost", post.get());
		
		try {
            ListenableFuture<SendResult<String, Object>> future = template.send("post-event-topic", event);
            future.completable().whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + event.toString() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                    		event.toString() + "] due to : " + ex.getMessage());
                }
            });

        } catch (Exception ex) {
            System.out.println("ERROR : "+ ex.getMessage());
        }

	}

	@Override
	public void deleteUser(String user) {
		userRepository.deleteByName(user);
		userRepository.deleteByUsername(user);
		userRepository.deleteByEmail(user);
	}

	@Override
	public void deleteAllPostsOfUser(String user) {
		Optional<Post> post = postRepository2.deleteByUser(user);
		
		PostEvent event=new PostEvent("DeletePost", post.get());
		
		try {
            ListenableFuture<SendResult<String, Object>> future = template.send("post-event-topic", event);
            future.completable().whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + event.toString() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                    		event.toString() + "] due to : " + ex.getMessage());
                }
            });

        } catch (Exception ex) {
            System.out.println("ERROR : "+ ex.getMessage());
        }

	}
}
