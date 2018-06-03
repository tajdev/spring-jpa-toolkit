package com.ibexsys.jpa.hibernate.toolkitdemo.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Address;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.AddressType;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Course;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Passport;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ToolkitJpaDemoApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StudentRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StudentRepository repo;

	private @Autowired EntityManager em;

	// @Add Other Methods for CRUD
	@Test
	@Transactional
	public void simpleCrudTest() {

		Student student = em.find(Student.class, 20001L);
		// Persistence Context(student);

		Passport passport = student.getPassport();
		// Persistence Context(student,passport);

		passport.setNumber("T52354");
		// Persistence Context(student,passport);

		student.setName("BXKC");
		// Persistence Context(student++,passport++);
	}

	@Test
	@Transactional // Needed to initialize session for logger statments, otherwise dropped after
					// find
	public void retrieveStudentAndPassportDetails() {

		Student student = em.find(Student.class, 20001L);
		logger.info("student -> {}", student);
		logger.info("passport -> {}", student.getPassport());
	}

	@Test
	@Transactional // Needed to initialize session for logger statments, otherwise dropped after
					// find
	public void retrievePassportAndAssociatedStudent() {

		Passport passport = em.find(Passport.class, 40001L);
		logger.info("student -> {}", passport);
		logger.info("passport -> {}", passport.getStudent());
	}

	@Test
	@Transactional
	public void retrieveStudentAndCourse() {

		Student student = em.find(Student.class, 20001L);
		logger.info("student -> {}", student);
		logger.info("Courses -> {}", student.getCourses());
	}

	@Test
	@Transactional
	public void retrieveCourseAndStudent() {

		Course course = em.find(Course.class, 10001L);
		logger.info("course -> {}", course);
		logger.info("Students -> {}", course.getStudents());

	}

//	@Test
//	@Transactional
//  @Todo
//	public void setStudentAddressTest() {
//
//		Student student = em.find(Student.class, 20001L);
//		student.setAddress(new Address("Line1", "Line2", "City"));
//		em.flush();
//
//		logger.info("student with address -> {}", student);
//
//	}

	@Test
	@Transactional
	public void insertStudentAndCourseAndAddressTest() {

		Student student = new Student("Foo Man Chu");
		Course course = new Course("Microservices in 100 steps - Testing");
		Address address = new Address("1025 Foo Ln","Apt 123","Attn FuManChu","LA","CA",
				         "90230","United States",AddressType.STREET_ADDRESS);

		em.persist(address);
		em.persist(student);
		em.persist(course);
		em.persist(address);

		student.addCourse(course);
		course.addStudent(student);
		
		student.addAddress(address);
		address.addStudent(student);;

		// persist owning side
		em.persist(student);

		student = em.find(Student.class, student.getId());
		assertNotNull(student);
		
		// Check the join table for course student
		Query query = em.createNativeQuery("SELECT * FROM STUDENT_COURSE where student_id = :id");
		query.setParameter("id", student.getId());
		List<?> resultList = query.getResultList();
        assertTrue(resultList.size() == 1);
        
		// Check the join table
		query = em.createNativeQuery("SELECT * FROM STUDENT_ADDRESS where student_id = :id");
		query.setParameter("id", student.getId());
		resultList = query.getResultList();
        assertTrue(resultList.size() == 1);
		
		
		
		logger.info("student -> {}", student);
		logger.info("Courses -> {}", student.getCourses());
		logger.info("Address -> {}", student.getAddressList());

	}
	
	/*
	 * insert into address(id,line1,line2,line3,city,state,zip,country,address_type,is_deleted)
            values(30001,'1025 Foo Ln','Apt 123','Attn FuManChu','LA','CA','90230','United States',1,false)
	 */
	
	@Test
	@Transactional
	public void insertStudentAndCourseAndAddressThenDeleteTest() {

		Student student = new Student("Foo Man Chu");
		Course course = new Course("Microservices in 100 steps - Testing");
		Address address = new Address("1025 Foo Ln","Apt 123","Attn FuManChu","LA","CA",
				         "90230","United States",AddressType.STREET_ADDRESS);

		em.persist(address);
		em.persist(student);
		em.persist(course);
		em.persist(address);

		student.addCourse(course);
		course.addStudent(student);
		
		student.addAddress(address);
		address.addStudent(student);

		// persist owning side
		em.persist(student);

		student = em.find(Student.class, student.getId());
        assertNotNull(student);
		
	
		// Check the join table for course student
		Query query = em.createNativeQuery("SELECT * FROM STUDENT_COURSE where student_id = :id");
		query.setParameter("id", student.getId());
		List<?> resultList = query.getResultList();
        assertTrue(resultList.size() == 1);
        
		// Check the join table
		query = em.createNativeQuery("SELECT * FROM STUDENT_ADDRESS where student_id = :id");
		query.setParameter("id", student.getId());
		resultList = query.getResultList();
        assertTrue(resultList.size() == 1);
   		
		// Delete it
    	Long testJoinId = student.getId();
		repo.deleteById(student.getId());
		assertNull(repo.findById(student.getId()));
		
		// Check the join again
		query = em.createNativeQuery("SELECT * FROM STUDENT_COURSE where student_id = :id");
		query.setParameter("id", testJoinId);
		resultList = query.getResultList();
        assertTrue(resultList.size() == 0);
        
		// Check the join table again
		query = em.createNativeQuery("SELECT * FROM STUDENT_ADDRESS where student_id = :id");
		query.setParameter("id", student.getId());
		resultList = query.getResultList();
        assertTrue(resultList.size() == 0);
		
	}

}
