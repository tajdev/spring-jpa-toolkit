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
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Passport;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Review;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Student;

@Repository
@Transactional
public class StudentRepository {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@PersistenceContext
	EntityManager em;

	public List<Student> findAll() {
		TypedQuery<Student> namedQuery = em.createNamedQuery("find_all_students", Student.class);
		return namedQuery.getResultList();
	}

	public Student findById(Long id) {
		Student student = em.find(Student.class, id);

		logger.info("Student -> {}", student);
		return student;
	}

	public Student findByName(String name) {
		TypedQuery<Student> namedQuery = em.createNamedQuery("find_student_by_name", Student.class);
		namedQuery.setParameter(0, name);
		return namedQuery.getSingleResult();
	}

	// Insert or Update
	public void saveStudentWithPassport() {

		Passport passport = new Passport("X90800");
		em.persist(passport);

		Student student = new Student("TJ");
		student.setPassport(passport);
		em.persist(student);

	}

	// Insert or Update
	public Student save(Student student) {

		if (student.getId() == null) {
			em.persist(student);
		} else {
			em.merge(student);
		}

		return student;
	}

	public void deleteById(Long id) {

		em.remove(findById(id));
	}

	public void insertStudentAndCourse(Student student, Course course) {

		em.persist(student);
		em.persist(course);

		student.addCourse(course);
		course.addStudent(student);

		// persist owning side
		em.persist(student);

	}

	public void insertStudentPasswordCourse(Student student, Passport passport, Review review, Course course) {

		em.persist(student);
		em.persist(passport);
		em.persist(course);
		em.persist(review);
		student.addCourse(course);
		course.addStudent(student);

		student.setPassport(passport);
		passport.setStudent(student);

		course.addReview(review);
		review.setCourse(course);

		// persist owning side
		em.persist(student);

	}

	public void insertHardCodedStudentAndCourse() {

		Student student = new Student("JackOff");
		Course course = new Course("Microservices in 100 steps");

		em.persist(student);
		em.persist(course);

		student.addCourse(course);
		course.addStudent(student);

		// persist owning side
		em.persist(student);

	}

	// public void playWithEntityManager() {
	// logger.info("playWithEntityManager - Start");
	//
	// Student student = new Student("Web Services in 100 Steps");
	// Student student2 = new Student("REST in 100 Steps");
	// em.persist(student);
	// em.persist(student2);
	// em.flush();
	//
	// // This is causing a update, due to @Transactional
	// student.setName("Web Services in 100 Steps - Updated");
	// //student2.setName(null);
	//
	//
	// // this will persist above changes to both students
	// //em.flush();
	//
	// // This will update from the DB
	// em.refresh(student);
	//
	//// em.merge(student2);
	//
	// em.flush();
	// }
}
