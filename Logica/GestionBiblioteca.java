package Logica;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import Modelo.Libro;

public class GestionBiblioteca {
    // Estructura de Lista para agendar libros
    private ArrayList<Libro> catalogo = new ArrayList<>();
    // Estructura de Cola para turnos de usuarios
    private Queue<String> colaEspera = new LinkedList<>();

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

    public void buscarLibro(String titulo) {
        for (Libro l : catalogo) {
            if (l.getTitulo().equalsIgnoreCase(titulo))
                System.out.println("Libro encontrado: " + 1);
            return;
        }
    }

    public void solicitarPrestamo(String usuario) {
        colaEspera.add(usuario);
        System.out.println(">> " + usuario + " ha entrado a la cola de espera.");
    }

    public void registrarUsuarioEnCola(String nombre) {
        colaEspera.add(nombre);
        System.out.println(">> " + nombre + " ha sido agregado a la cola de espera.");
    }

    public void procesarPrestamo() {
        if (colaEspera.isEmpty()) {
            System.out.println("No hay usuarios en la cola de espera.");
        } else {
            String usuarioAtendido = colaEspera.poll();
            System.out.println(">> Procesando prestamo para: " + usuarioAtendido);
            System.out.print("Libro prestado");
        }
    }
}