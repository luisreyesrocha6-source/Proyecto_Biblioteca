package Modelo;

/**
 * Registro de un préstamo para el historial y el grafo.
 */
public class Prestamo {

    private final Usuario usuario;
    private final Libro libro;

    public Prestamo(Usuario usuario, Libro libro) {
        this.usuario = usuario;
        this.libro = libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    @Override
    public String toString() {
        return usuario.getNombre() + " ← " + libro.getTitulo() + " (ID: " + libro.getId() + ")";
    }
}
