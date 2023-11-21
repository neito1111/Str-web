package org.example.start;


import org.example.action.CourseAction;
import org.example.action.StudentAction;
import org.example.model.Course;
import org.example.model.Student;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    StudentAction action = new StudentAction();

    CourseAction courseAction = new CourseAction();

    public static void main(String[] args) {


        String DbOperation = "EXIT(0), ADD(1), UPDATE(2), DELETE(3), FETCHBYID(4), FETCHBYEMAIL(5),\n" +
                "FETCHBYMOBNO(6), FETCHBYNAME(7), FETCHBYCITY(8), FETCHBYSALRANGE(9),\n" +
                "FETCHBYDOB(10), FETCHBYDOJRANGE(11), FETCHALL(12), ADD COURSE(13),\n" +
                "UPDATE COURSE(14), DELETE COURCE(15), FETCH COURSE BY ID(16), FETCH ALL COURSES(17),\n" +
                "FETCH MOST POPULAR COURSES(18), ADD STUDENT TO COURSE(19), DELETE STUDENT FROM COURSE(20)";


        Main mainclass = new Main();
        int value = 0;

        do {
            System.out.println("valid operations are");

            System.out.println(DbOperation);
            System.out.println("Enter valid operation number 0-20");
            Scanner scanner = new Scanner(System.in);
            value = scanner.nextInt();

            switch (value) {
                case 1:
                    mainclass.addStudent();
                    break;
                case 2:
                    mainclass.updateStudent();
                    mainclass.fetchAllStudent();
                    break;
                case 3:
                    mainclass.deleteStudent();
                    break;
                case 4:
                    mainclass.fetchStudentById();
                    break;
                case 5:
                    mainclass.fetchStudentByEmail();
                    break;
                case 6:
                    mainclass.fetchStudentByMobileNo();
                    break;
                case 7:
                    mainclass.fetchStudentByName();
                    break;
                case 8:
                    mainclass.fetchStudentByCity();
                    break;
                case 9:
                    mainclass.fetchStudentBySalaryRange();
                    break;
                case 10:
                    mainclass.fetchStudentByDob();
                    break;
                case 11:
                    mainclass.fetchStudentByDOjRange();
                    break;
                case 12:
                    mainclass.fetchAllStudent();
                    break;
                case 13:
                    mainclass.addCourse();
                    break;

                case 14:
                    mainclass.updateCourse();
                    mainclass.fetchAllCourses();
                    break;
                case 15:
                    mainclass.deleteCourse();
                    break;
                case 16:
                    mainclass.fetchCourseById();
                    break;
                case 17:
                    mainclass.fetchAllCourses();
                    break;
                case 18:
                    mainclass.fetchMostPopular();
                    break;
                case 19:
                    mainclass.addStudentToCurse();
                    break;
                case 20:
                    mainclass.deleteStudentFromCourse();
                    break;
                case 0:
                    System.out.println("Exiting code");
                    break;
                default:
                    System.out.println("Not a valid entry");
            }
        } while (value != 0);


    }


    public void addStudent() {
        Student student = new Student();
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter First Name");
        student.setFname(insert.next());
        System.out.println("Enter Last Name");
        student.setLname(insert.next());

      /*  System.out.println("Enter Address");
        student.setAddress(insert.next());*/

        student.setAddress(InputWorker.enterStringWithRegex("Enter address:", "Enter again, u entered: ", "^[a-zA-Z\\s]+[\\d]+[a-z]*$"));


     /*   System.out.println("Enter Mobile Number");
        student.setMobileNo(insert.next());*/

        student.setMobileNo(InputWorker.enterStringWithRegex("Enter mobile number:", "Enter again, u entered: ", "(^(\\+375|80)(29|33|44|25|17)\\d{7}$)"));

      /*  System.out.println("Enter Mail Id");
        student.setMailId(insert.next());*/

        student.setMailId(InputWorker.enterStringWithRegex("Enter email:", "Enter again, u entered: ", "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"));


        System.out.println("Enter City");
        student.setCity(insert.next());
        System.out.println("Enter Designation");
        student.setDesignation(insert.next());


        /*System.out.println("Enter Dob (yyyy-mm-dd)");
        student.setDob(Date.valueOf(insert.next()));
        System.out.println("Enter Doj  (yyyy-mm-dd)");
        student.setDoj(Date.valueOf(insert.next()));*/

        student.setDob(Date.valueOf(
                        InputWorker.enterStringWithRegex("Enter dob date:", "Enter again, u entered: ", "((19[6-9]\\d)|(^20\\d{2}))-(0[1-9]|(1[0-2]))-((0[1-9]$)|([1-2][0-9]$)|(3[0-1]$))")
                )
        );
        student.setDoj(Date.valueOf(
                        InputWorker.enterStringWithRegex("Enter doj date:", "Enter again, u entered: ", "((19[6-9]\\d)|(^20\\d{2}))-(0[1-9]|(1[0-2]))-((0[1-9]$)|([1-2][0-9]$)|(3[0-1]$))")
                )
        );

        System.out.println("Enter Salary");
        student.setSalary(insert.nextBigDecimal());
        action.insert(student);
    }

    public void updateStudent() {
        Student student = new Student();
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter Student Id");
        student.setId(insert.nextLong());

        System.out.println("Enter First Name");
        student.setFname(insert.next());
        System.out.println("Enter Last Name");
        student.setLname(insert.next());

        student.setAddress(InputWorker.enterStringWithRegex("Enter address:", "Enter again, u entered: ", "^[a-zA-Z\\s]+[\\d]+[a-z]*$"));
        student.setMobileNo(InputWorker.enterStringWithRegex("Enter mobile number:", "Enter again, u entered: ", "(^(\\+375|80)(29|33|44|25|17)\\d{7}$)"));
        student.setMailId(InputWorker.enterStringWithRegex("Enter email:", "Enter again, u entered: ", "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"));


        System.out.println("Enter City");
        student.setCity(insert.next());
        System.out.println("Enter Designation");
        student.setDesignation(insert.next());

        student.setDob(Date.valueOf(
                        InputWorker.enterStringWithRegex("Enter dob date:", "Enter again, u entered: ", "((19[6-9]\\d)|(^20\\d{2}))-(0[1-9]|(1[0-2]))-((0[1-9]$)|([1-2][0-9]$)|(3[0-1]$))")
                )
        );
        student.setDoj(Date.valueOf(
                        InputWorker.enterStringWithRegex("Enter doj date:", "Enter again, u entered: ", "((19[6-9]\\d)|(^20\\d{2}))-(0[1-9]|(1[0-2]))-((0[1-9]$)|([1-2][0-9]$)|(3[0-1]$))")
                )
        );


        System.out.println("Enter Salary");
      //  student.setSalary(insert.nextBigDecimal());
        student.setSalary(BigDecimal.ONE);
        action.update(student);
    }

    public void deleteStudent() {
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter Student Id");
        long id = insert.nextLong();
        action.delete(id);
    }

    public void fetchStudentById() {
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter Student Id");
        long id = insert.nextLong();
        action.fetchById(id);
    }

    public void fetchStudentByEmail() {
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter Student Mail Id");
        String mailId = insert.next();
        action.fetchByEmailId(mailId);
    }

    public void fetchStudentByMobileNo() {
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter Student Mobile Number");
        String mobileNo = insert.next();
        action.fetchByMobileNo(mobileNo);
    }

    public void fetchStudentByName() {
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter Student Name");
        String name = insert.next();
        System.out.println("Enter Student Surname");
        String lname = insert.next();
        action.searchByName(name, lname);
    }

    public void fetchStudentByCity() {
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter Student City");
        String city = insert.next();
        action.fetchByCity(city);
    }

    public void fetchStudentBySalaryRange() {
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter Salary Start Range");
        BigDecimal salaryRange1 = insert.nextBigDecimal();
        System.out.println("Enter Salary End Range");
        BigDecimal salaryRange2 = insert.nextBigDecimal();

        action.fetchBySalaryRange(salaryRange1, salaryRange2);
    }

    public void fetchStudentByDob() {
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter Date of Birth (yyyy-mm-dd)");
        String dob = insert.next();
        action.fetchByDob(Date.valueOf(dob));
    }

    public void fetchStudentByDOjRange() {
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter Start Date of Joining (yyyy-mm-dd)");
        String dob1 = insert.next();
        System.out.println("Enter End Date of Joining (yyyy-mm-dd)");
        String dob2 = insert.next();
        action.fetchByRangeDoj(Date.valueOf(dob1), Date.valueOf(dob2));
    }

    public void fetchAllStudent() {
        action.fetchAll();
    }


    public void addCourse() {
        Course course = new Course();
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter course name");
        course.setName(insert.next());
        courseAction.insert(course);
    }

    public void updateCourse() {
        Course course = new Course();
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter course Id");
        course.setId(insert.nextInt());
        System.out.println("Enter course Name");
        course.setName(insert.next());
        courseAction.update(course);
    }

    public void deleteCourse() {
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter course Id");
        int id = insert.nextInt();
        courseAction.delete(id);
    }

    public void fetchCourseById() {
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter course Id");
        int id = insert.nextInt();
        courseAction.fetchById(id);
    }

    public void fetchAllCourses() {
        courseAction.fetchAll();
    }

    public void fetchMostPopular() {
        courseAction.fetchMostPopular();
    }


    public void addStudentToCurse() {
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter student Id");
        long studentId = insert.nextLong();
        System.out.println("Enter course Id");
        int courseId = insert.nextInt();
        courseAction.addStudentToCourse(studentId, courseId);

    }

    public void deleteStudentFromCourse() {
        Scanner insert = new Scanner(System.in);
        System.out.println("Enter student Id");
        long studentId = insert.nextLong();
        System.out.println("Enter course Id");
        int courseId = insert.nextInt();
        courseAction.deleteStudentFromCourse(studentId, courseId);
    }


}