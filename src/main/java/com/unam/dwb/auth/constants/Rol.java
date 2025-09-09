package com.unam.dwb.auth.constants;


public enum Rol {
    ROLE_USER("CUSTOMER"),
    ROLE_ADMIN("ADMIN");

    private String nombreRol;

    Rol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }
}
