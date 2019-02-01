package com.example.vishnu.typroject;

public class User {

    public String usernameUser, passwordUser, nameUser, phoneUser, role, course;

    public User(){

    }

    public User(String usernameUser, String passwordUser, String nameUser, String phoneUser, String role, String course) {
        this.usernameUser = usernameUser;
        this.passwordUser = passwordUser;
        this.nameUser = nameUser;
        this.phoneUser = phoneUser;
        this.role = role;
        this.course = course;
    }
}
