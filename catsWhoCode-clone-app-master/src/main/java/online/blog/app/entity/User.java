package online.blog.app.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
//@Entity
//@Table(name = "users", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"username"}),
//        @UniqueConstraint(columnNames = {"email"})
//})
@Document(collection = "users")
public class User {
	

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
    private long id;
    private String name;
    private String username;
    private String email;
    private String password;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//    inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Set<Role> roles;


}
