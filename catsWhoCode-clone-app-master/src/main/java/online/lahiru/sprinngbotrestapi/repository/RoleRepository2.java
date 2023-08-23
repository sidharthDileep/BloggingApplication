package online.lahiru.sprinngbotrestapi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import online.lahiru.sprinngbotrestapi.entity.Role;

public interface RoleRepository2 extends MongoRepository<Role,Long> {

    Optional<Role> findByName(String name);

}
