package com.example.progression.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.progression.dto.TaskDTO;
import com.example.progression.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	int save(TaskDTO task);

	int update(Task task);

	Optional<Task> findById(Long id);

	void deleteById(Long id);
	
	List<Task> findAll();

	List<Task> findOfUser(Long userId);

	List<Task> findCompleted(boolean completed);
	
	List<Task> findAssigned(boolean assigned);

	void deleteAll();

}
