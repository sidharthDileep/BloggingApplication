package online.blog.app.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import online.blog.app.entity.Post;
import online.blog.app.exception.ResourceNotFoundException;
import online.blog.app.payload.PostDTO;
import online.blog.app.payload.PostResponse;
import online.blog.app.repository.PostRepository2;
import online.blog.app.repository.UserRepository2;
import online.blog.app.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private UserRepository2 userRepository;
	
	String topic = "myTopic";
	
	@Autowired
	private SequenceGeneratorService service;

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

//        Post post = new Post();
//        post.setTitle(postDTO.getTitle());
//        post.setDescription(postDTO.getDescription());
//        post.setContent(postDTO.getContent());
		String username;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		System.out.println("UserName : " + username);
		Post post = mapToEntiy(postDTO);
		post.setUser(username);
		post.setId((long) service.getSequenceNumber(Post.SEQUENCE_NAME));
		
		LocalDateTime date = LocalDate.now().atStartOfDay();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-DD-YYYY");
		date.format(formatter);
		
		post.setCreatedAt(date);
		
		Post newPost = postRepository2.save(post);

		// convert entity to DTO
		PostDTO postResponse = mapToDTO(newPost);
//        PostDTO postResponse = new PostDTO();
//
//        postResponse.setId(newPost.getId());
//        postResponse.setTitle(newPost.getTitle());
//        postResponse.setDescription(newPost.getDescription());
//        postResponse.setContent(newPost.getContent());
		kafkaTemplate.send(topic, "Post request Succesful -> " + newPost);

		return postResponse;
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
	public PostDTO updatePost(PostDTO postDTO, long id) {
		// get post by id from the database
		Post post = postRepository2.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(post.getContent());

		Post updatedPost = postRepository2.save(post);

		return mapToDTO(updatedPost);

	}

	@Override
	public void deletePostById(long id) {
		// get post by id from the database
		Post post = postRepository2.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		postRepository2.delete(post);

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
		
		System.out.println("UserName : " + username);

		List<PostDTO> postdtos = posts.stream().filter(s -> s.getUser().equals(username)).map(post -> mapToDTO(post))
				.collect(Collectors.toList());
		return postdtos;
	}

	@Override
	public List<PostDTO> getPostBetweenDate(LocalDateTime createdFrom, LocalDateTime createdTo) {
		return postRepository2.findByCreatedAtBetween(createdFrom, createdTo).get().stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
	}

	@Override
	public void deletePostByName(String title) {
		Optional<Post> post = Optional.ofNullable(postRepository2.findByTitle(title).orElseThrow(() -> new ResourceNotFoundException("post", "id", 00)));
		postRepository2.delete(post.get());
	}

	@Override
	public void deleteUser(String user) {
		userRepository.deleteByName(user);
		userRepository.deleteByUsername(user);
		userRepository.deleteByEmail(user);
	}

	@Override
	public void deleteAllPostsOfUser(String user) {
		postRepository2.deleteByUser(user);
		
//		for(int i = 0; i < list.size(); i++) {
//			if(list.get(i).getUser().equals(user)) {
//				postRepository2.deleteById(list.get(i).getId());
//			}
//		}
	}
}
