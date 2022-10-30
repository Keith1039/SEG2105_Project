package com.example.project;

public class User {
    protected String username;
    protected String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean login(String user, String pass) {
        if (user == null || pass == null) {
            return false;
        }

        return user.equals(username) && pass.equals(password);
    }
}
