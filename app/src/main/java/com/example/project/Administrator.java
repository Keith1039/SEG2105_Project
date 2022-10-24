package com.example.project;

public class Administrator extends User {

    public Administrator() {
        super("admin", "admin123");
    }

    private void createCourse(String code, String name) {
        Course course = new Course(code, name);
        // This method only creates the course
        // add to database
        return;
    }

    private void editCourse(String oldCode, String newCode, String newName) {
        // use oldCode (current course code) to find Course from database
        // course.setCode(newCode);
        // course.setName(newName);
        // update database
        return;
    }

    private void deleteCourse(String code) {
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
