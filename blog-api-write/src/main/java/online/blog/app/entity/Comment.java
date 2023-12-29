package online.blog.app.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
//@Document(collection = "comments")
public class Comment implements Serializable {
	

//    @Transient
//    public static final String SEQUENCE_NAME = "comment_sequence";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String body;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id",nullable = false)
    //@Fetch(value=FetchMode.SELECT)
    @JsonBackReference
    private Post post;
    
    private Long idOfPost;

}
