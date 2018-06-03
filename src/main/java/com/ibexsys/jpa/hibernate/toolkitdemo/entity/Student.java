package com.ibexsys.jpa.hibernate.toolkitdemo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Notes:
// Make sure to not create setter for ID and/or use it in constructor
// Make default constructor protected since JPA does not allow use of it by others

@Entity
@Cacheable // Causes entity to do cache lookup in 2nd level cache
@SQLDelete(sql = "update student set is_deleted=true where id=?") // Hibernate Specific
@Where(clause = "is_deleted = false") 
@Table(name = "Student") // maps any table
public class Student {

	private static Logger LOGGER = LoggerFactory.getLogger(Student.class);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	public Student(String name) {
		this.name = name;
	}

	@Column(name = "name", nullable = false) // maps any name
	private String name;

	@ManyToMany
	@JoinTable(name = "STUDENT_ADDRESS", joinColumns = @JoinColumn(name = "STUDENT_ID"), 
	           inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID"))
	private List<Address>  addresss = new ArrayList<Address>();
	
	@ManyToMany
	@JoinTable(name = "STUDENT_COURSE", joinColumns = @JoinColumn(name = "STUDENT_ID"), 
	           inverseJoinColumns = @JoinColumn(name = "COURSE_ID"))
	private List<Course> courses = new ArrayList<Course>();

	// Owner of one-to-one
	@OneToOne(fetch = FetchType.LAZY)
	private Passport passport;
	
	private boolean isDeleted;

	protected Student() {
	};
	
	public String getName() {
		return name;
	}

	public Student(String name, List<Address> addresss, List<Course> courses, 
			       Passport passport, boolean isDeleted) {
		super();
		this.name = name;
		this.addresss = addresss;
		this.courses = courses;
		this.passport = passport;
		this.isDeleted = isDeleted;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Passport getPassport() {
		return passport;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}

	public Long getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return String.format("Student[%s]", name);
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void addCourse(Course course) {
		this.courses.add(course);
	}


	public List<Address> getAddressList() {
		return addresss;
	}

	public void addAddress(Address address) {
		this.addresss.add(address);
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
