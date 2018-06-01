package com.ibexsys.jpa.hibernate.toolkitdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibexsys.jpa.hibernate.toolkitdemo.entity.Course;

public interface CourseRepoSpringDataInterface extends JpaRepository<Course, Long> {

}
