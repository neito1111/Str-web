package org.example.action;

import org.example.dao.CourseDao;
import org.example.model.Course;

import java.util.List;

public class CourseAction {

    CourseDao dao = new CourseDao();

    int st;

    public void insert(Course course) {
        st = dao.insert(course);
        if (st == 1) {
            System.out.println("Course Inserted Successfully");
        } else if (st == -1) {
            System.out.println("Course Already exists");
        } else {
            System.out.println("Unable to Insert Course");
        }
    }

    public void update(Course course) {
        st = dao.update(course);
        if (st == 1) {
            System.out.println("course Updated Successfully");
        } else {
            System.out.println("Unable to update course");
        }
    }

    public void delete(int id) {
        st = dao.delete(id);
        if (st == 1) {
            System.out.println("Course Deleted Successfully");
        } else {
            System.out.println("No Record Found");
        }
    }

    public void fetchById(int id) {
        Course course = dao.fetchById(id);
        if (course.getId() == 0) {
            System.out.println("No Record Found");
        } else {
            System.out.println("Course Details are :");
            System.out.println(course);
        }
    }

    public void fetchAll() {
        List<Course> courseList = dao.fetchAll();
        if (courseList.isEmpty()) {
            System.out.println("No Record Found");
        } else {
            System.out.println("Course Details are :");
            for (Course course : courseList) {
                System.out.println(course);
            }
        }
    }

    public void fetchMostPopular() {
        List<Course> courseList = dao.fetchMostPopular();
        if (courseList.isEmpty()) {
            System.out.println("No Record Found");
        } else {
            System.out.println("Most popular courses details are :");
            for (Course course : courseList) {
                System.out.println(course);
            }
        }
    }

    public void addStudentToCourse(long studentId, int courseId) {
        st = dao.addStudentToCourse( studentId, courseId);
        if (st == 1) {
            System.out.println("Added student Successfully");
        } else if (st == -1) {
            System.out.println(" Already exists");
        } else {
            System.out.println("Unable to add student");
        }
    }

    public void deleteStudentFromCourse(long studentId, int courseId) {
        st = dao.deleteStudentFromCourse(studentId, courseId);
        if (st == 1) {
            System.out.println("Studetn Deleted from course Successfully");
        } else {
            System.out.println("No Record Found");
        }
    }





}
