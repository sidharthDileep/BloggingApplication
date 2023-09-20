package online.blog.app.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import online.blog.app.entity.Post;
import online.blog.app.exception.ResourceNotFoundException;
import online.blog.app.payload.PostDTO;
import online.blog.app.payload.PostEvent;
import online.blog.app.payload.PostResponse;
import online.blog.app.repository.PostRepository2;
import online.blog.app.repository.UserRepository2;
import online.blog.app.service.PostService;

@Service
public class PostServiceImpl implements PostService {

//	@Autowired
//	private KafkaTemplate<String, String> kafkaTemplate;
//	
//	@Autowired
//	private KafkaTemplate<String, String> cqrsKafka;

	@Autowired
	private UserRepository2 userRepository;

	String topic = "myTopic";

	@Autowired
	private SequenceGeneratorService service;

	// private PostRepository postRepository;

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
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

//        PageRequest pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy).descending());
//descending order sorting

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);

		Page<Post> posts = postRepository2.findAll(pageable);
		// get content for page object
		posts.getContent();

		List<PostDTO> content = posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());

		return postResponse;
	}

	@Override
	public PostDTO getPostById(long id) {
		Post post = postRepository2.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		return mapToDTO(post);
	}

	@Override
	public List<PostDTO> getPostByCategory(String category) {
		List<Post> posts = postRepository2.findByCategory(category).get();
		// String principal =
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		// System.out.println("UserName : " + username);

		List<PostDTO> postdtos = posts.stream().filter(s -> s.getUser().equals(username)).map(post -> mapToDTO(post))
				.collect(Collectors.toList());
		return postdtos;
	}

	@Override
	public List<PostDTO> getPostBetweenDate(LocalDateTime createdFrom, LocalDateTime createdTo) {
		return postRepository2.findByCreatedAtBetween(createdFrom, createdTo).get().stream().map(post -> mapToDTO(post))
				.collect(Collectors.toList());
	}

	@KafkaListener(topics = "post-event-topic", groupId = "post-event-group")
	public void processProductEvents(PostEvent productEvent) {
		Post post = productEvent.getPost();
		if (productEvent.getEventType().equals("CreatePost")) {
			postRepository2.save(post);
		}
		else if (productEvent.getEventType().equals("UpdatePost")) {
			Post existingPost = postRepository2.findById(post.getId()).get();
			existingPost.setTitle(post.getTitle());
			existingPost.setCategory(post.getCategory());
			existingPost.setDescription(post.getDescription());
			existingPost.setUser(post.getUser());
			existingPost.setComments(post.getComments());
			existingPost.setContent(post.getContent());
			postRepository2.save(existingPost);
		} else {
			if(productEvent.getEventType().equals("DeletePost")){
				postRepository2.delete(post);
			}
		}
	}

}
