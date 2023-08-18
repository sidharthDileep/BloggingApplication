package online.lahiru.sprinngbotrestapi.controller;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import online.lahiru.sprinngbotrestapi.payload.PostDTO;
import online.lahiru.sprinngbotrestapi.payload.PostResponse;
import online.lahiru.sprinngbotrestapi.service.PostService;
import online.lahiru.sprinngbotrestapi.utils.AppConstants;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	String topic = "myTopic";

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create blog post rest API
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
    	kafkaTemplate.send(topic, "Post request received -> " + postDTO);
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    //get all post rest API
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBY", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
    	kafkaTemplate.send(topic, "Get all request received ");
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    //get post by id rest API
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostByIdv1(@PathVariable(name = "id") long id) {
    	kafkaTemplate.send(topic, "GET Request Received -> ID : " + id);
        return ResponseEntity.ok(postService.getPostById(id));
    }
    
    @GetMapping("category/{category}")
    public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable(name = "category") String category) {
    	kafkaTemplate.send(topic, "GET Request with Category -> Category : " + category);
        return ResponseEntity.ok(postService.getPostByCategory(category));
    }
    
    @GetMapping("between")
    public ResponseEntity<List<PostDTO>> getPostBetweenDate(Date durationFrom, Date durationTo) {
    	kafkaTemplate.send(topic, "GET Request with Duration range -> From : " + durationFrom + " To : " + durationTo);
        return ResponseEntity.ok(postService.getPostBetweenDate(durationFrom, durationTo));
    }

    //update update post API
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable(name = "id") long id) {
    	kafkaTemplate.send(topic, "PUT Request Received -> " + postDTO + " for id :" + id );
        PostDTO postResponce = postService.updatePost(postDTO, id);
        return new ResponseEntity<>(postResponce, HttpStatus.OK);

    }

    //    delete post rest Api
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post deleted successfully !", HttpStatus.OK);
    }
    
    @DeleteMapping("delete/{postTitle}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "postTitle") String title) {
        postService.deletePostByName(title);
        return new ResponseEntity<>("Post deleted successfully !", HttpStatus.OK);
    }
}
