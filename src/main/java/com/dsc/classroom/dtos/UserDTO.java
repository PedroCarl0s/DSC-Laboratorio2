package com.dsc.classroom.dtos;

public class UserDTO {
    
    private String name;
    private String email;
    
    public UserDTO() {

    }

    public UserDTO(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
  
}