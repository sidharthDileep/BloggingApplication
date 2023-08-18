package online.lahiru.sprinngbotrestapi.service.impl;

import online.lahiru.sprinngbotrestapi.entity.Post;
import online.lahiru.sprinngbotrestapi.exception.ResourceNotFoundException;
import online.lahiru.sprinngbotrestapi.payload.PostDTO;
import online.lahiru.sprinngbotrestapi.payload.PostResponse;
import online.lahiru.sprinngbotrestapi.repository.PostRepository;
import online.lahiru.sprinngbotrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.PageRanges;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	String topic = "myTopic";

	private PostRepository postRepository;
	private ModelMapper mapper;

	public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
		this.postRepository = postRepository;
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
		Post newPost = postRepository.save(post);

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

		Page<Post> posts = postRepository.findAll(pageable);
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
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		return mapToDTO(post);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, long id) {
		// get post by id from the database
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(post.getContent());

		Post updatedPost = postRepository.save(post);

		return mapToDTO(updatedPost);

	}

	@Override
	public void deletePostById(long id) {
		// get post by id from the database
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		postRepository.delete(post);

	}

	@Override
	public List<PostDTO> getPostByCategory(String category) {
		List<Post> posts = postRepository.findByCategory(category).get();
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
	public List<PostDTO> getPostBetweenDate(Date createdFrom, Date createdTo) {
		return postRepository.findByCreatedAtBetween(createdFrom, createdTo).get().stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
	}

	@Override
	public void deletePostByName(String title) {
		Optional<Post> post = Optional.ofNullable(postRepository.findByTitle(title).orElseThrow(() -> new ResourceNotFoundException("post", "id", 00)));
		postRepository.delete(post.get());
	}
}
