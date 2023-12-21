package com.example.LibraryManagementSystem.services;

import com.example.LibraryManagementSystem.dto.responseDTO.IssueBookResponse;
import com.example.LibraryManagementSystem.enums.TransactionStatus;
import com.example.LibraryManagementSystem.exceptions.BookNotAvailableException;
import com.example.LibraryManagementSystem.exceptions.StudentNotFoundException;
import com.example.LibraryManagementSystem.models.Book;
import com.example.LibraryManagementSystem.models.LibraryCard;
import com.example.LibraryManagementSystem.models.Student;
import com.example.LibraryManagementSystem.models.Transaction;
import com.example.LibraryManagementSystem.repositories.BookRepository;
import com.example.LibraryManagementSystem.repositories.StudentRepository;
import com.example.LibraryManagementSystem.repositories.TransactionRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public IssueBookResponse issueBook(int bookId, int studentId) {

        // Check if the student id is valid or not
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if(optionalStudent.isEmpty()){
            throw new StudentNotFoundException("Invalid Student id!!!");
        }

        // Check if the book id is valid or not
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(optionalBook.isEmpty()){
            throw new BookNotAvailableException("Invalid Book id!!!");
        }

        // Check if the book is already issued or not
        Book book = optionalBook.get();
        if(book.isIssued()){
            throw new BookNotAvailableException(("Book is already issued!!!"));
        }

        // Now issue the book
        Student student = optionalStudent.get();

        // Create the Transaction
        Transaction transaction = Transaction.builder()
                .transactionNumber(String.valueOf(UUID.randomUUID()))
                .transactionStatus(TransactionStatus.SUCCESS)
                .book(book)
                .libraryCard(student.getLibraryCard())
                .build();

        // Save the transaction to DB to get the id
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Update the Book
        book.setIssued(true);
        book.getTransactions().add(savedTransaction);

        // Update the Library Card
        student.getLibraryCard().getTransactions().add(savedTransaction);

        // Save to DB
        bookRepository.save(book);
        studentRepository.save(student);

        // Email the student
        String text = "Hi "+student.getName()+ ",\n" +
                "  The below book has been issued to you \n" +
                "Title  : "+book.getTitle()+"\n"+
                "Author : "+book.getAuthor().getName()+"\n"+
                "Transaction Number : "+savedTransaction.getTransactionNumber();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("lms09101999@gmail.com");
        simpleMailMessage.setTo(student.getEmail());
        simpleMailMessage.setSubject("Congrats! Book Issued");
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);

        // Prepare the response
        return IssueBookResponse.builder()
                .transactionNumber(savedTransaction.getTransactionNumber())
                .transactionTime(savedTransaction.getTransactionTime())
                .transactionStatus(savedTransaction.getTransactionStatus())
                .bookName(book.getTitle())
                .authorName(book.getAuthor().getName())
                .StudentName(student.getName())
                .libraryCardNumber(student.getLibraryCard().getCardNo())
                .build();
    }

    public String returnBook(int bookId, int studentId) {

        // Check if the StudentID is valid or not
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if(optionalStudent.isEmpty()){
            throw new StudentNotFoundException("Invalid Student id!!!");
        }

        // Check if the book id is valid or not
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(optionalBook.isEmpty()){
            throw new BookNotAvailableException("Invalid Book id!!!");
        }

        // Update the Book
        Book book = optionalBook.get();
        book.setIssued(false);

        // Save the Book to DB
        bookRepository.save(book);

        return "Book has been returned";
    }
}
