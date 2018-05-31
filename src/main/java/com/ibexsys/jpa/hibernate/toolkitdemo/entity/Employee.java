package com.ibexsys.jpa.hibernate.toolkitdemo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


// Notes:
// Make sure to not create setter for ID and/or use it in constructor
// Make default constructor protected since JPA does not allow use of it by others

// MappedSuperClass,Table_Per_Class - not 3rd Normal form
// Joined - concerned about data integrity
// Single_Table - performance

@MappedSuperclass
//@Entity
//@Inheritance(strategy=InheritanceType.JOINED)  // single table is default
//@NamedQueries(value = { 
//@NamedQuery(name="find_all_employees", query="select e from Employee e"),
//@NamedQuery(name="find_employee_by_name", query="select e from Employee e where e.name=?")})
public abstract class Employee {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="name", nullable = false)   // maps any name
	private String name;
	
//	@UpdateTimestamp
//	private LocalDateTime modifiedDate;
//
//	@CreationTimestamp
//	private LocalDateTime createdDate;

	protected Employee() {};
	
	public Employee(String name) {
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
	
	@Override
	public String toString() {
		return String.format("Employee[%s]", name);
	}

}
