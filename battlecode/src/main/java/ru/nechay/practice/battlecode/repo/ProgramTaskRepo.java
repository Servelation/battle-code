package ru.nechay.practice.battlecode.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.nechay.practice.battlecode.models.ProgramTask;

@Repository
public interface ProgramTaskRepo extends JpaRepository<ProgramTask, Long>{

	@Query("select p.tasks from Language p left join p.tasks e where p.name = ?1")
	List<ProgramTask> findByLanguage(String name);
}
