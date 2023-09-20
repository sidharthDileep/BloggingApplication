package online.blog.app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.blog.app.entity.Post;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostEvent {

	private String eventType;
	private Post post;
}
