package com.ibexsys.jpa.hibernate.toolkitdemo.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Notes:
// Make sure to not create setter for ID and/or use it in constructor
// Make default constructor protected since JPA does not allow use of it by others

@Entity
public class Review {
	
	private static Logger LOGGER = LoggerFactory.getLogger(Review.class);
	
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
	
	private String description;
	
	@Enumerated(EnumType.STRING)
	private ReviewRating rating;
	
	// matching db column course_id
	@ManyToOne
	private Course course;
	
	protected Review() {};
	
	public Review(ReviewRating rating, String description) {
		this.description = description;
		this.rating = rating;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ReviewRating getRating() {
		return rating;
	}

	public void setRating(ReviewRating rating) {
		this.rating = rating;
	}
	
	@Override
	public String toString() {
		return String.format("Review -> %s %s]",rating,description);
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
