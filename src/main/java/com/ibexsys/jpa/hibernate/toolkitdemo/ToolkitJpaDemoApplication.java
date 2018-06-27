package com.ibexsys.jpa.hibernate.toolkitdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This version is from intellij

@SpringBootApplication
public class ToolkitJpaDemoApplication implements CommandLineRunner {


	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(ToolkitJpaDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

}
