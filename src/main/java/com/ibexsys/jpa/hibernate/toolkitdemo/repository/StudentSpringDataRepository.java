package com.ibexsys.jpa.hibernate.toolkitdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Student;

public interface StudentSpringDataRepository extends JpaRepository<Student, Long> {
	
	/* Leaving this blank still gives all JpaRepository Methods or create custom see course repo
    
    @Autowire
    private StudentSpringDataRepository repository;
 
    */
}
