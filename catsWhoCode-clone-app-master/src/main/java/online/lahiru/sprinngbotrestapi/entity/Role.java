package online.lahiru.sprinngbotrestapi.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
//@Setter
//@Getter
//@Entity
//@Table(name = "roles")
@Document(collection = "roles")
public class Role {
	

    @Transient
    public static final String SEQUENCE_NAME = "role_sequence";

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
    private long id;

    //@Column(length = 60)
    private String name;

}
