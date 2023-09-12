package online.blog.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import online.blog.app.payload.PostDTO;
import online.blog.app.payload.PostResponse;
import online.blog.app.service.PostService;
import online.blog.app.utils.AppConstants;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllPosts() throws Exception {
        // Mock the PostResponse that would be returned by the service
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(Collections.emptyList());
        postResponse.setPageNo(0);
        //postResponse.setPageSize(AppConstants.DEFAULT_PAGE_SIZE);
        postResponse.setTotalElements(0);
        postResponse.setTotalPages(0);
        postResponse.setLast(true);

//        when(postService.getAllPosts(0, AppConstants.DEFAULT_PAGE_SIZE, AppConstants.DEFAULT_SORT_BY, AppConstants.DEFAULT_SORT_DIRECTION))
//                .thenReturn(postResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts")
                .param("pageNo", "0")
                .param("pageSize", AppConstants.DEFAULT_PAGE_SIZE)
                .param("sortBY", AppConstants.DEFAULT_SORT_BY)
                .param("sortDir", AppConstants.DEFAULT_SORT_DIRECTION)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty());
    }

    @Test
    public void testGetPostById() throws Exception {
        // Mock the PostDTO that would be returned by the service
        PostDTO postDTO = new PostDTO();
        postDTO.setId(1L);
        postDTO.setTitle("Test Post");
        postDTO.setDescription("Test Description");
        postDTO.setContent("Test Content");

        when(postService.getPostById(1L)).thenReturn(postDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Post"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Test Content"));
    }

    // Additional test cases for other controller methods can be added here
}