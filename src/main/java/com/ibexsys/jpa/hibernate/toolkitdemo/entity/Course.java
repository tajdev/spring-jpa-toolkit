package com.ibexsys.jpa.hibernate.toolkitdemo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

// Notes:
// Make sure to not create setter for ID and/or use it in constructor
// Make default constructor protected since JPA does not allow use of it by others

@Entity
@Table(name = "Course") // maps any table name
@Cacheable // Causes entity to do cache lookup in 2nd level cache
@SQLDelete(sql = "update course set is_deleted=true where id=?") // Hibernate Specific
@Where(clause = "is_deleted = false") // Hibernate Specific NOTE: This does not work for native queries
@NamedQueries(value = { @NamedQuery(name = "find_all_courses", query = "select c from Course c"),
		@NamedQuery(name = "find_all_courses_with_join_fetch", query = "select c from Course c JOIN FETCH c.students s"),
		@NamedQuery(name = "find_course_by_name", query = "select c from Course c where name=?") })
public class Course {

	private static Logger LOGGER = LoggerFactory.getLogger(Course.class);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@Column(name = "name", nullable = false) // maps any name
	private String name;

	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	private List<Review> reviews = new ArrayList<Review>();

	@ManyToMany(mappedBy = "courses")
	@JsonIgnore
	private List<Student> students = new ArrayList<Student>();

	@UpdateTimestamp
	private LocalDateTime modifiedDate;

	@CreationTimestamp
	private LocalDateTime createdDate;

	private boolean isDeleted;

	// Executed on delete, method name is arbritray, but should be clear
	@PreRemove
	private void preRemove() {
		this.isDeleted = true;

		LOGGER.info("Displaying PreRemove Value -> {} " + this.isDeleted);
	}

	protected Course() {
	};

	public Course(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void addReview(Review review) {
		this.reviews.add(review);
	}

	public void removeReview(Review review) {
		this.reviews.remove(review);
	}

	public List<Student> getStudents() {
		return students;
	}

	public void addStudent(Student student) {
		this.students.add(student);
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	@Override
	public String toString() {
		return String.format("Course[%s]", name);
	}

}
