package com.khan.vaquar.demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.khan.vaquar.demo.model.Student;
import  com.khan.vaquar.demo.repository.StudentJdbcRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	StudentJdbcRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		logger.info("Student id 10001 -> {}", repository.findById(10001L));

		logger.info("All users 1 -> {}", repository.findAll());
		
		logger.info("Inserting -> {}", repository.insert(new Student(10010L, "Vaquar Khan", "A1234657")));

		logger.info("Update 10001 -> {}", repository.update(new Student(10001L, "Zidan khan", "New-Passport")));

		repository.deleteById(10002L);
		
		logger.info("All users 2 -> {}", repository.findAll());

	}
}
