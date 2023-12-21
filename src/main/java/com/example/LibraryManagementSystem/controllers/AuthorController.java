package com.example.LibraryManagementSystem.controllers;

import com.example.LibraryManagementSystem.dto.responseDTO.AuthorResponse;
import com.example.LibraryManagementSystem.exceptions.AuthorNotFoundException;
import com.example.LibraryManagementSystem.models.Author;
import com.example.LibraryManagementSystem.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/add-author")
    public String addAuthor(@RequestBody Author author){

        return authorService.addAuthor(author);
    }

    // update the email id of an author  -->> observer lastActivity column
    @PutMapping("/update-email/{author-id}/{email}")
    public ResponseEntity updateAuthorEmail(@PathVariable("author-id") int authorId , @PathVariable("email") String email){
        try{
            AuthorResponse response = authorService.updateAuthorEmail(authorId , email);
            return  new ResponseEntity<>(response , HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    // Get the names of all the books written by a particular author
    @GetMapping("/books-of-author/{author-id}")
    public ResponseEntity booksOfAuthor(@PathVariable("author-id") int authorId){
        try {
            List<String> bookNames = authorService.booksOfAuthor(authorId);
            return new ResponseEntity<>(bookNames , HttpStatus.ACCEPTED);
        }
        catch (AuthorNotFoundException e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    // Get the names of authors who have written more than 'x' number of books
    @GetMapping("/get-authors-by-number-of-books/{x}")
    public ResponseEntity getAuthorsByNoOfBooks(@PathVariable("x") int x){
        List<String> authors = authorService.getAuthorsByNoOfBooks(x);
        return new ResponseEntity<>(authors , HttpStatus.ACCEPTED);
    }
}
