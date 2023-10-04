//package online.blog.app.service.impl;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.modelmapper.ModelMapper;
//import org.springframework.http.HttpStatus;
//
//import online.blog.app.entity.Comment;
//import online.blog.app.entity.Post;
//import online.blog.app.exception.BlogAPIException;
//import online.blog.app.exception.ResourceNotFoundException;
//import online.blog.app.payload.CommentDTO;
//import online.blog.app.repository.CommentRepository2;
//import online.blog.app.repository.PostRepository2;
//
//public class CommentServiceImplTest {
//
//    @InjectMocks
//    private CommentServiceImpl commentService;
//
//    @Mock
//    private CommentRepository2 commentRepository2;
//
//    @Mock
//    private PostRepository2 postRepository2;
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testGetCommentsByPostId() {
//        // Mock the commentRepository2's findByPostId method
//        long postId = 1L;
//        List<Comment> comments = new ArrayList<>();
//        when(commentRepository2.findByPostId(postId)).thenReturn(comments);
//
//        // Call the service method
//        List<CommentDTO> commentDTOs = commentService.getCommentsByPostId(postId);
//
//        // Verify that the findByPostId method was called
//        verify(commentRepository2, times(1)).findByPostId(postId);
//
//        // You can add assertions to check the content of the commentDTOs
//    }
//
//    @Test
//    public void testGetCommentById() {
//        // Mock the postRepository2's findById method
//        long postId = 1L;
//        long commentId = 2L;
//        when(postRepository2.findById(postId)).thenReturn(Optional.of(new Post()));
//        when(commentRepository2.findById(commentId)).thenReturn(Optional.of(new Comment()));
//
//        // Call the service method
//        CommentDTO commentDTO = commentService.getCommentById(postId, commentId);
//
//        // Verify that the findById methods were called
//        verify(postRepository2, times(1)).findById(postId);
//        verify(commentRepository2, times(1)).findById(commentId);
//
//        // You can add assertions to check the content of the commentDTO
//    }
//
//    // Additional test cases can be added as needed
//}