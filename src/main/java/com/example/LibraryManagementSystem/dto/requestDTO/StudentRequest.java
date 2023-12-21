package com.example.LibraryManagementSystem.dto.requestDTO;

import com.example.LibraryManagementSystem.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentRequest {

    String name;

    int age;

    String email;

    Gender gender;

}
