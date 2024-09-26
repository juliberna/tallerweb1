package com.tallerwebi.dominio.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "rol", nullable = false)
    private String rol;

    @Column(name = "activo", columnDefinition = "TINYINT(1)", nullable = false)
    private boolean activo;

    @Column(name = "edad", nullable = false)
    private Integer edad;

    @Column(name = "fechaNacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "nombreUsuario", nullable = false)
    private String nombreUsuario;

    @Column(name = "tokenRecuperacion", nullable = false)
    private String tokenRecuperacion;

    public Usuario() {
    }

    public Usuario(Long id, String email, String password, String rol, Integer edad, LocalDate fechaNacimiento, String nombre, boolean activo, String nombreUsuario) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.edad = edad;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
        this.activo = activo;
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTokenRecuperacion() {
        return tokenRecuperacion;
    }

    public void setTokenRecuperacion(String tokenRecuperacion) {
        this.tokenRecuperacion = tokenRecuperacion;
    }
}