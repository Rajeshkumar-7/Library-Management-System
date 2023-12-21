package com.example.LibraryManagementSystem.services;

import com.example.LibraryManagementSystem.dto.requestDTO.StudentRequest;
import com.example.LibraryManagementSystem.dto.responseDTO.StudentResponse;
import com.example.LibraryManagementSystem.enums.CardStatus;
import com.example.LibraryManagementSystem.enums.Gender;
import com.example.LibraryManagementSystem.models.LibraryCard;
import com.example.LibraryManagementSystem.models.Student;
import com.example.LibraryManagementSystem.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public StudentResponse addStudent(StudentRequest studentRequest) {

        // StudentRequest DTO --> student model
        Student student = new Student();
        student.setName(studentRequest.getName());
        student.setAge(studentRequest.getAge());
        student.setEmail(studentRequest.getEmail());
        student.setGender(studentRequest.getGender());

        // Creating a Library card for respective student
        LibraryCard libraryCard = new LibraryCard();
        libraryCard.setCardNo(String.valueOf(UUID.randomUUID()));
        libraryCard.setCardStatus(CardStatus.ACTIVE);

        // Link student and library
        libraryCard.setStudent(student);
        student.setLibraryCard(libraryCard);

        Student savedStudent = studentRepository.save(student);

        // savedStudent model --> StudentResponse DTO
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setName(savedStudent.getName());
        studentResponse.setAge(savedStudent.getAge());
        studentResponse.setEmail(savedStudent.getEmail());
        studentResponse.setLibraryCardNo(savedStudent.getLibraryCard().getCardNo());

        return studentResponse;
    }

    public Student getStudent(int regNo) {
        Optional<Student> studentOptional = studentRepository.findById(regNo);
        if(studentOptional.isPresent()){
            return studentOptional.get();
        }

        return null;
    }

    public void deleteStudent(int regNo) {
        Optional<Student> studentOptional = studentRepository.findById(regNo);
        if(studentOptional.isPresent()){
            studentRepository.delete(studentOptional.get());
        }
    }


    public StudentResponse updateAge(int regNo, int age) {
        Optional<Student> studentOptional = studentRepository.findById(regNo);
        if(!studentOptional.isPresent()){
            return null;
        }

        Student student = studentOptional.get();
        student.setAge(age);
        Student savedStudent = studentRepository.save(student);

        // Convert to DTO
        StudentResponse response = new StudentResponse();
        response.setAge(savedStudent.getAge());
        response.setName(savedStudent.getName());
        response.setEmail(savedStudent.getEmail());
        response.setLibraryCardNo(savedStudent.getLibraryCard().getCardNo());
        return response;
    }

    public List<StudentResponse> getAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        List<StudentResponse> students = new ArrayList<>();

        for(Student student : studentList){
            StudentResponse response = new StudentResponse();
            response.setName(student.getName());
            response.setEmail(student.getEmail());
            response.setAge(student.getAge());
            response.setLibraryCardNo(student.getLibraryCard().getCardNo());
            students.add(response);
        }

        return students;
     }

    public List<StudentResponse> getMaleStudents() {
        List<Student> studentList = new ArrayList<>();
        List<StudentResponse> maleStudents = new ArrayList<>();
        studentList = studentRepository.findAll();

        for(Student student : studentList){
            if(student.getGender() == Gender.MALE){
                StudentResponse response = new StudentResponse();
                response.setName(student.getName());
                response.setEmail(student.getEmail());
                response.setAge(student.getAge());
                response.setLibraryCardNo(student.getLibraryCard().getCardNo());
                maleStudents.add(response);
            }
        }

        return maleStudents;
    }

    public List<String> getAllMale() {

        List<String> males = new ArrayList<>();
        List<Student> students = studentRepository.findByGender(Gender.MALE);

        for(Student student : students){
            males.add(student.getName());
        }

        return males;
    }


    public List<String> getAllFemale() {
        List<String> females = new ArrayList<>();
        List<Student> students = studentRepository.findByGender(Gender.FEMALE);

        for(Student student : students){
            females.add(student.getName());
        }
        return females;
    }
}
