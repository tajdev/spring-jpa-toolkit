package com.ibexsys.jpa.hibernate.toolkitdemo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Course;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.FullTimeEmployee;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.PartTimeEmployee;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Passport;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Review;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.ReviewRating;
import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Student;
import com.ibexsys.jpa.hibernate.toolkitdemo.repository.CourseRepository;
import com.ibexsys.jpa.hibernate.toolkitdemo.repository.EmployeeRepository;
import com.ibexsys.jpa.hibernate.toolkitdemo.repository.StudentRepository;

@SpringBootApplication
public class ToolkitJpaDemoApplication implements CommandLineRunner{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	

	

	public static void main(String[] args) {
		SpringApplication.run(ToolkitJpaDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	
	}
	
	
}
