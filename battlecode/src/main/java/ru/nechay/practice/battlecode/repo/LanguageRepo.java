package ru.nechay.practice.battlecode.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.nechay.practice.battlecode.models.Language;

@Repository
public interface LanguageRepo extends JpaRepository<Language, Long> {
	
	Language findByName(String name);
}