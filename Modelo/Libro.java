package Modelo;

public class Libro {
    private String id;
    private String titulo;
    private String autor;

    public Libro(String id, String titulo, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Libro: " + titulo + " | Autor: " + autor;
    }
}