package com.example.LibraryManagementSystem.repositories;

import com.example.LibraryManagementSystem.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction , Integer> {
}
