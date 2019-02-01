package com.example.vishnu.typroject;

public class UserAllInstantiation {

    public String id,usernameUser, passwordUser, nameUser, phoneUser, role, course;

    public UserAllInstantiation(){

    }

    public UserAllInstantiation(String id,String usernameUser, String passwordUser, String nameUser, String phoneUser, String role, String course) {
        this.id = id;
        this.usernameUser = usernameUser;
        this.passwordUser = passwordUser;
        this.nameUser = nameUser;
        this.phoneUser = phoneUser;
        this.role = role;
        this.course = course;
    }

    public String getId() {
        return id;
    }

    public String getUsernameUser() {
        return usernameUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public String getRole() {
        return role;
    }

    public String getCourse() {
        return course;
    }
}
