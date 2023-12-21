package com.example.LibraryManagementSystem.controllers;

import com.example.LibraryManagementSystem.dto.responseDTO.IssueBookResponse;
import com.example.LibraryManagementSystem.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/issue-book/{book-id}/{student-id}")
    public ResponseEntity issueBook(@PathVariable("book-id") int bookId , @PathVariable("student-id") int studentId){
        try {
            IssueBookResponse response = transactionService.issueBook(bookId , studentId);
            return new ResponseEntity<>(response , HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/return-book/{book-id}/{student-id}")
    public ResponseEntity returnBook(@PathVariable("book-id") int bookId , @PathVariable("student-id") int studentId){
        try{
            String response = transactionService.returnBook(bookId , studentId);
            return new ResponseEntity<>(response , HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return  new ResponseEntity(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }
}
