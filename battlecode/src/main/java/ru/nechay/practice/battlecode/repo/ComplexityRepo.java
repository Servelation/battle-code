package ru.nechay.practice.battlecode.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.nechay.practice.battlecode.models.Complexity;

@Repository
public interface ComplexityRepo extends JpaRepository<Complexity, Long> {

}
