package com.example.progression.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.progression.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	<S extends Task> S save(S task);

	Optional<Task> findById(Long id);

	void deleteById(Long id);
	
	List<Task> findAll();

	List<Task> findByUserId(Long userId);

	List<Task> findByCompleted(boolean completed);
	
	List<Task> findByAssigned(boolean assigned);

	void deleteAll();

}
