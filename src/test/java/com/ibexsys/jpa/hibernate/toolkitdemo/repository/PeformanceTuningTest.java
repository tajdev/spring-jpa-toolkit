package com.ibexsys.jpa.hibernate.toolkitdemo.repository;

import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ibexsys.jpa.hibernate.toolkitdemo.ToolkitJpaDemoApplication;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Course;

@RunWith(SpringRunner.class)  // Mute for this testing class, but what the hell
@SpringBootTest(classes=ToolkitJpaDemoApplication.class)
public class PeformanceTuningTest implements CommandLineRunner{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CourseRepository repository;
	
	
	@Autowired
	EntityManager em;

	
//  Problem from entity for ManyToMany creates N+1
//	@ManyToMany(mappedBy="courses")
//	@JsonIgnore
//	private List<Student> students = new ArrayList<Student>();
    
    
    // Fires off three queries one for each course, and N for each student per Course
    // Sol 1 (problematic) can be fixed by setting Students @ManyToMany to eager due to upfront cost
    // Sol 2  Use Entity Graph is best
    @Test
    @Transactional
    public void creatingNPlusOneProblem() {
    	List<Course> courses =  em.createNamedQuery("find_all_courses",Course.class)
    			                     .getResultList();
    	
    	for (Course course : courses) {
    		logger.info("Course -> {} Students -> {}", course, course.getStudents());
    	}
    }

    // Solve problem by creating entity graph using hint
    // 1. create Entity Graph
    // 2. add setHint to em.createEntityGraph
    // 3. addSubGraph with list var name in entity (ie 
    @Test
    @Transactional
    public void solvingNPlusOneProblem_EntityGraph() {
    	
    	EntityGraph<Course> entityGraph = em.createEntityGraph(Course.class);
    	entityGraph.addSubgraph("students");
    	
    	List<Course> courses =  em.createNamedQuery("find_all_courses",Course.class)
    			                     .setHint("javax.persistence.loadgraph", entityGraph).getResultList();
    	
    	for (Course course : courses) {
    		logger.info("Course -> {} Students -> {}", course, course.getStudents());
    	}
    }
    
    @Test
    @Transactional
    public void solvingNPlusOneProblem_JoinFetch() {
    	
    	EntityGraph<Course> entityGraph = em.createEntityGraph(Course.class);
    	entityGraph.addSubgraph("students");
    	
    	List<Course> courses =  em.createNamedQuery("find_all_courses",Course.class)
    			                     .setHint("javax.persistence.loadgraph", entityGraph).getResultList();
    	
    	for (Course course : courses) {
    		logger.info("Course -> {} Students -> {}", course, course.getStudents());
    	}
    }
    
    
    
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		logger.info("Test is running....");

	}
	

}
