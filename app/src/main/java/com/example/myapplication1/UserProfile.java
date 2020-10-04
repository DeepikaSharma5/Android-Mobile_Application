package com.example.myapplication1;

public class UserProfile {
    public String username;
    public String userEmail;
    public String userPassword;
    public String userAddres;
    public String userContactNo;

    public UserProfile(){

    }

    public UserProfile(String username, String userEmail, String userPassword, String userAddres, String userContactNo) {
        this.username = username;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userAddres = userAddres;
        this.userContactNo = userContactNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserAddres() {
        return userAddres;
    }

    public void setUserAddres(String userAddres) {
        this.userAddres = userAddres;
    }

    public String getUserContactNo() {
        return userContactNo;
    }

    public void setUserContactNo(String userContactNo) {
        this.userContactNo = userContactNo;
    }
}