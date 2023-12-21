package com.example.LibraryManagementSystem.controllers;

import com.example.LibraryManagementSystem.dto.requestDTO.StudentRequest;
import com.example.LibraryManagementSystem.dto.responseDTO.StudentResponse;
import com.example.LibraryManagementSystem.models.Student;
import com.example.LibraryManagementSystem.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;
    @PostMapping("/add-student")
    public ResponseEntity addStudent(@RequestBody StudentRequest studentRequest){
        StudentResponse response = studentService.addStudent(studentRequest);
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    @GetMapping("/get-student")
    public ResponseEntity getStudent(@RequestParam("id") int regNo){
        Student response = studentService.getStudent(regNo);
        if(response == null){
            return new ResponseEntity<>("Student Not found" , HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response.getName() , HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-student")
    public ResponseEntity deleteStudent(@RequestParam("id") int regNo){
        studentService.deleteStudent(regNo);
        return new ResponseEntity<>("Student has been Deleted" , HttpStatus.ACCEPTED);
    }

    @PutMapping("/update-age")
    public ResponseEntity updateAge(@RequestParam("id") int regNo , @RequestParam("age") int age){
        StudentResponse response = studentService.updateAge(regNo , age);

        if(response == null){
            return new ResponseEntity<>("Student was not found" , HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response , HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all-students")
    public ResponseEntity getAllStudents(){
        List<StudentResponse> studentList = studentService.getAllStudents();

        return new ResponseEntity<>(studentList , HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-male-students")
    public ResponseEntity getMaleStudents(){

        List<StudentResponse> studentList = studentService.getMaleStudents();

        return new ResponseEntity(studentList , HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all-male")
    public ResponseEntity getAllMale(){
        List<String> males = studentService.getAllMale();
        return new ResponseEntity<>(males , HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all-female")
    public ResponseEntity getAllFemale(){
        List<String> females = studentService.getAllFemale();
        return new ResponseEntity<>(females , HttpStatus.ACCEPTED);
    }
}
