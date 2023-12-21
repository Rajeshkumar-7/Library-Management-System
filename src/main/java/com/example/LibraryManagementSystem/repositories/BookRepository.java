package com.example.LibraryManagementSystem.repositories;

import com.example.LibraryManagementSystem.dto.responseDTO.BookResponse;
import com.example.LibraryManagementSystem.enums.Genre;
import com.example.LibraryManagementSystem.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book , Integer> {


    @Query(value = "select * from book where genre = :genre and cost > :cost" , nativeQuery = true)
    List<Book> getBookBySQL(String genre, double cost);


    @Query(value = "select b from Book b where b.genre = :genre and b.cost > :cost")
    List<Book> getBookByHQL(Genre genre, double cost);

    @Query(value = "select b from Book b where b.genre = :genre and b.cost <= :cost")
    List<Book> getBookByGenreAndCost(Genre genre , double cost);

    @Query(value = "select b from Book b where b.noOfPages > :a and b.noOfPages < :b")
    List<Book> getBookByPages(int a, int b);
}
