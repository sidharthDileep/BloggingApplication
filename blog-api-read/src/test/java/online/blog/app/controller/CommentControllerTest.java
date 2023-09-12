package online.blog.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import online.blog.app.payload.CommentDTO;
import online.blog.app.service.CommentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateComment() throws Exception {
        // Mock the CommentDTO that would be returned by the service
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setName("Test User");
        commentDTO.setEmial("test@example.com");
        commentDTO.setBody("Test Comment");

        when(commentService.createComment(1L, commentDTO)).thenReturn(commentDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test User"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").value("Test Comment"));
    }

    @Test
    public void testGetCommentsByPostId() throws Exception {
        // Mock the CommentDTO list that would be returned by the service
        CommentDTO comment1 = new CommentDTO();
        comment1.setId(1L);
        comment1.setName("User 1");
        comment1.setEmial("user1@example.com");
        comment1.setBody("Comment 1");

        CommentDTO comment2 = new CommentDTO();
        comment2.setId(2L);
        comment2.setName("User 2");
        comment2.setEmial("user2@example.com");
        comment2.setBody("Comment 2");

        when(commentService.getCommentsByPostId(1L)).thenReturn(List.of(comment1, comment2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/1/comments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("User 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("user1@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].body").value("Comment 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("User 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value("user2@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].body").value("Comment 2"));
    }

    // Additional test cases for other controller methods can be added here
}
