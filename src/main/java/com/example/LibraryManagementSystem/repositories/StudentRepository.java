package com.example.LibraryManagementSystem.repositories;

import com.example.LibraryManagementSystem.enums.Gender;
import com.example.LibraryManagementSystem.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student , Integer> {

    List<Student> findByGender(Gender gender);
}
