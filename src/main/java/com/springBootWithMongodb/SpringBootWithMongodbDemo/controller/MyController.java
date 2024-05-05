package com.springBootWithMongodb.SpringBootWithMongodbDemo.controller;

import com.springBootWithMongodb.SpringBootWithMongodbDemo.model.Student;
import com.springBootWithMongodb.SpringBootWithMongodbDemo.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class MyController {

    @Autowired
    private StudentRepository studentRepository;

    // Endpoint to add a student
    @PostMapping("/")
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        try {
            Student save = this.studentRepository.save(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(save);
        } catch (Exception e) {
            return handleException(e, "Failed to add student");
        }
    }

    // Endpoint to get all students
    @GetMapping("/")
    public ResponseEntity<?> getStudents() {
        try {
            List<Student> students = this.studentRepository.findAll();
            if (students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.ok(students);
            }
        } catch (Exception e) {
            return handleException(e, "Failed to retrieve students");
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id){
        studentRepository.deleteById(id);

        return "Deleted Successfully";
    }

    @PutMapping("/stud/{id}")
    public ResponseEntity updateStudent(@PathVariable("id") Integer id, @RequestBody Student student)
    {
        Optional<Student> opt = studentRepository.findById(id);

        if (opt.isPresent()) {
            Student existingStudent = opt.get();
            existingStudent.setName(student.getName());
            existingStudent.setCity(student.getCity());
            existingStudent.setCollege(student.getCollege());
            return new ResponseEntity<>(studentRepository.save(existingStudent), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    /*@DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
        try {
            Optional<Student> studentToDelete = studentRepository.findById(id.intValue());

            if (studentToDelete.isPresent()) {
                studentRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
            }
        } catch (Exception e) {
            return handleException(e, "Failed to delete student");
        }
    }
*/

    // Exception handling method
    private ResponseEntity<?> handleException(Exception e, String errorMessage) {
        // Log the exception for debugging purposes
        e.printStackTrace();
        // Return an error response with an appropriate status code
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}

