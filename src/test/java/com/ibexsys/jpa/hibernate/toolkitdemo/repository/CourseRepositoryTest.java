package com.ibexsys.jpa.hibernate.toolkitdemo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.ibexsys.jpa.hibernate.toolkitdemo.ToolkitJpaDemoApplication;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Course;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Review;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ToolkitJpaDemoApplication.class)
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CourseRepositoryTest implements CommandLineRunner{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CourseRepository repository;
	
	@Autowired
	private EntityManager em;
	
	@Test
	public void saveUpdateTest() {

		String name = "JPA in 5 Steps";
		String target = name.concat( "UPDATED");
		
		repository.save(new Course(name));
		Course course = repository.findByName(name);
		
		assertNotNull(course);
	    assertEquals(name,course.getName());
		 
		
		course.setName(target);
		repository.save(course);
		
		course = repository.findById(course.getId());
		assert(course != null && course.getName().equalsIgnoreCase(target));
	}
	
	@Test
	@Transactional
	public void saveCreateTestNoDuplicates() {

		repository.save(new Course("JPA in 2850 Steps - No Dups Allowed"));
		Course course = repository.findByName("JPA in 2850 Steps - No Dups Allowed");
		
		assertNotNull(course);
		assertTrue(course.getName().equals("JPA in 2850 Steps - No Dups Allowed"));
		
	}
	
	
	@Test
	public void saveCreateTestHandlesDuplicates() {

		repository.save(new Course("JPA in 2850 Steps"));
		List<Course> courses = repository.findAllByName("JPA in 2850 Steps");
		
		assertNotNull(courses);
		
	}
	

	@Test
	public void findByIdBasicTest() {
		Course course = repository.findById(10001L);
		assertNotNull(course);
		assertEquals("JPA in 50 Steps",course.getName());
	}
	
	@Test
	@Transactional  // Without Transactional we would get two hits on DB, 1st level within 1 transaction, transaction annotation at repo
	public void findById_firstLevelCacheDemo() {
		Course course = repository.findById(10001L);
		logger.info("First Course Retrieved {}" + course);
		
		Course course2 = repository.findById(10001L);
		logger.info("First Course Retrieved Again {}" + course2);
		
		
		assertNotNull(course);
		assertEquals("JPA in 50 Steps",course.getName());
	}
	
	@Test
	public void findByNameBasicTest() {
		Course course = repository.findByName("JPA in 50 Steps");
	    
		assertNotNull(course);
		assertEquals("JPA in 50 Steps",course.getName());

	}
	
	
	@Test
	public void findAllBasicTest() {
		
		List<Course> courses =  repository.findAll();
		assertNotNull(courses);
		assert(courses.size() > 0);
			
	}
	
	
	// Soft Delete, uses @SQLDelete and @Where clauses in Entity
	@Test
	@Transactional
	public void deleteByIdSoftDeleteTest() {
		Course course = repository.findById(10001L);

		repository.deleteById(course.getId());
		
		course = repository.findById(10001L);
		
	    assertNull(repository.findById(10001L));
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		logger.info("Test is running....");
		
	}
	
	@Test
   public void playWithEntityManager() {
		repository.playWithEntityManager();
		assert(true);
	}
	
	@Test
	@Transactional
	public void retrieveReviewsForCourse() {
		
		Course course = repository.findById(10001L);
		logger.info("{}", course.getReviews());

	}
	
	@Test
	@Transactional
	public void retrieveCourseForReviews() {
		
		logger.info("Finding...");
		
		Review review = em.find(Review.class, 50001L);
		logger.info("{}", review.getCourse());

	}


}
