package com.lexludi.front.model;

public class Usuario {
    
    private String username;
    private String rol;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(String username, String rol) {
        this.username = username;
        this.rol = rol;
    }

    // --- GETTERS Y SETTERS ---

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}