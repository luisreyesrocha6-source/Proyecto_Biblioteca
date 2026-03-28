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

    public void buscarLibro(String titulo) {
        for (Libro l : catalogo) {
            if (l.getTitulo().equalsIgnoreCase(titulo))
                System.out.println("Libro encontrado: " + 1);
            return;
        }
    }

    public void solicitarPrestamo(String usuario, String identidad) {
        Usuario nuevo = new Usuario(usuario, identidad);
        colaEspera.add(nuevo);
        System.out.println(">> " + usuario + " Proximo en la fila.");
    }

    public void registrarUsuarioEnCola(String nombre, String id) {
        Usuario nuevo = new Usuario(nombre, id);
        colaEspera.add(nuevo);
        System.out.println(">> " + nombre + " ha sido agregado a la cola de espera.");
    }

    public void procesarPrestamo() {
        if (colaEspera.isEmpty()) {
            System.out.println("No hay usuarios en la cola de espera.");
        } else {
            Usuario usuarioAtendido = colaEspera.poll();
            System.out.println(">> Procesando para: " + usuarioAtendido.getNombre());
            System.out.print("Libro prestado");
        }
    }
}