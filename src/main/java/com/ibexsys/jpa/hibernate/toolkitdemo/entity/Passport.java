package com.ibexsys.jpa.hibernate.toolkitdemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// Notes:
// Make sure to not create setter for ID and/or use it in constructor
// Make default constructor protected since JPA does not allow use of it by others

@Entity
@Table(name="Passport")  // maps any table
public class Passport {
	
	private static Logger LOGGER = LoggerFactory.getLogger(Passport.class);
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="number", nullable = false)   // maps any name
	private String number;
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy="passport")
    private Student student;
	
	
	protected Passport() {};
	
	public Passport(String number) {
		this.number = number;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return String.format("Passport[%s][%s]", number,student);
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
