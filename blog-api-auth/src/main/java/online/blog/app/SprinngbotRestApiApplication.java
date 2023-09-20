package online.blog.app;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import online.blog.app.entity.Role;
import online.blog.app.repository.RoleRepository2;

@SpringBootApplication
public class SprinngbotRestApiApplication implements CommandLineRunner {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(SprinngbotRestApiApplication.class, args);
	}

//	@Autowired
//	private RoleRepository roleRepository;
	
//	@Autowired
//	private SequenceGeneratorService service;
	
	@Autowired
	private RoleRepository2 roleRepository2;

	@Override
	public void run(String... args) throws Exception {
		
//		String uri = "mongodb://localhost:27017";
//        try (MongoClient mongoClient = MongoClients.create(uri)) {
//            MongoDatabase database = mongoClient.getDatabase("myblog");
//            MongoCollection<Document> collection = database.getCollection("roles");
//            //Bson query = lt("imdb.rating", 1.9);
//            try {
//                //DeleteResult result = collection.deleteMany(query);
//                Bson filter = new Document();
//                collection.deleteMany(filter);
//                //System.out.println("Deleted document count: " + result.getDeletedCount());
//            } catch (MongoException me) {
//                System.err.println("Unable to delete due to an error: " + me);
//            }
//        }
		
//		Role adminRole = new Role();
//		//adminRole.setId(service.getSequenceNumber(Role.SEQUENCE_NAME));
//		adminRole.setName("ROLE_ADMIN");
//		
//		roleRepository2.save(adminRole);
//
//		Role userRole = new Role();
//		//userRole.setId(service.getSequenceNumber(Role.SEQUENCE_NAME));
//		userRole.setName("ROLE_USER");
//		roleRepository2.save(userRole);




	}
}
