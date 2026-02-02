package com.example.progression.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.progression.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	<S extends User> S save(S user);

	Optional<User> findById(Long Id);

	void deleteById(Long id);

	List<User> findAll();

	List<User> findByAdmin(boolean admin);

	User findByUsername(String username);
	
	void deleteAll();

}
