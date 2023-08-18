package online.lahiru.sprinngbotrestapi.service;

import online.lahiru.sprinngbotrestapi.payload.PostDTO;
import online.lahiru.sprinngbotrestapi.payload.PostResponse;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;


public interface PostService {
     PostDTO createPost(PostDTO postDTO);

     PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

     PostDTO getPostById(long id);
     
     List<PostDTO> getPostByCategory(String category);
     
     List<PostDTO> getPostBetweenDate(Date createdFrom, Date createdTo);

     PostDTO updatePost(PostDTO postDTO, long id);

     void deletePostById(long id);
     
     void deletePostByName(String title);
}
