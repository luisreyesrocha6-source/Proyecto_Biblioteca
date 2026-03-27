package Vista_app;

import Logica.GestionBiblioteca;
import Modelo.Libro;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        GestionBiblioteca gestion = new GestionBiblioteca();
        int opcion;
        boolean continuar = true;

        do {
            System.out.println("\n MENÚ BIBLIOTECA ");
            System.out.println("1. Registrar libros");
            System.out.println("2. Mostrar libros");
            System.out.println("3. Buscar libros");
            System.out.println("4. Registrar usuarios");
            System.out.println("5. Solicitar préstamo");
            System.out.println("6. Procesar préstamo");
            System.out.println("7. Devolver libros");
            System.out.println("8. Salir");
            System.out.print("Seleccione: ");
            opcion = leer.nextInt();
            leer.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese ID: ");
                    String id = leer.nextLine();
                    System.out.print("Ingrese Título: ");
                    String titulo = leer.nextLine();
                    System.out.print("Ingrese Autor: ");
                    String autor = leer.nextLine();
                    Libro nuevo = new Libro(id, titulo, autor);
                    gestion.registrarLibro(nuevo);
                    break;
                case 2:
                    gestion.mostrarLibros();
                    break;
                case 3:
                    System.out.print("Ingrese el título: ");
                    String busqueda = leer.nextLine();
                    gestion.buscarLibro(busqueda);
                    break;
                case 4:
                    System.out.print("Nombre del usuario: ");
                    String nombreU = leer.nextLine();
                    gestion.registrarUsuarioEnCola(nombreU);
                    break;
                case 5:
                    System.out.print("Nombre del solicitante: ");
                    String solicitante = leer.nextLine();
                    gestion.solicitarPrestamo(solicitante);
                    break;
                case 6:
                    gestion.procesarPrestamo();
                    break;
                case 7:
                    System.out.println("Devolucion en proceso...");
                    break;
                case 8:
                    System.out.println("Saliendo del sistema...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }

            if (opcion != 8) {
                System.out.println("\n-----------------");
                System.out.println("\nPresione ENTER para volver al menú principal-");
                System.out.println("O escriba 8 para salir de la Biblioteca.");
                System.out.print("¿Que desea hacer?: ");
                String respuesta = leer.nextLine();
                if (respuesta.equals("8")) {
                    continuar = false;
                }
            }

        } while (continuar);

        System.out.println("Finalizando el programa. ¡Hasta pronto!");
        leer.close();
    }
}