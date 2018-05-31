package com.ibexsys.jpa.hibernate.toolkitdemo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Course;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Employee;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.FullTimeEmployee;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.PartTimeEmployee;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Review;

@Repository
@Transactional
public class EmployeeRepository {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext 
	EntityManager em;
	

	
	public Employee findById(Long id) {
		Employee employee = em.find(Employee.class, id);
		
		logger.info("Employee -> {}", employee);
		return employee;
	}
	
	public List<PartTimeEmployee> retrieveAllPartTimeEmployees(){
		return em.createQuery("select e from PartTimeEmployee e",PartTimeEmployee.class).getResultList();
	}
	
	public List<FullTimeEmployee> retrieveAllFullTimeEmployees(){
		return em.createQuery("select e from FullTimeEmployee e",FullTimeEmployee.class).getResultList();
	}
	
	

//	public Employee findByName(String name) {
//		TypedQuery<Employee> namedQuery = em.createNamedQuery("find_employee_by_name",Employee.class);
//		namedQuery.setParameter(0, name);
//		return namedQuery.getSingleResult();
//	}
	
	// Insert or Update
	public Employee save(Employee employee) {
        
		if (employee.getId() == null) {
			em.persist(employee);
		} else {
			em.merge(employee);
		}
		
    	return employee;
	}
	
    public void deleteById(Long id) {
 	
    	em.remove(findById(id));
    }
    
    public void playWithEntityManager() {
    	logger.info("playWithEntityManager - Start");

    	Course course = new Course("Web Services in 100 Steps");
    	Course course2 = new Course("REST in 100 Steps");
		em.persist(course);
		em.persist(course2);
		em.flush(); 
		
		// Subsequent em.flushes will not update
//		em.detach(course);
//    	em.detach(course2);
		
		// Alternative to detaching a single class
        //em.clear();
		
		// This is causing a update, due to @Transactional
		course.setName("Web Services in 100 Steps - Updated");
		//course2.setName(null);
		
		
		// this will persist above changes to both courses
		//em.flush();
		
		// This will update from the DB
		em.refresh(course);
		
//        em.merge(course2);

		em.flush();
    }

//	public void addHardedReviewsForCourse() {
//		// get the course 10003
//		// add 2 reviews
//		//save
//		
//		Course course = findById(10002L);
//		Review review1 = new Review("5","Great Stuff");
//		Review review2 = new Review("5","Great Stuff Mainard");
//		
//		course.addReview(review1);
//		review1.setCourse(course);
//		
//		course.addReview(review2);
//		review2.setCourse(course);
//		
//		em.persist(review1);
//		em.persist(review2);
//		
//		logger.info("course.getReviews() -> {}", course.getReviews());
//		logger.info("NFW");
//		
//	}
	
//	public void addReviewToCourse(Long courseId, Review review) {
//		Course course = findById(courseId);
//		review.setCourse(course);
//		course.addReview(review);
//		em.persist(review);
//		
//	}
//	
//	public void addReviewsToCourse(Long courseId, List<Review> reviews) {
//		
//		Course course = findById(courseId);
//		
//        for(Review review: reviews) {
//        	course.addReview(review);
//        	review.setCourse(course);
//        	em.persist(review);
//        }
//	}
}
