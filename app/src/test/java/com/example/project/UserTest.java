package com.example.project;

import junit.framework.TestCase;

import org.junit.Test;

public class UserTest extends TestCase {

    User user = new User("adjmal","adjmal123");
    @Test
    public void testGetUsername() {
        String actual = user.getUsername();
        assertEquals("adjmal",actual);

    }

    @Test
    public void testGetPassword() {
        String actual = user.getPassword()  ;
        assertEquals("adjmal123",actual);
    }

    @Test
    public void testLogin() {
        assertTrue(user.login("adjmal","adjmal123"));
    }
}