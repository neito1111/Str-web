package org.example.model;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Student {
    private long id;
    private String fname;
    private String lname;
    private String address;
    private String mobileNo;
    private String mailId;
    private String city;
    private String designation;
    private Date dob;
    private Date doj;
    private BigDecimal salary;
    private Timestamp addDate;
}
