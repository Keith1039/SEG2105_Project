package com.example.project;

public class Administrator extends User {

    public Administrator(String username, String password) {
        super(username, password);
    }

    private Course createCourse(String code, String name) {
        Course course = new Course(code, name);
        // add course to database
        return course;
    }

    private void editCourse(String oldCode, String newCode, String newName) {
        // use oldCode (current course code) to find Course from database
        // course.setCode(newCode);
        // course.setName(newName);
        return;
    }

    private void deleteCourse(String code, String name) {
        // find Course using code (current course code)
        // remove Course from database
        return;
    }

    private void deleteUser(String username) {
        // find User using username
        // cannot delete admin
        // remove User from database
        return;
    }
}
