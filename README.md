## Chaos Monkey with Springboot

----------------------------------
## PRINCIPLES OF CHAOS ENGINEERING

- https://principlesofchaos.org/?lang=ENcontent

Chaos Monkey the solution, based on the idea behind Nelflix's tool, designed to test Spring Boot applications.
There are two required steps for enabling Chaos Monkey for a Spring Boot application. 

----------------------------------


### First, let's add the library chaos-monkey-spring-boot to the project's dependencies.


		<dependency>
			<groupId>de.codecentric</groupId>
			<artifactId>chaos-monkey-spring-boot</artifactId>
			<version>2.2.0</version>
		</dependency>
		
----------------------------------

### Then, we should activate the profile chaos-monkey on application startup.

          -  spring.profiles.active=chaos-monkey

          - $ java -jar target/order-service-1.0-SNAPSHOT.jar --spring.profiles.active=chaos-monkey

          - inside eclipse enable profile 

## Enable Spring Boot Actuator Endpoints

			management:
			  endpoint:
				chaosmonkey:
				  enabled: true
			  endpoints:
				web:
				  exposure:
					include: health,info,chaosmonkey


or 


#### End point

        management.endpoint.chaosmonkey.enabled: true
        management.endpoint.chaosmonkeyjmx.enabled=true

#### inlcude all endpoints
     
     management.endpoints.web.exposure.include=*

#### include specific endpoints
     
     management.endpoints.web.exposure.include=health,info,metrics,chaosmonkey


--------------------------

### GET
http://localhost:8080/actuator/chaosmonkey


			{
			   "chaosMonkeyProperties":{
				  "enabled":true
			   },
			   "assaultProperties":{
				  "level":5,
				  "latencyRangeStart":10000,
				  "latencyRangeEnd":15000,
				  "latencyActive":false,
				  "exceptionsActive":false,
				  "exception":{
					 "type":null,
					 "arguments":null
				  },
				  "killApplicationActive":false,
				  "memoryActive":false,
				  "memoryMillisecondsHoldFilledMemory":90000,
				  "memoryMillisecondsWaitNextIncrease":1000,
				  "memoryFillIncrementFraction":0.15,
				  "memoryFillTargetFraction":0.25,
				  "runtimeAssaultCronExpression":"OFF",
				  "watchedCustomServices":null
			   },
			   "watcherProperties":{
				  "controller":true,
				  "restController":true,
				  "service":true,
				  "repository":true,
				  "component":false
			   }
			}


### GET
http://localhost:8080/actuator/chaosmonkey/status

      Ready to be evil!

### POST

http://localhost:8080/actuator/chaosmonkey/enable 

### POST

http://localhost:8080/actuator/chaosmonkey/disable

### GET
http://localhost:8080/actuator/chaosmonkey/watchers

			{
			"controller": true,
			"restController": false,
			"service": true,
			"repository": false,
			"component": false
			}


### GET
http://localhost:8080/actuator/chaosmonkey/assaults


			{
			   "level":5,
			   "latencyRangeStart":10000,
			   "latencyRangeEnd":15000,
			   "latencyActive":false,
			   "exceptionsActive":false,
			   "exception":{
				  "type":null,
				  "arguments":null
			   },
			   "killApplicationActive":false,
			   "memoryActive":false,
			   "memoryMillisecondsHoldFilledMemory":90000,
			   "memoryMillisecondsWaitNextIncrease":1000,
			   "memoryFillIncrementFraction":0.15,
			   "memoryFillTargetFraction":0.25,
			   "runtimeAssaultCronExpression":"OFF",
			   "watchedCustomServices":null
			}


## Exception 

### POST

http://localhost:8080/actuator/chaosmonkey/assaults

			{
			"level": 3,
			"latencyRangeStart": 20000,
			"latencyRangeEnd": 50000,
			"latencyActive": false,
			"exceptionsActive": true,
			"killApplicationActive": false,
			"exception": {
				"type": "java.lang.IllegalArgumentException",
				"arguments": [
				   {
					"className": "java.lang.String",
					 "value": "custom illegal argument exception"
					}
				  ] 
				 }
			}


