package com.example.LibraryManagementSystem.dto.responseDTO;

import com.example.LibraryManagementSystem.enums.Genre;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {

    String title;

    int noOfPages;

    Genre genre;

    double cost;

    boolean isAvailable;

    String authorName;
}
