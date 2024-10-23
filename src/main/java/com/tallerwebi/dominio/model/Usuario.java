package com.tallerwebi.dominio.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String nombreUsuario;
    private String nombre;
    private Integer edad;
    private LocalDate fechaNacimiento;
    private String rol;
    private Boolean activo = false;
    private String tokenRecuperacion;
    private Long meta;
    @Column(name = "imagen_url")
    private String imagenUrl;
    private String biografia;


    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UsuarioNotificacion> notificaciones;


//    @ManyToMany
//    @JoinTable(
//            name = "usuario_genero",
//            joinColumns = @JoinColumn(name = "usuario_id"),
//            inverseJoinColumns = @JoinColumn(name = "genero_id")
//    )
//    private List<Genero> generosFavoritos = new ArrayList<>();

//    @ManyToMany
//    @JoinTable(
//            name = "usuario_autor",
//            joinColumns = @JoinColumn(name = "usuario_id"),
//            inverseJoinColumns = @JoinColumn(name = "autor_id")
//    )
//    private List<Autor> autoresFavoritos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public boolean activo() {
        return activo;
    }

    public void activar() {
        activo = true;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getTokenRecuperacion() {
        return tokenRecuperacion;
    }

    public void setTokenRecuperacion(String tokenRecuperacion) {
        this.tokenRecuperacion = tokenRecuperacion;
    }

    public Long getMeta() {
        return meta;
    }

    public void setMeta(Long meta) {
        this.meta = meta;
    }

    public List<UsuarioNotificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<UsuarioNotificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    //    public List<Genero> getGeneros() {
//        return generosFavoritos;
//    }
//
//    public void setGenero(Genero genero) {
//        this.generosFavoritos.add(genero);
//    }

//    public List<Autor> getAutores() {
//        return autoresFavoritos;
//    }
//
//    public void setAutor(Autor autor) {
//        this.autoresFavoritos.add(autor);
//    }
}