## Latency
### POST 

http://localhost:8080/actuator/chaosmonkey/assaults 


			{
			"level": 1,
			"latencyRangeStart": 20000,
			"latencyRangeEnd": 50000,
			"latencyActive": true,
			"exceptionsActive": false,
			"killApplicationActive": false,
			"restartApplicationActive":false
			}
			

## Method test

 You can customize the behavior of all watchers using the property watchedCustomServices and decide which classes and public methods should be attacked. 
If no signatures are stored, all classes and public methods, recognized by the watchers are attacked by default.You can either maintain the list in your 
application properties or adjust it at runtime using the Spring Boot Actuator Endpoint.

### POST 

http://localhost:8080/actuator/chaosmonkey/assaults \


			{
			"level": 1,
			"latencyRangeStart": 20000,
			"latencyRangeEnd": 50000,
			"latencyActive": true,
			"exceptionsActive": false,
			"killApplicationActive": false,
			"restartApplicationActive":false,
			"watchedCustomServices": ["com.khan.vaquar.demo.controller.StudentController.findAll"]

			}



After adding assult in findAll method you can see only latancy inside of findAll method 

            http://localhost:8080/students
            
Other methods are working without issue 

            http://localhost:8080/student?id=10001            


Same logic applicable to exceptions :

			{
					"level": 1,
					"latencyRangeStart": 20000,
					"latencyRangeEnd": 50000,
					"latencyActive": false,
					"exceptionsActive": true,
							"exception": {
						"type": "java.lang.IllegalArgumentException",
						"arguments": [
						   {
							"className": "java.lang.String",
							 "value": "custom illegal argument exception"
							}
						  ] 
						 },

					"killApplicationActive": false,
					"restartApplicationActive":false,
					"watchedCustomServices": ["com.khan.vaquar.demo.controller.StudentController.findAll"]

					}


After adding assult in findAll method you can see only exception inside of findAll method 

            http://localhost:8080/students
            
Other methods are working without issue 

            http://localhost:8080/student?id=10001 


### POST 

http://localhost:8080/actuator/chaosmonkey/assaults \

			{
			"level": 1,
			"latencyRangeStart": 1000,
			"latencyRangeEnd": 3000,
			"latencyActive": true,
			"exceptionsActive": false,
			"killApplicationActive": false,
			"memoryActive": false,
			"memoryMillisecondsHoldFilledMemory": 90000,
			"memoryMillisecondsWaitNextIncrease": 1000,
			"memoryFillIncrementFraction": 0.15,
			"memoryFillTargetFraction": 0.25,
			"runtimeAssaultCronExpression": "OFF",
			"watchedCustomServices": null
			}

or 

			{
			"level": 2,
			"latencyRangeStart": 1000,
			"latencyRangeEnd": 3000,
			"latencyActive": true,
			"exceptionsActive": false,
			"killApplicationActive": false,
			"memoryActive": true,
			"memoryMillisecondsHoldFilledMemory": 90000,
			"memoryMillisecondsWaitNextIncrease": 1000,
			"memoryFillIncrementFraction": 99.10,
			"memoryFillTargetFraction": 99.10,
			"runtimeAssaultCronExpression": "OFF",
			"watchedCustomServices": null
			}



### POST

 http://localhost:8080/actuator/chaosmonkey/assaults 

			{
				"latencyRangeStart": 2000,
				"latencyRangeEnd": 5000,
				"latencyActive": true,
				"exceptionsActive": false,
				"killApplicationActive": false
			}



### POST

 http://localhost:8080/actuator/chaosmonkey/assaults

			{
				"latencyActive": false,
				"exceptionsActive": true,
				"killApplicationActive": false
			}




