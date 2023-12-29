package online.blog.app.payload;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {
    private long id;

    @NotEmpty(message = "name should not be empty")
    private String name;

    @NotEmpty(message = "email should not be empty")
    @Email
    private String email;
    @NotEmpty
    @Size(min = 10, message = "you need to add minimum 10 charactors")
    private String body;

}
