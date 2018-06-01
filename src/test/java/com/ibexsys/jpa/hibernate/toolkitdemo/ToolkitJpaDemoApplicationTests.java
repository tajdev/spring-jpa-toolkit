package com.ibexsys.jpa.hibernate.toolkitdemo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Course;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Passport;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Review;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.ReviewRating;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Student;
import com.ibexsys.jpa.hibernate.toolkitdemo.repository.CourseRepository;
import com.ibexsys.jpa.hibernate.toolkitdemo.repository.EmployeeRepository;
import com.ibexsys.jpa.hibernate.toolkitdemo.repository.StudentRepository;

@RunWith(SpringRunner.class)
// Launches context from java source boot app
@SpringBootTest(classes = ToolkitJpaDemoApplication.class)
public class ToolkitJpaDemoApplicationTests implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CourseRepository courseRepo;

	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	private EmployeeRepository employeeRepo;

	@Test
	public void contextLoads() {
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

	}

	// Some examples from course, NOT tests
	public void addReviewsTmp() {
		List<Review> reviews = new ArrayList<Review>();

		Review review1 = new Review(ReviewRating.FIVE, "Great Stuff FWIW");
		Review review2 = new Review(ReviewRating.THREE, "Great Stuff FWIW Mainard");

		reviews.add(review1);
		reviews.add(review2);

		courseRepo.addReviewsToCourse(10001L, reviews);

		logger.info("All Courses - ");
	}

	public void listCourses() {
		listCourses("Generic");
	}

	public void listCourses(String msg) {

		logger.info("All Courses - " + msg);

		List<Course> allCourses = courseRepo.findAll();

		for (Course c : allCourses) {
			System.out.println(c.toString());
		}

	}

	public void runHardCodedStudentAndCourse() {

		studentRepo.insertHardCodedStudentAndCourse();

		Student student = new Student("JackOff");
		Course course = new Course("Microservices in 100 steps");

	}

	public void runInsertStudentAndCourse() {

		Student student = new Student("JackOff");
		Course course = new Course("Microservices in 100 steps");

		studentRepo.insertStudentAndCourse(student, course);
		;
	}

	public void runInsertCompleteRecord() {

		Student student = new Student("JackOff");
		Course course = new Course("Microservices in 100 steps");
		Review review = new Review(ReviewRating.FIVE, "Great Stuff FWIW Mainard");
		Passport passport = new Passport("Z90971");

		studentRepo.insertStudentPasswordCourse(student, passport, review, course);

		Student dumpStudent = studentRepo.findById(student.getId());
		logger.info("Dump of student -> {}", dumpStudent);

	}
}
