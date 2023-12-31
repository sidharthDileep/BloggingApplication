package online.blog.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import online.blog.app.entity.User;

public interface UserRepository2 extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
	void deleteByName(String user);
	void deleteByUsername(String user);
	void deleteByEmail(String user);
}
