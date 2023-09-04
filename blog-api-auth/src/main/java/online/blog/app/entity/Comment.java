package online.blog.app.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "comments")
@Document(collection = "comments")
public class Comment {
	

    @Transient
    public static final String SEQUENCE_NAME = "comment_sequence";

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String emial;
    private String body;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

}
