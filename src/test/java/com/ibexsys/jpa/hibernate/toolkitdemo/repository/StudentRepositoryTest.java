package com.ibexsys.jpa.hibernate.toolkitdemo.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.ibexsys.jpa.hibernate.toolkitdemo.ToolkitJpaDemoApplication;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Course;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Passport;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ToolkitJpaDemoApplication.class)
public class StudentRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	//
	
	@Autowired
	StudentRepository repo;
	
	@Autowired
	EntityManager em;
	
	@Test
	@Transactional
	public void simpleCrudTest() {
		
		Student student = em.find(Student.class,20001L);
		// Persistence Context(student);
		
		Passport passport = student.getPassport();
		// Persistence Context(student,passport);
		
		passport.setNumber("T52354");
		// Persistence Context(student,passport);
		
		student.setName("BXKC");
		// Persistence Context(student++,passport++);
	}
	
		
	@Test
	@Transactional	// Needed to initialize session for logger statments, otherwise dropped after find
	public void retrieveStudentAndPassportDetails() {
		
		Student student = em.find(Student.class,20001L);
		logger.info("student -> {}",student);
		logger.info("passport -> {}",student.getPassport());
	}
	
	@Test
	@Transactional	// Needed to initialize session for logger statments, otherwise dropped after find
	public void retrievePassportAndAssociatedStudent() {
		
		Passport passport = em.find(Passport.class,40001L);
		logger.info("student -> {}",passport);
		logger.info("passport -> {}",passport.getStudent());
	}
	
	@Test
	@Transactional	
	public void retrieveStudentAndCourse() {
		
		Student student = em.find(Student.class, 20001L);
		logger.info("student -> {}",student);
		logger.info("Courses -> {}",student.getCourses());
	}
	
	@Test
	@Transactional
	public void retrieveCourseAndStudent() {
		
		Course course = em.find(Course.class, 10001L);
		logger.info("course -> {}", course);
		logger.info("Students -> {}", course.getStudents());
		
	}
	
	@Test
	@Transactional
	public void insertStudentAndCourse() {
		
		Student student = new Student("JackOff");
		Course course = new Course("Microservices in 100 steps");
		
		em.persist(student);
		em.persist(course);
		
		student.addCourse(course);
		course.addStudent(student);
		
		// persist owning side
		em.persist(student);
		
		student = em.find(Student.class, student.getId());
		logger.info("student -> {}",student);
		logger.info("Courses -> {}",student.getCourses());
		
		
	}

}