### POST 

http://localhost:8080/actuator/chaosmonkey/assaults


			{
				"latencyActive": false,
				"exceptionsActive": false,
				"killApplicationActive": true
			}



			{
			"chaosMonkeyProperties":{
			"enabled": true
			},
			"assaultProperties":{
			"level": 3,
			"latencyRangeStart": 1000,
			"latencyRangeEnd": 3000,
			"latencyActive": true,
			"exceptionsActive": false,
			"killApplicationActive": false,
			"watchedCustomServices": []
			},
			"watcherProperties":{
			"controller": true,
			"restController": false,
			"service": true,
			"repository": false,
			"component": false
			}
			}



-----------------------------------

## What is Chaos Eng -introductions including how to start your first chaos experiment:
https://www.gremlin.com/community/tutorials/chaos-engineering-the-history-principles-and-practice/

## Good summary of people, tools, companies doing chaos experiments: 
https://coggle.it/diagram/WiKceGDAwgABrmyv/t/chaos-engineeringcompanies%2C-people%2C-tools-practices/0a2d4968c94723e48e1256e67df51d0f4217027143924b23517832f53c536e62

## Tools:

ChaosMonkey for SpringBoot: https://docs.chaostoolkit.org/drivers/cloudfoundry/. Very easy to follow instructions. Easy to turn on/off using Spring profile.

Spinnaker: https://www.spinnaker.io/. Netflix Chaos Monkey does not support deployments that are managed by anything other than Spinnaker. That makes it pretty hard to use Chaos Monkey from Netflix.

Chaos Toolkit - https://docs.chaostoolkit.org/drivers/cloudfoundry/. This tool is particularly helpful to my situation since my applications are deployed in Cloud Foundry and this tool has a CloudFoundry extension. Pretty elaborate, but easy to follow instructions. My preferred tool so far.

Chaos Lemur - https://content.pivotal.io/blog/chaos-lemur-testing-high-availability-on-pivotal-cloud-foundry. This tool has promise but network admin won't share AWS credentials for me to muck with Pivotal cells.

Gramlin -https://www.gremlin.com/

- https://www.youtube.com/watch?v=-smx0-qeurw
- https://www.youtube.com/embed/cefJd2v037U
- https://netflix.github.io/chaosmonkey/
- https://chaostoolkit.org/
- https://codecentric.github.io/chaos-monkey-spring-boot/

---------------------------

application.properties

spring.profiles.active=chaos-monkey
chaos.monkey.enabled=true
############################
chaos.monkey.assaults.level=5

#Latency Assault
chaos.monkey.assaults.latencyActive=true
chaos.monkey.assaults.latencyRangeStart=10000
chaos.monkey.assaults.latencyRangeEnd=15000

# Exception-assault
chaos.monkey.assaults.exceptionsActive=false
# Kill application
chaos.monkey.assaults.killApplicationActive=false
chaos.monkey.assaults.restartApplicationActive=false

# Memory
chaos.monkey.assaults.memoryActive=false
chaos.monkey.assaults.memoryMillisecondsHoldFilledMemory=900
chaos.monkey.assaults.memoryMillisecondsWaitNextIncrease=1000
chaos.monkey.assaults.memoryFillIncrementFraction=0.15
chaos.monkey.assaults.memoryFillTargetFraction=0.25
# watcher
chaos.monkey.watcher.controller=true
chaos.monkey.watcher.restController=true
chaos.monkey.watcher.service=true
chaos.monkey.watcher.repository=true
##
chaos.monkey.assaults.watchedCustomServices=com.khan.vaquar.demo.controller.StudentController.findAll


#End point
management.endpoint.chaosmonkey.enabled: true
management.endpoint.chaosmonkeyjmx.enabled=true
# inlcude all endpoints
management.endpoints.web.exposure.include=*
# include specific endpoints
#management.endpoints.web.exposure.include=health,info,metrics,chaosmonkey




