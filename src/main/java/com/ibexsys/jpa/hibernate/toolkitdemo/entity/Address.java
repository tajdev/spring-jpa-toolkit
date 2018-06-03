package com.ibexsys.jpa.hibernate.toolkitdemo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Cacheable // Causes entity to do cache lookup in 2nd level cache
@SQLDelete(sql = "update address set is_deleted=true where id=?") // Hibernate Specific
@Where(clause = "is_deleted = false") 
@Table(name = "Address") // maps any table
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	
	@ManyToMany(mappedBy = "addresss")
	@JsonIgnore
	private List<Student> students = new ArrayList<Student>();

	private boolean isDeleted;

	private String line1;
	private String line2;
	private String line3;
	private String city;
	private String state;
	private String zip;
	private String country;	
	private AddressType addressType;

	protected Address() {
	};
	
	public Address(String line1, String line2,String line3,
		       String city, String state, String zip,
		       String country,AddressType addressType) {
	
	//this.students.add(student);
	this.line1 = line1;
	this.line2 = line2;
	this.line3 = line3;
	this.city = city;
	this.state = state;
	this.zip = zip;
	this.country = country;
	this.addressType = addressType;
	this.isDeleted = false;
}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}
	
	public String getLine3() {
		return line3;
	}

	public void setLine3(String line3) {
		this.line3 = line3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getId() {
		return id;
	}
	
// @Todo - clean up	

//	public Student getStudent() {
//		return student;
//	}
//
//	public void setStudent(Student student) {
//		this.student = student;
//	}

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<Student> getStudents() {
		return students;
	}
	
	public void addStudent(Student student) {
		this.students.add(student);
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", students=" + students + ", isDeleted=" + isDeleted + ", line1=" + line1
				+ ", line2=" + line2 + ", line3=" + line3 + ", city=" + city + ", state=" + state + ", zip=" + zip
				+ ", country=" + country + ", addressType=" + addressType + "]";
	}
	


}
