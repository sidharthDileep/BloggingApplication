package online.blog.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import online.blog.app.entity.Role;

public interface RoleRepository2 extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String name);

	boolean existsByName(String name);

}
