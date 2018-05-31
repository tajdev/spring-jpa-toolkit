package com.ibexsys.jpa.hibernate.toolkitdemo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
public class CriteriaQueryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;

	@Test
	public void JPQL_Basic() {
		TypedQuery<Course> query = em.createQuery("Select c From Course c",Course.class);
		List<Course> resultList = query.getResultList();
		logger.info("Select c From Course c -> {}", resultList);
	}
	
	@Test
	public void Criteria_Basic_All() {
		
		// Simple Steps for criteria - Select C from Course

		// 1. Use Criteria Builder to create a Criteria Query returning the expected result object
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		
		// 2. Define roots for tables which are involved in the query
			Root<Course> courseRooot = cq.from(Course.class);
		
		// 3. Define predicated ect using criteria builder
			// Not used in simple
		
		// 4. Add predicates to ect to the criteria Query
			// Not used in simple		
		
		// 5. Build the Typed query using entity manager and criteria query
			// Not used in simple
		
		TypedQuery<Course> query = em.createQuery(cq.select(courseRooot));
		List<Course> resultList = query.getResultList();
		logger.info("Select c From Course c -> {}", resultList);
	}
	
	@Test
	public void Criteria_Basic_All_Courses_Having100Steps() {
		
		// Simple Steps for criteria - Select C from Course where name like '%100 Steps'

		// 1. Use Criteria Builder to create a Criteria Query returning the expected result object
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		
		// 2. Define roots for tables which are involved in the query
			Root<Course> courseRooot = cq.from(Course.class);
		
		// 3. Define predicated ect using criteria builder
			Predicate like100Steps = cb.like(courseRooot.get("name"),"%100 Steps");
			
		// 4. Add predicates to ect to the criteria Query
			cq.where(like100Steps);		
		
		// 5. Build the Typed query using entity manager and criteria query
			TypedQuery<Course> query = em.createQuery(cq.select(courseRooot));
			List<Course> resultList = query.getResultList();
			logger.info("Select C from Course where name like '%100 Steps' -> {}", resultList);
	}
	
	@Test
	public void Criteria_Basic_All_Courses_WithOutStudents() {
		
		// Simple Steps for criteria - Select C from Course where c.students is empty

		// 1. Use Criteria Builder to create a Criteria Query returning the expected result object
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		
		// 2. Define roots for tables which are involved in the query
			Root<Course> courseRooot = cq.from(Course.class);
		
		// 3. Define predicated ect using criteria builder
			Predicate noStudents = cb.isEmpty(courseRooot.get("students"));
			
		// 4. Add predicates to ect to the criteria Query
			cq.where(noStudents);		
		
		// 5. Build the Typed query using entity manager and criteria query
			TypedQuery<Course> query = em.createQuery(cq.select(courseRooot));
			List<Course> resultList = query.getResultList();
			logger.info("Select C from Course where c.students is empty -> {}", resultList);
	}
	
	@Test
	public void join() {
		
		// Simple Steps for criteria - Select C from Course join c.students

		// 1. Use Criteria Builder to create a Criteria Query returning the expected result object
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		
		// 2. Define roots for tables which are involved in the query
			Root<Course> courseRoot = cq.from(Course.class);
		
		// 3. Define predicated ect using criteria builder
			Join<Object,Object> join = courseRoot.join("students");
			
		// 4. Add predicates to ect to the criteria Query
		//	cq.where(noStudents);		
		
		// 5. Build the Typed query using entity manager and criteria query
			TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
			List<Course> resultList = query.getResultList();
			logger.info("Select C from Course where c.students is empty -> {}", resultList);
	}
	
	@Test
	public void left_join() {
		
		// Simple Steps for criteria - Select C from Course join c.students

		// 1. Use Criteria Builder to create a Criteria Query returning the expected result object
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		
		// 2. Define roots for tables which are involved in the query
			Root<Course> courseRoot = cq.from(Course.class);
		
		// 3. Define predicated ect using criteria builder (includes all courses regardless of students)
			Join<Object,Object> join = courseRoot.join("students",JoinType.LEFT);
			
		// 4. Add predicates to ect to the criteria Query
		//	cq.where(noStudents);		
		
		// 5. Build the Typed query using entity manager and criteria query
			TypedQuery<Course> query = em.createQuery(cq.select(courseRoot));
			List<Course> resultList = query.getResultList();
			logger.info("Select C from Course where c.students is empty -> {}", resultList);
	}
	
	
	
}
