package ru.nechay.practice.battlecode.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.nechay.practice.battlecode.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
}

