package com.rosokha.fairshare.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationDto {

    @NotBlank(message = "The login is mandatory")
    @Size(min = 3, max = 32, message = "The length of the login should be between 3 and 32 characters")
    private String login;

    @NotBlank(message = "The full name is mandatory")
    @Size(min = 3, max = 64, message = "The length of the full name should be between 3 and 64 characters")
    private String fullName;

    @NotBlank(message = "The email is mandatory")
    @Email(message = "Invalid email format")
    @Size(min = 3, max = 128, message = "The length of the email should be between 3 and 128 characters")
    private String email;

    @NotBlank(message = "The password is mandatory")
    @Size(min = 8, max = 128, message = "The length of the password should be between 8 and 128 characters")
    private String password;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String login, String fullName, String email, String password) {
        this.login = login;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public void setPassword(String password) {
        this.password = password;
    }
}
