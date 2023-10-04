//package onlineblog.app.service.impl;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.Spy;
//import org.modelmapper.ModelMapper;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import online.blog.app.entity.Post;
//import online.blog.app.exception.ResourceNotFoundException;
//import online.blog.app.payload.PostDTO;
//import online.blog.app.repository.PostRepository2;
//import online.blog.app.repository.UserRepository2;
//import online.blog.app.service.impl.PostServiceImpl;
//
//class PostServiceImplTest {
//
//    @InjectMocks
//    private PostServiceImpl postService;
//
//    @Mock
//    private PostRepository2 postRepository2;
//
//    @Mock
//    private UserRepository2 userRepository;
//
//    @Mock
//    private KafkaTemplate<String, String> kafkaTemplate;
//
////    @Mock
////    private SequenceGeneratorService sequenceGeneratorService;
//
//    @Spy
//    private ModelMapper modelMapper;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void testCreatePost() {
//        // Mock the SecurityContext to provide a mock user principal
//        SecurityContext securityContext = mock(SecurityContext.class);
//        SecurityContextHolder.setContext(securityContext);
//        when(securityContext.getAuthentication().getPrincipal()).thenReturn("testUser");
//
//        // Mock the sequenceGeneratorService to return a specific value
//        //when(sequenceGeneratorService.getSequenceNumber(Post.SEQUENCE_NAME)).thenReturn(1);
//
//        PostDTO postDTO = new PostDTO();
//        postDTO.setTitle("Test Post");
//        postDTO.setCategory("Test Category");
//        postDTO.setDescription("Test Description");
//        postDTO.setContent("Test Content");
//
//        LocalDateTime now = LocalDateTime.now();
//
//        Post savedPost = new Post();
//        savedPost.setId(1L);
//        savedPost.setTitle("Test Post");
//        savedPost.setCategory("Test Category");
//        savedPost.setDescription("Test Description");
//        savedPost.setContent("Test Content");
//        savedPost.setUser("testUser");
//        savedPost.setCreatedAt(now);
//
//        when(postRepository2.save(any())).thenReturn(savedPost);
//
//        PostDTO result = postService.createPost(postDTO);
//
//        assertNotNull(result);
//        assertEquals(1L, result.getId());
//        assertEquals("Test Post", result.getTitle());
//        assertEquals("Test Category", result.getCategory());
//        assertEquals("Test Description", result.getDescription());
//        assertEquals("Test Content", result.getContent());
//
//        verify(kafkaTemplate, times(1)).send(anyString(), anyString());
//    }
//
//    @Test
//    void testUpdatePost() {
//        long postId = 1L;
//        PostDTO postDTO = new PostDTO();
//        postDTO.setTitle("Updated Post");
//
//        Post existingPost = new Post();
//        existingPost.setId(postId);
//        existingPost.setTitle("Test Post");
//
//        when(postRepository2.findById(postId)).thenReturn(Optional.of(existingPost));
//        when(postRepository2.save(any(Post.class))).thenReturn(existingPost);
//
//        PostDTO result = postService.updatePost(postDTO, postId);
//
//        assertNotNull(result);
//        assertEquals(postId, result.getId());
//        assertEquals("Updated Post", result.getTitle());
//    }
//
//    @Test
//    void testUpdatePostThrowsExceptionWhenPostNotFound() {
//        long postId = 1L;
//        PostDTO postDTO = new PostDTO();
//        postDTO.setTitle("Updated Post");
//
//        when(postRepository2.findById(postId)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> postService.updatePost(postDTO, postId));
//    }
//
//    @Test
//    void testDeletePostById() {
//        long postId = 1L;
//        Post post = new Post();
//        post.setId(postId);
//
//        when(postRepository2.findById(postId)).thenReturn(Optional.of(post));
//
//        postService.deletePostById(postId);
//
//        verify(postRepository2, times(1)).delete(post);
//    }
//
//    @Test
//    void testDeletePostByIdThrowsExceptionWhenPostNotFound() {
//        long postId = 1L;
//        when(postRepository2.findById(postId)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> postService.deletePostById(postId));
//    }
//
//    @Test
//    void testDeletePostByName() {
//        String title = "Test Post";
//
//        when(postRepository2.findByTitle(title)).thenReturn(Optional.of(new Post()));
//
//        postService.deletePostByName(title);
//
//        verify(postRepository2, times(1)).delete(any(Post.class));
//    }
//
//    @Test
//    void testDeletePostByNameThrowsExceptionWhenPostNotFound() {
//        String title = "Test Post";
//
//        when(postRepository2.findByTitle(title)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> postService.deletePostByName(title));
//    }
//
//    @Test
//    void testDeleteUser() {
//        String username = "testUser";
//        postService.deleteUser(username);
//        verify(userRepository, times(1)).deleteByName(username);
//        verify(userRepository, times(1)).deleteByUsername(username);
//        verify(userRepository, times(1)).deleteByEmail(username);
//    }
//
//    @Test
//    void testDeleteAllPostsOfUser() {
//        String username = "testUser";
//
//        //when(postRepository2.deleteByUser(username)).thenReturn(1L);
//
//        postService.deleteAllPostsOfUser(username);
//
//        verify(postRepository2, times(1)).deleteByUser(username);
//    }
//}
