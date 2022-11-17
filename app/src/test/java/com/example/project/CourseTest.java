package com.example.project;

import junit.framework.TestCase;

import org.junit.Test;

public class CourseTest extends TestCase {

    Course course = new Course("SEG2105","software engineering");

    @Test
    public void testGetCode() {
        String actual = course.getCode();
        assertEquals("SEG2105",actual);

    }

    @Test
    public void testTestGetName() {
        String actual = course.getName();
        assertEquals("software engineering",actual);
    }
}