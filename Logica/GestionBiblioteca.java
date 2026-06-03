package Logica;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import Grafos.GrafoBiblioteca;
import Modelo.Libro;
import Modelo.Prestamo;
import Modelo.Usuario;

public class GestionBiblioteca {
        private final ArrayList<Libro> catalogo;

        private final ArbolBinarioLibros indicePorTitulo;

        private final Queue<Usuario> colaEspera;

        private final GrafoBiblioteca grafoPrestamos;

        private final ArrayList<Prestamo> historialPrestamos;

    public GestionBiblioteca() {
        this.catalogo = new ArrayList<>();
        this.indicePorTitulo = new ArbolBinarioLibros();
        this.colaEspera = new LinkedList<>();
        this.grafoPrestamos = new GrafoBiblioteca();
        this.historialPrestamos = new ArrayList<>();
    }

    public void registrarLibro(Libro nuevoLibro) {
        catalogo.add(nuevoLibro);
        indicePorTitulo.insertar(nuevoLibro);
        System.out.println(">> Éxito: Libro '" + nuevoLibro.getTitulo() + "' registrado.");
    }

    public void mostrarLibros() {
        System.out.println("\n CATÁLOGO DE BIBLIOTECA (orden de registro — ArrayList) ");
        if (catalogo.isEmpty()) {
            System.out.println("El catálogo está vacío.");
        } else {
            for (Libro l : catalogo) {
                System.out.println(l);
            }
        }
    }

        public void buscarLibro(String tituloBuscado) {
        List<Libro> coincidencias = indicePorTitulo.buscarPorTitulo(tituloBuscado);
        if (coincidencias.isEmpty()) {
            System.out.println("\n[x] No se encontró el libro: " + tituloBuscado);
        } else {
            System.out.println("\n[!] Resultado(s) vía índice ABB:");
            for (Libro libro : coincidencias) {
                System.out.println(libro);
            }
        }
    }

    public void reporteRecorridoInOrden() {
        System.out.println("\n REPORTE — INORDEN (árbol de libros) ");
        indicePorTitulo.imprimirInOrden();
    }

    public void reporteRecorridoPreOrden() {
        System.out.println("\n REPORTE — PREORDEN (árbol de libros) ");
        indicePorTitulo.imprimirPreOrden();
    }

    public void reporteRecorridoPostOrden() {
        System.out.println("\n REPORTE — POSTORDEN (árbol de libros) ");
        indicePorTitulo.imprimirPostOrden();
    }

    public void reporteAlturaArbol() {
        System.out.println("\n REPORTE — ALTURA DEL ÁRBOL DE ÍNDICE ");
        if (indicePorTitulo.estaVacio()) {
            System.out.println("El árbol está vacío (altura 0).");
        } else {
            System.out.println("Altura del árbol: " + indicePorTitulo.altura());
        }
    }

    public void solicitarPrestamo(String usuario, String identidad) {
        Usuario nuevo = new Usuario(usuario, identidad);
        colaEspera.add(nuevo);
        System.out.println(">> " + usuario + " Proximo en la fila.");
    }

    public void registrarUsuarioEnCola(String nombre, String cedula) {
        Usuario nuevo = new Usuario(nombre, cedula);
        colaEspera.add(nuevo);
        System.out.println(">> " + nuevo.getNombre() + "  Ingresó a la fila de espera.");
    }

    public void procesarSiguientePrestamo(String idLibro) {
        if (colaEspera.isEmpty()) {
            System.out.println(">>No hay usuarios en fila de espera.");
            return;
        }
        Libro libro = buscarLibroPorId(idLibro);
        if (libro == null) {
            System.out.println(">> No existe un libro con ID: " + idLibro);
            return;
        }
        Usuario atendido = colaEspera.poll();
        Prestamo registro = new Prestamo(atendido, libro);
        historialPrestamos.add(registro);
        grafoPrestamos.registrarPrestamo(atendido, libro);
        System.out.println(">> Atendiendo a: " + atendido.getNombre());
        System.out.println(">> Libro prestado: " + libro.getTitulo());
        System.out.println(">> Registrado en el grafo de préstamos.");
        System.out.println(">> Quedan " + colaEspera.size() + " personas en espera.");
    }

    private Libro buscarLibroPorId(String id) {
        for (Libro libro : catalogo) {
            if (libro.getId().equalsIgnoreCase(id)) {
                return libro;
            }
        }
        return null;
    }

    public void mostrarEstadisticasGrafoLibros() {
        grafoPrestamos.mostrarLibrosMasPrestados();
    }

    public void mostrarEstadisticasGrafoUsuarios() {
        grafoPrestamos.mostrarUsuariosMasSolicitantes();
    }

    public void mostrarRecorridoLibroEnGrafo(String idLibro) {
        grafoPrestamos.mostrarRecorridoLibro(idLibro);
    }
}
