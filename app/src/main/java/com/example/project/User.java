package com.example.project;

public class User {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }
    public User(){

    }

    public boolean login(String user, String pass) {
        if (user == null || pass == null) {
            return false;
        }

        return user.equals(username) && pass.equals(password);
    }
    public String getUsername(){
        return(username);
    }
    public String getPassword(){
        return(password);
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
