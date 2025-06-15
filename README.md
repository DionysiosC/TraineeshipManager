#  Traineeship Management Web App

# Table of Contents

1. [Introduction](#introduction)  
2. [Development](#development)  
   - [Maven Dependencies](#maven-dependencies)  
3. [Usage Overview](#usage-overview)  
4. [Assignment Logic](#assignment-logic)  
5. [Testing & Final Notes](#testing--final-notes)  
6. [Instructions](#instructions)

##  Introduction
This is a full-stack web application for managing university internships (traineeships). The main users are:
- **Committee members** (assign and finalize internships)
- **Professors** (supervisors)
- **Companies** (post and evaluate positions)
- **Students** (apply and log activity)

Developed in 2025, for the needs of the Software Engineering course at the University of Ioannina by me, [@IoannisBouzas](https://github.com/IoannisBouzas), and [@pousoupou](https://github.com/pousoupou).


##  Development  
Built with **Java 23.0.2** and **Spring Boot** for a complete MVC approach and **Thymeleaf** for a solid front-end. A **MySQL** DB and RDBMS is used.

Dependencies are listed in the `pom.xml` file. Summary:

### Maven Dependencies

| Dependency                             | Version   | Scope     | Description                          |
|----------------------------------------|-----------|-----------|--------------------------------------|
| spring-boot-starter-security           | 3.4.3     | compile   | Security support                     |
| spring-boot-starter-thymeleaf          | 3.4.3     | compile   | Template engine                      |
| spring-boot-starter-web                | 3.4.3     | compile   | RESTful web services                 |
| thymeleaf-extras-springsecurity6       | 3.4.3     | compile   | Thymeleaf-Spring Security integration|
| mysql-connector-j                      | Inherited | runtime   | MySQL driver                         |
| spring-boot-starter-test               | 3.4.3     | test      | Testing support                      |
| spring-security-test                   | Inherited | test      | Spring Security testing              |
| lombok                                 | 1.18.36   | provided  | Boilerplate code reduction           |
| spring-boot-starter-data-jpa           | 3.4.3     | compile   | ORM & data access                    |

> *Versions marked “Inherited” use Spring Boot parent: `3.4.3`.*

---

##  Usage Overview

This app supports the full internship lifecycle:

- Students declare interest, log activity.
- Companies post positions, evaluate interns.
- Professors supervise.
- Committees assign and finalize internships.

> ⚠️ Committee handles all assignments—students express interest in doing an internship only 

---

## Assignment Logic
The most interesting aspect of this app could be the way the app proposes to the committee a more suitable internship for a given student to complete.

The committee does not just pick a position to assign an internship to a student or a supervisor professor. The **Jaccard similarity metric** (more info [here)](https://en.wikipedia.org/wiki/Jaccard_index) is used to "calculate" the similarity between a student's interests and the position's related topics.

If the result number is greater than a set threshold, while the student's skills are included in the position's required skills, the position is then listed as a possible match for the committee to choose for the candidate student.

Alternatively, the committee can choose to pick an internship for an intern that matches his preferred location of doing an internship, given that the skills requirements of the position are again fulfilled. The committee can also view positions that satisfy both the student's interests and preferred location criteria.

The same logic is applied when assigning a supervisor to an internship. In this case, the app uses the supervisor's interests. Otherwise, the committee can choose to assign the supervisor with the smallest 'load', i.e. that supervises the least number of internships among the other supervisors.

---

## Testing & Final Notes

- Tested mostly manually, but automatically too with **JUnit** and **Mockito**
- This version is a **refactored backend** with improved logic and fewer bugs, while the UI is simpler than the delivered version.

## Instructions
A basic way to run the app:
1.  Create a DB named *project_db*. This MySQL command can be used:
```sql 
CREATE DATABASE project_db;
```
2.  At *\src\main\resources*, create an *application.properties* file that includes the code shown below. Update the password field accordingly. 

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/project_db?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password= <your password>

spring.jpa.hibernate.ddl-auto=update
```
3.  If no process uses port 8080, type the correct instruction on the terminal (cmd or bash), depending on the host OS:
```bash
(at the root folder of the project)
mvnw.cmd spring-boot:run # windows
./mvnw spring-boot:run # linux
```
---
