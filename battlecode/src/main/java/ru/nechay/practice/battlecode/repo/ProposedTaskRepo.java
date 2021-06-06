package ru.nechay.practice.battlecode.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.nechay.practice.battlecode.models.ProposedTask;

public interface ProposedTaskRepo  extends JpaRepository<ProposedTask, Long>{

	
	ProposedTask findById(Integer id);
}
