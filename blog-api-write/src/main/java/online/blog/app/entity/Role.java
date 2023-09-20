package online.blog.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Entity
@Table(name = "roles")
//@Document(collection = "roles")
public class Role {
	

//    @Transient
//    public static final String SEQUENCE_NAME = "role_sequence";

    //@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
    private long id;

    @Column(length = 60)
    private String name;

}
