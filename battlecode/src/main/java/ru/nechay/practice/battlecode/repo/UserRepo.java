package ru.nechay.practice.battlecode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.nechay.practice.battlecode.models.ProgramTask;
import ru.nechay.practice.battlecode.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	
	@Query("select u.tasks from User u")
	List<ProgramTask> findAllTasks();
}

