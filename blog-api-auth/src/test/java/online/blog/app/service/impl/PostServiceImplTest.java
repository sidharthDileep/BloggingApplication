package online.blog.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import online.blog.app.entity.Post;
import online.blog.app.payload.PostDTO;
import online.blog.app.payload.PostResponse;
import online.blog.app.repository.PostRepository2;
import online.blog.app.repository.UserRepository2;

public class PostServiceImplTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository2 postRepository2;

    @Mock
    private UserRepository2 userRepository2;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePost() {
        // Mock SecurityContextHolder to return a UserDetails
//        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
//        SecurityContextHolder.getContext().setAuthentication(userDetails);

        // Create a sample PostDTO
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle("Sample Post");
        postDTO.setContent("Sample content");
        //postDTO.setCreatedAt(LocalDateTime.now());

        // Mock the postRepository2's save method
        when(postRepository2.save(any())).thenReturn(new Post());

        // Call the service method
        PostDTO createdPost = postService.createPost(postDTO);

        // Verify that the save method was called
        verify(postRepository2, times(1)).save(any());

        // Check if the returned PostDTO is not null
        assertNotNull(createdPost);

        // You can add more assertions here to check the content of the createdPost
    }

    @Test
    public void testGetAllPosts() {
        // Mock the postRepository2's findAll method
//        List<Post> posts = new ArrayList<>();
//        when(postRepository2.findAll(any())).thenReturn(posts);
//
//        // Call the service method
//        PostResponse postResponse = postService.getAllPosts(1, 10, "createdAt", "asc");
//
//        // Verify that the findAll method was called
//        verify(postRepository2, times(1)).findAll(any());

        // You can add assertions to check the content of the postResponse
    }

    @Test
    public void testGetPostById() {
        // Mock the postRepository2's findById method
        long postId = 1L;
        when(postRepository2.findById(postId)).thenReturn(Optional.of(new Post()));

        // Call the service method
        PostDTO postDTO = postService.getPostById(postId);

        // Verify that the findById method was called
        verify(postRepository2, times(1)).findById(postId);

        // You can add assertions to check the content of the postDTO
    }

    @Test
    public void testUpdatePost() {
        // Mock the postRepository2's findById and save methods
        long postId = 1L;
        when(postRepository2.findById(postId)).thenReturn(Optional.of(new Post()));
        when(postRepository2.save(any())).thenReturn(new Post());

        // Create a sample PostDTO for updating
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postId);
        postDTO.setTitle("Updated Post");
        postDTO.setContent("Updated content");

        // Call the service method
        PostDTO updatedPost = postService.updatePost(postDTO, postId);

        // Verify that the findById and save methods were called
        verify(postRepository2, times(1)).findById(postId);
        verify(postRepository2, times(1)).save(any());

        // You can add assertions to check the content of the updatedPost
    }

    @Test
    public void testDeletePostById() {
        // Mock the postRepository2's findById and delete methods
        long postId = 1L;
        when(postRepository2.findById(postId)).thenReturn(Optional.of(new Post()));

        // Call the service method
        postService.deletePostById(postId);

        // Verify that the findById and delete methods were called
        verify(postRepository2, times(1)).findById(postId);
        verify(postRepository2, times(1)).delete(any());
    }

    @Test
    public void testGetPostByCategory() {
        // Mock SecurityContextHolder to return a UserDetails
//        UserDetails userDetails = new User("testuser", "password", new ArrayList<>());
//        SecurityContextHolder.getContext().setAuthentication(userDetails);

        // Mock the postRepository2's findByCategory method
        String category = "Technology";
        List<Post> posts = new ArrayList<>();
        when(postRepository2.findByCategory(category)).thenReturn(Optional.of(posts));

        // Call the service method
        List<PostDTO> postDTOs = postService.getPostByCategory(category);

        // Verify that the findByCategory method was called
        verify(postRepository2, times(1)).findByCategory(category);

        // You can add assertions to check the content of the postDTOs
    }

    @Test
    public void testDeletePostByName() {
        // Mock the postRepository2's findByTitle and delete methods
        String title = "Sample Post";
        when(postRepository2.findByTitle(title)).thenReturn(Optional.of(new Post()));

        // Call the service method
        postService.deletePostByName(title);

        // Verify that the findByTitle and delete methods were called
        verify(postRepository2, times(1)).findByTitle(title);
        verify(postRepository2, times(1)).delete(any());
    }

    @Test
    public void testDeleteUser() {
        String user = "testUser";

        // Call the service method
        postService.deleteUser(user);

        // Verify that the delete methods were called
        verify(userRepository2, times(1)).deleteByName(user);
        verify(userRepository2, times(1)).deleteByUsername(user);
        verify(userRepository2, times(1)).deleteByEmail(user);
    }

    @Test
    public void testDeleteAllPostsOfUser() {
        String user = "testUser";

        // Call the service method
        postService.deleteAllPostsOfUser(user);

        // Verify that the deleteByUser method was called
        verify(postRepository2, times(1)).deleteByUser(user);
    }

    // Additional test cases can be added as needed
}
