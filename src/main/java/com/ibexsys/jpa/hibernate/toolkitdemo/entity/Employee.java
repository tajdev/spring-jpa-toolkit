package com.ibexsys.jpa.hibernate.toolkitdemo.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;


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
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="native"
	)
	@GenericGenerator(
		    name = "native", 
		    strategy = "native"
	)
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
