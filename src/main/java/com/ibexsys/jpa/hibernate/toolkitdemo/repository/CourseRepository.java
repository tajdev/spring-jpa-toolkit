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
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Review;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.ReviewRating;

@Repository
@Transactional
public class CourseRepository {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext 
	private EntityManager em;
	
	public List<Course> findAll(){
		TypedQuery<Course> namedQuery =  em.createNamedQuery("find_all_courses",Course.class);
		return namedQuery.getResultList();
	}
	
	public Course findById(Long id) {
		Course course = em.find(Course.class, id);
		
		logger.info("Course -> {}", course);
		return course;
	}
	

	
	public Course findByName(String name) {
		TypedQuery<Course> namedQuery = em.createNamedQuery("find_course_by_name",Course.class);
		namedQuery.setParameter(0, name);
		return namedQuery.getSingleResult();
	}
	
	// Insert or Update
	public Course save(Course course) {
        
		if (course.getId() == null) {
			em.persist(course);
		} else {
			em.merge(course);
		}
		
    	return course;
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

	public void addHardedReviewsForCourse() {
		// get the course 10003
		// add 2 reviews
		//save
		
		Course course = findById(10002L);
		Review review1 = new Review(ReviewRating.FIVE,"Great Stuff");
		Review review2 = new Review(ReviewRating.THREE,"Great Stuff Mainard");
		
		course.addReview(review1);
		review1.setCourse(course);
		
		course.addReview(review2);
		review2.setCourse(course);
		
		em.persist(review1);
		em.persist(review2);
		
		logger.info("course.getReviews() -> {}", course.getReviews());
		logger.info("NFW");
		
	}
	
	public void addReviewToCourse(Long courseId, Review review) {
		Course course = findById(courseId);
		review.setCourse(course);
		course.addReview(review);
		em.persist(review);
		
	}
	
	public void addReviewsToCourse(Long courseId, List<Review> reviews) {
		
		Course course = findById(courseId);
		
        for(Review review: reviews) {
        	course.addReview(review);
        	review.setCourse(course);
        	em.persist(review);
        }
	}
}
