package com.ibexsys.jpa.hibernate.toolkitdemo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Student;

@RunWith(SpringRunner.class)
// Launches context from java source boot app
@SpringBootTest(classes = ToolkitJpaDemoApplication.class)
public class JPQLTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;

	@Test
	public void JPQL_Basic() {
		List resultList = em.createQuery("Select c From Course c").getResultList();
		logger.info("Select c From Course c -> {}", resultList);

	}
	
	@Test
	public void JPQL_Named_Type() {
		
		TypedQuery<Course> query = em.createNamedQuery("find_all_courses",Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Select c From Course c -> {}", resultList);

	}
	
	@Test
	public void JPQL_Native_Type() {
		
		Query query = em.createNativeQuery("SELECT * FROM COURSE",Course.class);
		List<?> resultList = query.getResultList();
		logger.info("Select c From Course c -> {}", resultList);

	}
	
	@Test
	public void JPQL_Native_Type_Parameter() {
		
		Query query = em.createNativeQuery("SELECT * FROM COURSE WHERE ID = ?",Course.class);
		query.setParameter(1, 10001L);
		List<?> resultList = query.getResultList();
		logger.info("Select c From Course c Where ID = ? -> {}", resultList);

	}
	
	@Test
	public void JPQL_Native_Named_Parameter() {
		
		Query query = em.createNativeQuery("SELECT * FROM COURSE WHERE ID = :id",Course.class);
		query.setParameter("id", 10001L);
		List<?> resultList = query.getResultList();
		logger.info("Select c From Course c Where ID = ? -> {}", resultList);

	}
	

	@Test
	public void JPQL_Type() {
		
		TypedQuery<Course> query = em.createQuery("Select c From Course c", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Select c From Course c -> {}", resultList);

	}
	
	@Test
	public void JPQL_Where() {
		
		TypedQuery<Course> query = em.createQuery("Select c From Course c where name like '%100 Steps'", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Select c From Course c -> {}", resultList);

	}

	
	@Test
	public void JPQL_Courses_Without_students() {
		
		TypedQuery<Course> query = em.createQuery("Select c From Course c where c.students is empty", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Results -> {}", resultList);

	}
	
	@Test
	public void JPQL_Courses_At_Least_Students() {
		
		TypedQuery<Course> query = em.createQuery("Select c From Course c where size(c.students) >= 2", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Results -> {}", resultList);

	}
	
	@Test
	public void JPQL_Courses_Ordered_By_Students() {
		
		TypedQuery<Course> query = em.createQuery("Select c From Course c order by size(c.students)", Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Results -> {}", resultList);
	}
	
	@Test
	public void JPQL_students_with_passports_in_a_certain_pattern() {
		
		TypedQuery<Student> query = em.createQuery("Select s From Student s where s.passport like '%1234%'", Student.class);
		List<Student> resultList = query.getResultList();
		logger.info("Results -> {}", resultList);

	}
	
	//JOIN => Select c, s from Course c JOIN c.students s
	//LEFT JOIN =>  Select c,s from Course c LEFT JOIN c.student s
	//CROSS JOIN =>Select c,s from Course c, Student s
	
	@Test
	public void join() {
		Query  query = em.createQuery("Select c, s from Course c JOIN c.students s");
		List<Object[]> resultList = query.getResultList();
		logger.info("Results -> {}", resultList.size());
		
		for(Object[] result:resultList) {
			logger.info("Course{} Student{}",result[0], result[1]);
		}
	}
	
	@Test
	public void left_join() {
		Query  query = em.createQuery("Select c, s from Course c LEFT JOIN c.students s");
		List<Object[]> resultList = query.getResultList();
		logger.info("Results -> {}", resultList.size());
		
		for(Object[] result:resultList) {
			logger.info("Course{} Student{}",result[0], result[1]);
		}
	}
	
	@Test
	public void cross_join() {
		Query  query = em.createQuery("Select c,s from Course c, Student s");
		List<Object[]> resultList = query.getResultList();
		logger.info("Results -> {}", resultList.size());
		
		for(Object[] result:resultList) {
			logger.info("Course{} Student{}",result[0], result[1]);
		}
	}
}
