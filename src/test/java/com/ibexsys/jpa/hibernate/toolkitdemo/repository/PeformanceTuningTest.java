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

	
	/*  Problem from entity for ManyToMany creates N+1
	@ManyToMany(mappedBy="courses")
	@JsonIgnore
	private List<Student> students = new ArrayList<Student>();
	.... Fires off three queries one for each course, and N for each student per Course
	     logger.info("Course -> {} Students -> {}", course, course.getStudents());
	*/
	@Test
    @Transactional
    public void creatingNPlusOneProblem() {
    	List<Course> courses =  em.createNamedQuery("find_all_courses",Course.class)
    			                     .getResultList();
    	
    	for (Course course : courses) {
    		logger.info("Course -> {} Students -> {}", course, course.getStudents());
    	}
    }

   /* Solve problem by creating entity graph using hint
    1. create Entity Graph
    2. add setHint to em.createEntityGraph
    3. addSubGraph with list var name in entity
    4. Creates only one query for each Course
    
    Hibernate: 
        select
            course0_.id as id1_0_0_,
            student2_.id as id1_5_1_,
            course0_.created_date as created_2_0_0_,
            course0_.is_deleted as is_delet3_0_0_,
            course0_.modified_date as modified4_0_0_,
            course0_.name as name5_0_0_,
            student2_.city as city2_5_1_,
            student2_.line1 as line3_5_1_,
            student2_.line2 as line4_5_1_,
            student2_.name as name5_5_1_,
            student2_.passport_id as passport6_5_1_,
            students1_.course_id as course_i2_6_0__,
            students1_.student_id as student_1_6_0__ 
        from
            course course0_ 
        left outer join
            student_course students1_ 
                on course0_.id=students1_.course_id 
        left outer join
            student student2_ 
                on students1_.student_id=student2_.id 
        where
            (
                course0_.is_deleted = 0
            )
     */
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
    

    /*
       Solves using named query that creates inner joins
       1. Solved by creating joined_query add JOIN FETCH
       2. ie @NamedQuery(name="find_all_courses_with_join_fetch",
			query="select c from Course c JOIN FETCH c.students s"),
			
    Hibernate: 
    select
        course0_.id as id1_0_0_,
        student2_.id as id1_5_1_,
        course0_.created_date as created_2_0_0_,
        course0_.is_deleted as is_delet3_0_0_,
        course0_.modified_date as modified4_0_0_,
        course0_.name as name5_0_0_,
        student2_.city as city2_5_1_,
        student2_.line1 as line3_5_1_,
        student2_.line2 as line4_5_1_,
        student2_.name as name5_5_1_,
        student2_.passport_id as passport6_5_1_,
        students1_.course_id as course_i2_6_0__,
        students1_.student_id as student_1_6_0__ 
    from
        course course0_ 
    inner join
        student_course students1_ 
            on course0_.id=students1_.course_id 
    inner join
        student student2_ 
            on students1_.student_id=student2_.id 
    where
        (
            course0_.is_deleted = 0
        )
     */
    
    @Test
    @Transactional
    public void solvingNPlusOneProblem_JoinFetch() {
    	
    	List<Course> courses =  em.createNamedQuery("find_all_courses_with_join_fetch",Course.class)
    			                     .getResultList();
    	
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
