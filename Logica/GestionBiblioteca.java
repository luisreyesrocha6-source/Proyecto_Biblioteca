package Logica;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import Modelo.Libro;
import Modelo.Usuario;

public class GestionBiblioteca {
        private final ArrayList<Libro> catalogo;

        private final ArbolBinarioLibros indicePorTitulo;

        private final Queue<Usuario> colaEspera;

    public GestionBiblioteca() {
        this.catalogo = new ArrayList<>();
        this.indicePorTitulo = new ArbolBinarioLibros();
        this.colaEspera = new LinkedList<>();
    }


1    public void registrarLibro(Libro nuevoLibro) {
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

    public void procesarSiguientePrestamo() {
        if (colaEspera.isEmpty()) {
            System.out.println(">>No hay usuarios en fila de espera.");
        } else {
            Usuario atendido = colaEspera.poll();
            System.out.println(">> Atendiendo a: " + atendido.getNombre());
            System.out.print(">> Libro prestado con exito.");
            System.out.println(">> Quedan " + colaEspera.size() + "personas en espera.");
        }
    }
}
