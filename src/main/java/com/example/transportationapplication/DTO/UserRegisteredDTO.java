package com.example.transportationapplication.DTO;

public class UserRegisteredDTO {
    private String name;
    private String email;
    private String password;
    private String role;


    public UserRegisteredDTO() {
        super();
    }

    public UserRegisteredDTO(String role) {
        super();
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
