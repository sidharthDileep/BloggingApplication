package online.blog.app.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
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

import online.blog.app.payload.PostDTO;
import online.blog.app.payload.PostResponse;
import online.blog.app.service.PostService;
import online.blog.app.utils.AppConstants;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	String topic = "myTopic";

    private PostService postService;
    
    List<String> messages = new ArrayList<>();

    public PostController(PostService postService) {
        this.postService = postService;
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
    public ResponseEntity<List<PostDTO>> getPostBetweenDate(String durationFrom, String durationTo) {
    	kafkaTemplate.send(topic, "GET Request with Duration range -> From : " + durationFrom + " To : " + durationTo);
    	
		///DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-DD-YYYY");
		//System.out.println(LocalDate.parse(durationFrom, formatter).atStartOfDay());

		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.US);

		  LocalDateTime localDateTime = LocalDate.parse(durationFrom, formatter).atStartOfDay();
		  System.out.println(localDateTime);
        return ResponseEntity.ok(postService.getPostBetweenDate(LocalDate.parse(durationFrom, formatter).atStartOfDay(), LocalDate.parse(durationTo, formatter).atStartOfDay()));
    }

    //update update post API
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable(name = "id") long id) {
    	kafkaTemplate.send(topic, "PUT Request Received -> " + postDTO + " for id :" + id );
        PostDTO postResponce = postService.updatePost(postDTO, id);
        return new ResponseEntity<>(postResponce, HttpStatus.OK);

    }
}
