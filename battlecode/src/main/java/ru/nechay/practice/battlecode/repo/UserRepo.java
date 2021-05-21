package ru.nechay.practice.battlecode.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.nechay.practice.battlecode.models.User;


public interface UserRepo extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
}

