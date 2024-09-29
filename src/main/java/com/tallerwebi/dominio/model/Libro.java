package com.tallerwebi.dominio.model;

import javax.persistence.*;

@Entity
@Table(name = "Libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String isbn;
    private String estadoDeLectura;
    private String autor;
    private String editorial;
    private Rating rating;
    private String descripcion;
    private String genero;
    private Integer puntuacion; //del 1 al 5.
    private String imagenUrl;
    private String reseña;

    public Libro() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() { return autor; }

    public void setAutor(String autor) { this.autor = autor; }

    public String getEditorial() { return editorial; }

    public void setEditorial(String editorial) { this.editorial = editorial; }
    public Rating getRating() { return rating; }

    public void setRating(Rating rating) { this.rating = rating; }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEstadoDeLectura() {
        return estadoDeLectura;
    }

    public void setEstadoDeLectura(String estadoDeLectura) {
        this.estadoDeLectura = estadoDeLectura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getReseña() {
        return reseña;
    }

    public void setReseña(String reseña) {
        this.reseña = reseña;
    }

    public String generarUrlWikipedia() {
        return "https://es.wikipedia.org/wiki/" + this.autor.replace(" ", "_");
    }
}
