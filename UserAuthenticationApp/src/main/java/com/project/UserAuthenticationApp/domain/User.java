package com.project.UserAuthenticationApp.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
// Add the @Entity annotation
public class User {
    // Make userId as the primary key by using the @Id annotation
    @Id
    private String userEmail;
    private String password;

    public User() {
    }

    public User(String userId, String password) {
        this.userEmail = userId;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userEmail +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
