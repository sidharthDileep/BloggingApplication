package online.blog.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.blog.app.payload.PostDTO;
import online.blog.app.service.PostService;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
	
	@Autowired
    private KafkaTemplate<String, Object> template;
	
	String topic = "myTopic";

    private PostService postService;
    
    List<String> messages = new ArrayList<>();

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create blog post rest API
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
    	//kafkaTemplate.send(topic, "Post request received -> " + postDTO);
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    //update update post API
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable(name = "id") long id) {
    	//kafkaTemplate.send(topic, "PUT Request Received -> " + postDTO + " for id :" + id );
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
    
    @DeleteMapping("deleteUser/{user}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "user") String user) {
        postService.deleteUser(user);
        //template.send("deletePost", user);
        return new ResponseEntity<>("User deleted successfully !", HttpStatus.OK);
    }
    
    //@KafkaListener(groupId = "user", topics = "deletePost")
    		//, containerFactory = "kafkaListenerContainerFactory")
	public void getMsgFromTopic(String data) {
		postService.deleteAllPostsOfUser(data);
	}
}
