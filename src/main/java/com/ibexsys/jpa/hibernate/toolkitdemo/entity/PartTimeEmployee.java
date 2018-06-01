package com.ibexsys.jpa.hibernate.toolkitdemo.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
public class PartTimeEmployee extends Employee{
	
	private static Logger LOGGER = LoggerFactory.getLogger(PartTimeEmployee.class);
	
	private BigDecimal hourlyWage;
	
	protected PartTimeEmployee() {};
	
	public PartTimeEmployee(String name, BigDecimal hourlyWage) {
		super(name);
		this.hourlyWage = hourlyWage;
	}

	public BigDecimal getHourlyWage() {
		return hourlyWage;
	}

	public void setHourlyWage(BigDecimal hourlyWage) {
		this.hourlyWage = hourlyWage;
	}

}
