package online.blog.app.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import online.blog.app.payload.PostDTO;
import online.blog.app.payload.PostResponse;


public interface PostService {
     PostDTO createPost(PostDTO postDTO);

     PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

     PostDTO getPostById(long id);
     
     List<PostDTO> getPostByCategory(String category);
     
     List<PostDTO> getPostBetweenDate(LocalDateTime localDateTime, LocalDateTime localDateTime2);

     PostDTO updatePost(PostDTO postDTO, long id);

     void deletePostById(long id);
     
     void deletePostByName(String title);

	 void deleteUser(String user);
	 
	 void deleteAllPostsOfUser(String user);
}
