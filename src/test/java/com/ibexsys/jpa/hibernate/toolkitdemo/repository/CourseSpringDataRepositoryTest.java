package com.ibexsys.jpa.hibernate.toolkitdemo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.ibexsys.jpa.hibernate.toolkitdemo.ToolkitJpaDemoApplication;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Course;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Review;

@RunWith(SpringRunner.class)
// Launches context from java source boot app
@SpringBootTest(classes=ToolkitJpaDemoApplication.class)
public class CourseSpringDataRepositoryTest implements CommandLineRunner{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CourseSpringDataRepository repository;
	
	@Autowired
	CourseRepository courseRepo;
	
	@Test
	public void findById_CoursePresent() {
		
		// Optional deals
		Optional<Course> courseOptional = repository.findById(10001l);
		assertTrue(courseOptional.isPresent());
		
	}
	
	@Test
	public void findById_NotCoursePresent() {
		
		// Optional deals
		Optional<Course> courseOptional = repository.findById(20001l);
		assertFalse(courseOptional.isPresent());
	}
	
	@Test
	@DirtiesContext
	public void  playingAroundWithSpringDataRepository() {
		
		Course course = new Course("Spring Data Repository in 10 Steps");
		repository.save(course);
		course.setName("Name_Check");
		repository.save(course);
		
		List<Course> courses = repository.findByName("Name_Check");
		assertTrue(courses.size() != 0);
		
		logger.info("All Courses -> {}", repository.findAll());
		logger.info("Course Count -> {}", repository.count());
	
	}
	
	
	@Test
	@DirtiesContext
	public void  playingAroundWithTwoRepositories() {
		
		// Get and save from Spring Data Repo
		Course course = new Course("Spring Data Repository in 10 Steps");
		repository.save(course);
		
		course.setName("Name_Check");
		repository.save(course);

		// Coded Repo 
		String newName = course.getName().concat("_Foo");
		Course courseCoded = courseRepo.findByName(course.getName());
		courseCoded.setName(newName);
		courseRepo.save(courseCoded);
		
		assertTrue(courseRepo.findByName(newName).getName().equalsIgnoreCase(newName));
	}
	
	@Test
	public void sortCourses() {
		
		Sort dateSort = new Sort(Sort.Direction.ASC,"createdDate");
		Sort sort = new Sort(Sort.Direction.DESC,"name").and(dateSort);
		logger.info("Sorted Courses -> {}", repository.findAll(sort));
	}
	
	@Test
	public void paginationCourses() {
		
		PageRequest pageRequest = PageRequest.of(0,5);

		Page<Course> firstPage = repository.findAll(pageRequest);
		logger.info("First Page -> {}", firstPage.getContent());
		
		
	}
	
	@Test
	public void paginationCoursesAll() {
		
		PageRequest pageRequest = PageRequest.of(0,5);
		Page<Course> page = repository.findAll(pageRequest);
	
		int i = 1;
		logger.info("Page - {} -> {}",i++, page.getContent());
		
		while (page.hasNext()) {
			page = repository.findAll(page.nextPageable());
			logger.info("Page - {} -> {}",i++, page.getContent());
		       	
		}

		
		
	}
	

	
	@Override
	public void run(String... args) throws Exception {
		

		
	}
	

	

}
