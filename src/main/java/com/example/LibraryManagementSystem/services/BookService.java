package com.example.LibraryManagementSystem.services;

import com.example.LibraryManagementSystem.dto.responseDTO.BookResponse;
import com.example.LibraryManagementSystem.enums.Genre;
import com.example.LibraryManagementSystem.exceptions.AuthorNotFoundException;
import com.example.LibraryManagementSystem.models.Author;
import com.example.LibraryManagementSystem.models.Book;
import com.example.LibraryManagementSystem.repositories.AuthorRepository;
import com.example.LibraryManagementSystem.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    public String addBook(Book book) {

        // Check if the Author is present or not
        Optional<Author> authorOptional = authorRepository.findById(book.getAuthor().getId());

        if(!authorOptional.isPresent()){
            throw new AuthorNotFoundException("Invalid Author ID !! ");
        }
        Author author = authorOptional.get();

        // Create a Book
        Book newBook = new Book();
        newBook.setAuthor(author);
        newBook.setCost(book.getCost());
        newBook.setNoOfPages(book.getNoOfPages());
        newBook.setTitle(book.getTitle());
        newBook.setGenre(book.getGenre());
        newBook.setIssued(false);

        author.getBooks().add(newBook);

        authorRepository.save(author);
        return "Book has been saved";

    }

    public List<BookResponse> getBookBySQL(String genre, double cost) {

        List<Book> books = bookRepository.getBookBySQL(genre , cost);

        List<BookResponse> bookResponses = new ArrayList<>();
        for(Book b : books){
            BookResponse bookResponse = new BookResponse();
            bookResponse.setTitle(b.getTitle());
            bookResponse.setNoOfPages(b.getNoOfPages());
            bookResponse.setGenre(b.getGenre());
            bookResponse.setAvailable(b.isIssued());
            bookResponse.setCost(b.getCost());
            bookResponse.setAuthorName(b.getAuthor().getName());

            bookResponses.add(bookResponse);
        }

        return bookResponses;
    }

    public List<BookResponse> getBookByHQL(Genre genre, double cost) {

        List<Book> books = bookRepository.getBookByHQL(genre , cost);

        List<BookResponse> bookResponses = new ArrayList<>();
        for(Book b : books){
            BookResponse bookResponse = new BookResponse();
            bookResponse.setTitle(b.getTitle());
            bookResponse.setNoOfPages(b.getNoOfPages());
            bookResponse.setGenre(b.getGenre());
            bookResponse.setAvailable(b.isIssued());
            bookResponse.setCost(b.getCost());
            bookResponse.setAuthorName(b.getAuthor().getName());

            bookResponses.add(bookResponse);
        }

        return bookResponses;
    }

    public String deleteBook(int bookId) {

        // Check if the Book is available
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if(optionalBook.isEmpty()){
            return "There is no book available in the given ID";
        }

        // Delete the Book
        Book book = optionalBook.get();
        String name = book.getTitle();
        bookRepository.delete(book);
        return name +" book has been deleted";
    }

    public List<BookResponse> getBookByGenre(Genre genre) {

        // Get all the Books from the DB
        List<Book> books = bookRepository.findAll();

        // Add all the Books with given genre to bookResponses
        List<BookResponse> bookResponses = new ArrayList<>();
        for(Book book : books){
            if(book.getGenre() == genre){
                BookResponse bookResponse = new BookResponse();
                bookResponse.setTitle(book.getTitle());
                bookResponse.setNoOfPages(book.getNoOfPages());
                bookResponse.setGenre(genre);
                bookResponse.setCost(book.getCost());
                bookResponse.setAvailable(book.isIssued());
                bookResponse.setAuthorName(book.getAuthor().getName());
                bookResponses.add(bookResponse);
            }
        }
        return bookResponses;
    }

    public List<BookResponse> getBookByGenreAndCost(Genre genre, double cost) {

        List<Book> books = bookRepository.getBookByGenreAndCost(genre , cost);
        // Add all the Books with given genre to bookResponses
        List<BookResponse> bookResponses = new ArrayList<>();
        for(Book book : books){
            BookResponse bookResponse = new BookResponse();
            bookResponse.setTitle(book.getTitle());
            bookResponse.setNoOfPages(book.getNoOfPages());
            bookResponse.setGenre(book.getGenre());
            bookResponse.setCost(book.getCost());
            bookResponse.setAvailable(book.isIssued());
            bookResponse.setAuthorName(book.getAuthor().getName());
            bookResponses.add(bookResponse);
        }
        return bookResponses;
    }

    public List<BookResponse> getBookByPages(int a, int b) {

        List<Book> books = bookRepository.getBookByPages(a , b);
        // Add all the Books with given genre to bookResponses
        List<BookResponse> bookResponses = new ArrayList<>();
        for(Book book : books){
            BookResponse bookResponse = new BookResponse();
            bookResponse.setTitle(book.getTitle());
            bookResponse.setNoOfPages(book.getNoOfPages());
            bookResponse.setGenre(book.getGenre());
            bookResponse.setCost(book.getCost());
            bookResponse.setAvailable(book.isIssued());
            bookResponse.setAuthorName(book.getAuthor().getName());
            bookResponses.add(bookResponse);
        }
        return bookResponses;
    }

    public List<String> getAuthorByGenre(Genre genre) {

        // Get all the Books from the DB
        List<Book> books = bookRepository.findAll();

        // Add all the author names of the Books with given genre
        List<String> authors = new ArrayList<>();
        for(Book book : books){
            if(book.getGenre() == genre){
                String name = book.getAuthor().getName();
                if(!authors.contains(name)) authors.add(name);
            }
        }

        return authors;
    }
}
