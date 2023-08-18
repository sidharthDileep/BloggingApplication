package online.lahiru.sprinngbotrestapi.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDTO {
    private Long id;
    
    @NotEmpty
    private String category;

    @NotEmpty
    @Size(min = 2,message = "Post title should have at least two charactors")
    private String title;

    @NotEmpty
    @Size(min = 10,message = "you need to add at least 10 charactors")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDTO> comments;



}
