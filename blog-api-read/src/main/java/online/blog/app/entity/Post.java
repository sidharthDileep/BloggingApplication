package online.blog.app.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(
//        name = "posts",uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
//)
@Document(collection = "posts")
public class Post implements Serializable {
	

    @Transient
    public static final String SEQUENCE_NAME = "post_sequence";
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
    private Long id;
    
    //@Column(name = "user",nullable = false)
    private String user;
    
    //@Column(name = "category",nullable = false)
    private String category;

    //@Column(name = "title",nullable = false)
    private String title;

    //@Column(name = "description",nullable = false)
    private String description;

    //@Column(name = "content",nullable = false)
    private String content;
    
    
    private LocalDateTime createdAt ;

    //@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments= new HashSet<>();

}
