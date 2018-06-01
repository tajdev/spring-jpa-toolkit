package com.ibexsys.jpa.hibernate.toolkitdemo.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ibexsys.jpa.hibernate.toolkitdemo.ToolkitJpaDemoApplication;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ToolkitJpaDemoApplication.class)
public class PassportRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	StudentRepository repo;
	
	@Autowired
	EntityManager em;
		
	@Test
	@Transactional	// Needed to initialize session for logger statments, otherwise dropped after find
	public void retrieveStudentAndPassportDetails() {
		
		Student student = em.find(Student.class,20001L);
		logger.info("student -> {}",student);
		logger.info("passport -> {}",student.getPassport());
	}
}
