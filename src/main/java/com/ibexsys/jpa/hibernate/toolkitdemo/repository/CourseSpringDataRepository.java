package com.ibexsys.jpa.hibernate.toolkitdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Course;

@RepositoryRestResource(path="courses")
public interface CourseSpringDataRepository extends JpaRepository<Course,Long> {
	
	
	// Create some custom using introspection
	
	List<Course> findByName(String name);
	
	List<Course> findByNameAndId(String name, Long id);
	
	List<Course> countByName(String name);
	
	List<Course> findByNameOrderByIdDesc(String name);
	
	List<Course> deleteByName(String name);
	
//	//Custom Query
	@Query("Select c from Course c where name like '%100 Steps'")
	List<Course> courseWith100StepsInName();
	
	@Query(value = "Select * from Course C where name like '%100 Steps'",nativeQuery = true)
	List<Course> courseWith100StepsInNameNative();
	
	@Query(name = "find_course_by_name")
	List<Course> courseWith100StepsInNameNamedQuery();
		
}
