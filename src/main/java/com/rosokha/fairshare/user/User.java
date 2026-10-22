package com.rosokha.fairshare.user;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "login", nullable = false, length = 32, unique = true)
    private String login;

    @Column(name = "full_name", nullable = false, length = 64)
    private String fullName;

    @Column(name = "email", nullable = false, length = 128, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public User() {}

    public User(String login, String fullName, String email, String password) {
        this.login = login;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = UserRole.USER;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User(String login, String fullName, String email, String password, UserRole userRole, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.login = login;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = userRole;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole userRole) {
        this.role = userRole;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
