package online.blog.app.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import online.blog.app.payload.PostDTO;
import online.blog.app.payload.PostResponse;


public interface PostService {

     PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

     PostDTO getPostById(long id);
     
     List<PostDTO> getPostByCategory(String category);
     
     List<PostDTO> getPostBetweenDate(LocalDateTime localDateTime, LocalDateTime localDateTime2);

}
