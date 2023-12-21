package com.example.LibraryManagementSystem.repositories;

import com.example.LibraryManagementSystem.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author , Integer> {
}
