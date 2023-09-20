package online.blog.app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.blog.app.entity.Comment;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentEvent {

	private String eventType;
	private Comment comment;
}
