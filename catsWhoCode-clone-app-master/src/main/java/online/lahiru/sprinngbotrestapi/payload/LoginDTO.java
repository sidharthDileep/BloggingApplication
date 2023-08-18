package online.lahiru.sprinngbotrestapi.payload;

import lombok.Data;

@Data
public class LoginDTO {

    private String usernameOrEmail;
    private String password;

}
