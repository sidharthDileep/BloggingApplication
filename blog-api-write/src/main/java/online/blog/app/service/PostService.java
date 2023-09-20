package online.blog.app.service;

import online.blog.app.payload.PostDTO;


public interface PostService {
     PostDTO createPost(PostDTO postDTO);

     PostDTO updatePost(PostDTO postDTO, long id);

     void deletePostById(long id);
     
     void deletePostByName(String title);

	 void deleteUser(String user);
	 
	 void deleteAllPostsOfUser(String user);
}
