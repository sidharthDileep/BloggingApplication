package online.blog.app.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "posts",uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
//@Document(collection = "posts")
@EqualsAndHashCode(exclude = "comments")
public class Post implements Serializable {
	

    //@Transient
    //public static final String SEQUENCE_NAME = "post_sequence";
    //@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
    private Long id;
    
    @Column(name = "user",nullable = false)
    private String user;
    
    @Column(name = "category",nullable = false)
    private String category;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "content",nullable = false)
    private String content;
    
    
    private LocalDateTime createdAt ;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments= new HashSet<>();

}
