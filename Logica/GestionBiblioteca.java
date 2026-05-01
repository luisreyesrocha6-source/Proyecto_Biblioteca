package Logica;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import Modelo.Libro;
import Modelo.Usuario;

public class GestionBiblioteca {
    // Catálogo de libros usando ArrayList para acceso rápido y orden de inserción
    private final ArrayList<Libro> catalogo;

    // Índice de búsqueda: ABB por título (y desempate por ID)
    private final ArbolBinarioLibros indicePorTitulo;

    // Cola para gestionar el turno de usuarios, garantiza FIFO
    private final Queue<Usuario> colaEspera;

    public GestionBiblioteca() {
        this.catalogo = new ArrayList<>();
        this.indicePorTitulo = new ArbolBinarioLibros();
        this.colaEspera = new LinkedList<>();
    }


    public void registrarLibro(Libro nuevoLibro) {
        catalogo.add(nuevoLibro);
        indicePorTitulo.insertar(nuevoLibro);
        System.out.println(">> Éxito: Libro '" + nuevoLibro.getTitulo() + "' registrado.");
    }

    public void mostrarLibros() {
        System.out.println("\n CATÁLOGO DE BIBLIOTECA ");
        if (catalogo.isEmpty()) {
            System.out.println("El catálogo está vacío.");
        } else {
            for (Libro l : catalogo) {
                System.out.println(l);
            }
        }
    }

    /**
     * Búsqueda por título usando el árbol binario de búsqueda (todas las coincidencias).
     */
    public void buscarLibro(String tituloBuscado) {
        List<Libro> coincidencias = indicePorTitulo.buscarPorTitulo(tituloBuscado);
        if (coincidencias.isEmpty()) {
            System.out.println("\n[x] No se encontró el libro: " + tituloBuscado);
        } else {
            System.out.println("\n[!] Resultado(s) en el índice (ABB):");
            for (Libro libro : coincidencias) {
                System.out.println(libro);
            }
        }
    }

    /** Lista el catálogo según recorrido inorden del ABB (orden por título). */
    public void mostrarLibrosOrdenadosPorTitulo() {
        System.out.println("\n CATÁLOGO (recorrido inorden del árbol por título) ");
        if (indicePorTitulo.estaVacio()) {
            System.out.println("El índice está vacío.");
            return;
        }
        for (Libro libro : indicePorTitulo.recorridoInOrden()) {
            System.out.println(libro);
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