package org.example.dao;

import org.example.model.Student;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet resultSet = null;
    int st;//status

    public int insert(Student student) {
        connection = ConnectionFactory.getConnection();
        try {
            String query = "insert into student(fname,lname,address,mobile_no,email_id,city,designation,dob,doj,salary) "
                    + "values(?,?,?,?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, student.getFname());
            ps.setString(2, student.getLname());
            ps.setString(3, student.getAddress());
            ps.setString(4, student.getMobileNo());
            ps.setString(5, student.getMailId());
            ps.setString(6, student.getCity());
            ps.setString(7, student.getDesignation());
            ps.setDate(8, student.getDob());
            ps.setDate(9, student.getDoj());
            ps.setBigDecimal(10, student.getSalary());
            st = ps.executeUpdate();
            System.out.println("inserted student " + st);
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;
    }

    public int update(Student student) {
        connection = ConnectionFactory.getConnection();
        try {
            String query = "update student set fname=?,lname=?,address=?,mobile_no=?,email_id=?,city=?, "
                    + "designation=?,dob=?,doj=?,salary=? "
                    + "where id=? ";
            ps = connection.prepareStatement(query);
            ps.setString(1, student.getFname());
            ps.setString(2, student.getLname());
            ps.setString(3, student.getAddress());
            ps.setString(4, student.getMobileNo());
            ps.setString(5, student.getMailId());
            ps.setString(6, student.getCity());
            ps.setString(7, student.getDesignation());
            ps.setDate(8, student.getDob());
            ps.setDate(9, student.getDoj());
            ps.setBigDecimal(10, student.getSalary());
            ps.setLong(11, student.getId());
            st = ps.executeUpdate();
            System.out.println("updated student " + st);
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;
    }

    public int delete(long id) {
        connection = ConnectionFactory.getConnection();
        try {
            String query = "delete from student where id=? ";
            ps = connection.prepareStatement(query);
            ps.setLong(1, id);
            st = ps.executeUpdate();
            System.out.println("deleted student " + st);
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return st;
    }

    public Student fetchById(long id) {
        Student student = new Student();
        connection = ConnectionFactory.getConnection();
        try {
            String query = "select * from student where id=?";
            ps = connection.prepareStatement(query);
            ps.setLong(1, id);
            resultSet = ps.executeQuery();
            student = createStudentFromResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return student;
    }

    public Student fetchByEmailId(String emailId) {
        Student student = new Student();
        connection = ConnectionFactory.getConnection();
        try {

            // Найти студентов по emailID
            String query = "select * from student where \"email_id\" =?";
            ps = connection.prepareStatement(query);
            ps.setString(1, emailId);
            resultSet = ps.executeQuery();

            student = createStudentFromResultSet(resultSet);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return student;
    }

    public Student fetchByMobileNo(String mobileNo) {
        Student student = new Student();
        connection = ConnectionFactory.getConnection();
        try {


            String query = "select * from student where  mobile_no like ?";
            ps = connection.prepareStatement(query);
           // ps.setString(1, mobileNo);
            ps.setString(1, "%" + mobileNo+ "%");
            resultSet = ps.executeQuery();
            // Найти студентов по Номеру телефона

            student = createStudentFromResultSet(resultSet);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return student;
    }

    public List<Student> searchByName(String name, String lname) {
        List<Student> studentList = new ArrayList<Student>();

        connection = ConnectionFactory.getConnection();
        try {

            // Найти студентов по Имени
            // Модернизируйте метод. С возможностью искать и по имени и по фамилии с помощью слова like
            String query = "SELECT * FROM student " +
                    "WHERE fname like ? or lname like ? ";

            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + name + "%");
            ps.setString(2, "%" + lname + "%");
            resultSet = ps.executeQuery();

            studentList = createListFromResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return studentList;
    }

    public List<Student> fetchByCity(String city) {
        List<Student> studentList = new ArrayList<Student>();

        connection = ConnectionFactory.getConnection();
        try {
            String query = "select * from student where  city=?";
            ps = connection.prepareStatement(query);
            ps.setString(1, city);
            resultSet = ps.executeQuery();
            studentList = createListFromResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return studentList;
    }

    public List<Student> fetchBySalaryRange(BigDecimal lowerSalary, BigDecimal higherSalary) {
        List<Student> studentList = new ArrayList<Student>();

        connection = ConnectionFactory.getConnection();
        try {

            String query = "SELECT * FROM student" +
                    " WHERE salary BETWEEN  ? AND ?";

            ps = connection.prepareStatement(query);
            ps.setBigDecimal(1, lowerSalary );
            ps.setBigDecimal(2, higherSalary);
            resultSet = ps.executeQuery();

            studentList = createListFromResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return studentList;
    }

    public List<Student> fetchByDob(Date dob) {
        List<Student> studentList = new ArrayList<>();

        connection = ConnectionFactory.getConnection();
        try {
            String query = "select * from student where dob=?";
            ps = connection.prepareStatement(query);
            ps.setDate(1, dob);
            resultSet = ps.executeQuery();

            studentList = createListFromResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return studentList;
    }

    public List<Student> fetchByRangeDoj(Date startDate, Date endDate) {
        List<Student> studentList = new ArrayList<>();

        connection = ConnectionFactory.getConnection();
        try {


            // Найти студентов с датами От и До

            String query = "SELECT * FROM student " +
                    "WHERE doj BETWEEN  ? AND ? ";

            ps = connection.prepareStatement(query);
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            resultSet = ps.executeQuery();

            studentList = createListFromResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return studentList;
    }

    public List<Student> fetchAll() {
        List<Student> studentList = new ArrayList<>();

        connection = ConnectionFactory.getConnection();
        try {

            //done?
            // Модернизируйте поиск - отсортировав от большего к меньшему по полю id

            String query = "SELECT * FROM  student ORDER BY \"id\" DESC ";

            ps = connection.prepareStatement(query);
            resultSet = ps.executeQuery();
            studentList = createListFromResultSet(resultSet);

        } catch (PSQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return studentList;
    }


    public Student createStudentFromResultSet(ResultSet resultSet) {
        Student student = new Student();
        try {
            while (resultSet.next()) {
                student.setId(resultSet.getLong("id"));
                student.setFname(resultSet.getString("fname"));
                student.setLname(resultSet.getString("lname"));
                student.setAddress(resultSet.getString("address"));
                student.setMobileNo(resultSet.getString("mobile_no"));
                student.setMailId(resultSet.getString("email_id"));
                student.setCity(resultSet.getString("city"));
                student.setDesignation(resultSet.getString("designation"));
                student.setDob(resultSet.getDate("dob"));
                student.setDoj(resultSet.getDate("doj"));
                student.setSalary(resultSet.getBigDecimal("salary"));
                //student.setAddDate(resultSet.getTimestamp("add_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public List<Student> createListFromResultSet(ResultSet resultSet) {
        List<Student> studentList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getLong("id"));
                student.setFname(resultSet.getString("fname"));
                student.setLname(resultSet.getString("lname"));
                student.setAddress(resultSet.getString("address"));
                student.setMobileNo(resultSet.getString("mobile_no"));
                student.setMailId(resultSet.getString("email_id"));
                student.setCity(resultSet.getString("city"));
                student.setDesignation(resultSet.getString("designation"));
                student.setDob(resultSet.getDate("dob"));
                student.setDoj(resultSet.getDate("doj"));
                student.setSalary(resultSet.getBigDecimal("salary"));
                //student.setAddDate(resultSet.getTimestamp("add_date"));
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

}
