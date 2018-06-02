package com.ibexsys.jpa.hibernate.toolkitdemo.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

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
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Student;

@RunWith(SpringRunner.class)
// Launches context from java source boot app
@SpringBootTest(classes = ToolkitJpaDemoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StudentSpringDataRepositoryTest implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StudentSpringDataRepository repository;

	@Autowired
	private CourseRepository courseRepo;

	@Test
	public void findById_CoursePresent() {

		// Optional deals
		Optional<Student> studentOptional = repository.findById(20001L);
		assertTrue(studentOptional.isPresent());

	}

	@Test
	public void findById_NotCoursePresent() {

		// Optional deals
		Optional<Student> studentOptional = repository.findById(20001L);
		assertFalse(studentOptional.isPresent());
	}

	//@Test
	// @ToDo Added created/modifed ?? not needed
	public void sortCourses() {

		Sort dateSort = new Sort(Sort.Direction.ASC, "createdDate");
		Sort sort = new Sort(Sort.Direction.DESC, "name").and(dateSort);
		logger.info("Sorted Courses -> {}", repository.findAll(sort));
	}

	@Test
	public void paginationCourses() {

		PageRequest pageRequest = PageRequest.of(0, 2);

		Page<Student> firstPage = repository.findAll(pageRequest);
		logger.info("First Page -> {}", firstPage.getContent());

	}

	@Test
	public void paginationCoursesAll() {

		PageRequest pageRequest = PageRequest.of(0, 2);
		Page<Student> page = repository.findAll(pageRequest);

		int i = 1;
		logger.info("Page - {} -> {}", i++, page.getContent());

		while (page.hasNext()) {
			page = repository.findAll(page.nextPageable());
			logger.info("Page - {} -> {}", i++, page.getContent());

		}

	}

	@Override
	public void run(String... args) throws Exception {

	}

}
