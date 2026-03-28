package Logica;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import Modelo.Libro;
import Modelo.Usuario;

public class GestionBiblioteca {
    // Estructura de Lista para agendar libros
    private ArrayList<Libro> catalogo = new ArrayList<>();
    // Estructura de Cola para turnos de usuarios
    private Queue<Usuario> colaEspera = new LinkedList<>();

    public void registrarLibro(Libro nuevoLibro) {
        catalogo.add(nuevoLibro);
        System.out.println(">> Éxito: Libro '" + nuevoLibro.getTitulo() + " Registrado.");
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

    public void buscarLibro(String tituloBuscado) {
        boolean encontrado = false;

        for (Libro l : catalogo) {
            if (l.getTitulo().toLowerCase().contains(tituloBuscado.toLowerCase())) {
                System.out.println("\n[!] Libro encontrado: ");
                System.out.println(l.toString());
                encontrado = true;
            }
        } // Cierra el for

        if (!encontrado) {
            System.out.println("\n[x] No se encontró el libro: " + tituloBuscado);
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