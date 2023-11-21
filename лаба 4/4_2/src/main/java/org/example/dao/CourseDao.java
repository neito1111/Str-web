package org.example.dao;

import org.example.model.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet resultSet = null;
    int st;//status


    public int insert(Course course) {
        connection = ConnectionFactory.getConnection();
        try {
            String query = "insert into course(name) values (?)";
            ps = connection.prepareStatement(query);

            ps.setString(1, course.getName());

            st = ps.executeUpdate();
            System.out.println("inserted course " + st);
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


    public int update(Course course) {
        connection = ConnectionFactory.getConnection();
        try {
            String query = "update course set name = ? where id = ?";

            ps = connection.prepareStatement(query);
            ps.setString(1, course.getName());
            ps.setLong(2, course.getId());
            st = ps.executeUpdate();
            System.out.println("updated course " + st);
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

    public int delete(int id) {
        connection = ConnectionFactory.getConnection();
        try {
            String query = "delete from course where id=? ";
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            st = ps.executeUpdate();
            System.out.println("deleted course " + st);
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

    public Course fetchById(int id) {

        Course course = new Course();

        connection = ConnectionFactory.getConnection();
        try {
            String query = "SELECT id, name, COALESCE(count,0)  AS count FROM course" +
                    " LEFT JOIN (SELECT course_id, COUNT(student_id)  count FROM studentscourses GROUP BY course_id)" +
                    "  as derivedTable on derivedTable.course_id = course.id where id = ?";

            ps = connection.prepareStatement(query);
            ps.setLong(1, id);
            resultSet = ps.executeQuery();
            course = createCourseFromResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return course;
    }

    public List<Course> fetchAll() {

        List<Course> courseList =  new ArrayList<>();

        connection = ConnectionFactory.getConnection();
        try {
            String query = "SELECT id, name, COALESCE(count,0)  AS count FROM course" +
                    " LEFT JOIN (SELECT course_id, COUNT(student_id)  count FROM studentscourses GROUP BY course_id)" +
                    "  as derivedTable on derivedTable.course_id = course.id ";

            ps = connection.prepareStatement(query);

            resultSet = ps.executeQuery();
            courseList = createCourseListFromResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return courseList;
    }

    //popular

    public List<Course> fetchMostPopular() {

        List<Course> courseList =  new ArrayList<>();

        connection = ConnectionFactory.getConnection();
        try {
            String query = "SELECT course_id as id, course.name, count(student_id) " +
                    "FROM studentscourses " +
                    "inner join course on course.id = studentscourses.course_id " +
                    "GROUP BY course_id, course.name " +
                    "HAVING count(student_id) = (SELECT  MAX (mycount) " +
                    "                            FROM (SELECT course_id, COUNT(student_id)  mycount" +
                    "                                  FROM studentscourses GROUP BY course_id)  AS derivedTable)";

            ps = connection.prepareStatement(query);

            resultSet = ps.executeQuery();
            courseList = createCourseListFromResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return courseList;
    }

    public int addStudentToCourse(long studentId, int courseId) {
        connection = ConnectionFactory.getConnection();
        try {
            String query = "insert into studentscourses values (?, ?)";
            ps = connection.prepareStatement(query);

            ps.setLong(1,studentId);
            ps.setInt(2,courseId);

            st = ps.executeUpdate();
            System.out.println("added student to course " + st);
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

    public int deleteStudentFromCourse(long studentId, int courseId) {
        connection = ConnectionFactory.getConnection();
        try {
            String query = "delete from studentscourses where student_id = ? and course_id = ?";
            ps = connection.prepareStatement(query);

            ps.setLong(1,studentId);
            ps.setInt(2, courseId);

            st = ps.executeUpdate();
            System.out.println("deleted student from course " + st);
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



    private Course createCourseFromResultSet(ResultSet resultSet) {
        Course course = new Course();
        try {
            while (resultSet.next()) {
                course.setId(resultSet.getInt("id"));
                course.setName(resultSet.getString("name"));
                course.setStudentsNumber(resultSet.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }

    private List<Course> createCourseListFromResultSet(ResultSet resultSet) {

        List<Course> courseList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Course course =  new Course();
                course.setId(resultSet.getInt("id"));
                course.setName(resultSet.getString("name"));
                course.setStudentsNumber(resultSet.getInt("count"));
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }





}
