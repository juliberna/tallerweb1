package com.tallerwebi.dominio.model;

import javax.persistence.*;

@Entity
@Table
public class Plan {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer precio;
    private Boolean puedeEscribirResenias;
    private Boolean puedeElegirMetaDeLectura;
    private Boolean puedeLeerOtrasResenias;
    private Boolean puedeObtenerRecomendaciones;
    private Boolean puedeObtenerLogros;
    private Boolean puedeVerForo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Boolean getPuedeEscribirResenias() {
        return puedeEscribirResenias;
    }

    public void setPuedeEscribirResenias(Boolean puedeEscribirResenias) {
        this.puedeEscribirResenias = puedeEscribirResenias;
    }

    public Boolean getPuedeElegirMetaDeLectura() {
        return puedeElegirMetaDeLectura;
    }

    public void setPuedeElegirMetaDeLectura(Boolean puedeElegirMetaDeLectura) {
        this.puedeElegirMetaDeLectura = puedeElegirMetaDeLectura;
    }

    public Boolean getPuedeLeerOtrasResenias() {
        return puedeLeerOtrasResenias;
    }

    public void setPuedeLeerOtrasResenias(Boolean puedeLeerOtrasResenias) {
        this.puedeLeerOtrasResenias = puedeLeerOtrasResenias;
    }

    public Boolean getPuedeObtenerRecomendaciones() {
        return puedeObtenerRecomendaciones;
    }

    public void setPuedeObtenerRecomendaciones(Boolean puedeObtenerRecomendaciones) {
        this.puedeObtenerRecomendaciones = puedeObtenerRecomendaciones;
    }

    public Boolean getPuedeObtenerLogros() {
        return puedeObtenerLogros;
    }

    public void setPuedeObtenerLogros(Boolean puedeObtenerLogros) {
        this.puedeObtenerLogros = puedeObtenerLogros;
    }

    public Boolean getPuedeVerForo() {
        return puedeVerForo;
    }

    public void setPuedeVerForo(Boolean puedeVerForo) {
        this.puedeVerForo = puedeVerForo;
    }
}
