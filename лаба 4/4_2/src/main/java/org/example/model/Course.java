package org.example.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Course {
    private String name;
    private int id;
    private int studentsNumber;
}