# Enabling H2 Console
spring.h2.console.enabled=true
##http://localhost:8080/h2-console
spring.datasource.url=jdbc:h2:mem:testdb


#Turn Statistics on
spring.jpa.properties.hibernate.generate_statistics=true
logging.level.org.hibernate.stat=debug
# Show all queries
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.type=trace



----------------------------------------------------

Student

	private Long id;
	private String name;
	private String passportNumber;
	
----------------------------------------------------
StudentJdbcRepository

package com.khan.vaquar.demo.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.khan.vaquar.demo.domain.Student;

@Repository
public class StudentJdbcRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	class StudentRowMapper implements RowMapper<Student> {
		@Override
		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			Student student = new Student();
			student.setId(rs.getLong("id"));
			student.setName(rs.getString("name"));
			student.setPassportNumber(rs.getString("passport_number"));
			return student;
		}

	}

	public List<Student> findAll() {
		return jdbcTemplate.query("select * from student", new StudentRowMapper());
	}

	public Student findById(long id) {
		return jdbcTemplate.queryForObject("select * from student where id=?", new Object[] { id },
				new BeanPropertyRowMapper<Student>(Student.class));
	}

	public int deleteById(long id) {
		return jdbcTemplate.update("delete from student where id=?", new Object[] { id });
	}

	public int insert(Student student) {
		return jdbcTemplate.update("insert into student (id, name, passport_number) " + "values(?,  ?, ?)",
				new Object[] { student.getId(), student.getName(), student.getPassportNumber() });
	}

	public int update(Student student) {
		return jdbcTemplate.update("update student " + " set name = ?, passport_number = ? " + " where id = ?",
				new Object[] { student.getName(), student.getPassportNumber(), student.getId() });
	}

}

----------------------------------------------------

StudentController

package com.khan.vaquar.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.khan.vaquar.demo.domain.Student;
import com.khan.vaquar.demo.service.StuentService;

@RestController
public class StudentController {
	

	@Autowired
	private StuentService stuentService;

	@GetMapping("/students")
	public List<Student> findAll() {
		return stuentService.findAll();
	}

	@GetMapping("/student")
	public Student findById(long id) {
		return stuentService.findById(id);
	}

	@DeleteMapping("/student/delete")
	public int deleteById(long id) {
		return stuentService.deleteById(id);
	}

	@PostMapping("/student/create")
	public int insert(@RequestBody Student student) {
		return stuentService.insert(student);
	}

	@PutMapping("/student/update")
	public int update(@RequestBody Student student) {
		return stuentService.update(student);
	}
	@GetMapping("/sayHello")
	public String sayHello(String name) {
		return "Hello="+name;
	}
	@GetMapping("/sayGoodbye")
	public String sayGoodbye(String name) {
		return "Goodbye "+name;
	}
}
----------------------
StuentService

package com.khan.vaquar.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khan.vaquar.demo.domain.Student;
import com.khan.vaquar.demo.repo.StudentJdbcRepository;
@Service
public class StuentService {
	@Autowired
	private StudentJdbcRepository studentJdbcRepository;
	
	public List<Student> findAll() {
		return studentJdbcRepository.findAll();
	}

	public Student findById(long id) {
		return studentJdbcRepository.findById(id);
	}
	public int deleteById(long id) {
		return studentJdbcRepository.deleteById(id);
	}

	public int insert(Student student) {
		return studentJdbcRepository.insert(student);
	}

	public int update(Student student) {
		return studentJdbcRepository.update(student);
	}

}
---------------------
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.3.1.RELEASE</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.khan.vaquar</groupId>
	<artifactId>demo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>demo</name>
	<description>Demo project for Spring Boot</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>

		<dependency>
			<groupId>de.codecentric</groupId>
			<artifactId>chaos-monkey-spring-boot</artifactId>
			<version>2.2.0</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
	

</project>

------------------------
