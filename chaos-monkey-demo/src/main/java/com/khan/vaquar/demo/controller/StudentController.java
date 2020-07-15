package com.khan.vaquar.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.khan.vaquar.demo.model.Student;
import com.khan.vaquar.demo.service.StudentService;

@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;

	@GetMapping("/students")
	public List<Student> findAll() {
		return studentService.findAll();
	}

	@GetMapping("/student")
	public Student findById(long id) {
		return studentService.findById(id);
	}

	@DeleteMapping("/student/delete")
	public int deleteById(long id) {
		return studentService.deleteById(id);
	}

	@PostMapping("/student/create")
	public int insert(@RequestBody Student student) {
		return studentService.insert(student);
	}

	@PutMapping("/student/update")
	public int update(@RequestBody Student student) {
		return studentService.update(student);
	}

	@GetMapping("/sayHello")
	public String sayHello(String name) {
		return "Hello=" + name;
	}

	@GetMapping("/sayGoodbye")
	public String sayGoodbye(String name) {
		return "Goodbye " + name;
	}
}
