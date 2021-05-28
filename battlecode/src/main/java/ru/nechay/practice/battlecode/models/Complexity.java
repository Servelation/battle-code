package ru.nechay.practice.battlecode.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "complexity")
public class Complexity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	
	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy="complexity")
    private Set<ProgramTask> tasks;

	
	
	public Complexity() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ProgramTask> getTasks() {
		return tasks;
	}

	public void setTasks(Set<ProgramTask> tasks) {
		this.tasks = tasks;
	}
	
	
}
