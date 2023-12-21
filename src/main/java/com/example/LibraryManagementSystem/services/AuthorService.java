package com.example.LibraryManagementSystem.services;

import com.example.LibraryManagementSystem.dto.responseDTO.AuthorResponse;
import com.example.LibraryManagementSystem.exceptions.AuthorNotFoundException;
import com.example.LibraryManagementSystem.models.Author;
import com.example.LibraryManagementSystem.models.Book;
import com.example.LibraryManagementSystem.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public String addAuthor(Author author) {

        authorRepository.save(author);
        return "Author has been saved";
    }

    public AuthorResponse updateAuthorEmail(int authorId, String email) {

        // Check if the authorId is valid or not
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isEmpty()) {
            throw new AuthorNotFoundException("Invalid AuthorId!!!");
        }

        // Update the email and save it
        Author author = optionalAuthor.get();
        author.setEmail(email);
        Author savedAuthor = authorRepository.save(author);

        // Prepare authorResponse
        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setName(savedAuthor.getName());
        authorResponse.setAge(savedAuthor.getAge());
        authorResponse.setEmail(savedAuthor.getEmail());
        List<String> bookNames = new ArrayList<>();
        for(Book book : savedAuthor.getBooks()){
            bookNames.add(book.getTitle());
        }
        authorResponse.setBookNames(bookNames);


        return  authorResponse;
    }

    public List<String> booksOfAuthor(int authorId) {

        // Check if the authorId is valid or not
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        if (optionalAuthor.isEmpty()) {
            throw new AuthorNotFoundException("Invalid AuthorId!!!");
        }

        // Get all the Books of author
        List<Book> books = optionalAuthor.get().getBooks();
        List<String> bookNames = new ArrayList<>();
        for(Book book : books){
            bookNames.add(book.getTitle());
        }

        return bookNames;
    }

    public List<String> getAuthorsByNoOfBooks(int x) {

        List<Author> authors = authorRepository.findAll();
        List<String> authorNames = new ArrayList<>();
        for(Author author : authors){
            if(author.getBooks().size() > x){
                authorNames.add(author.getName());
            }
        }
        return authorNames;
    }
}
