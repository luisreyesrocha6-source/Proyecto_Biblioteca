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
            System.out.println("7. Módulo de grafos (estadísticas y recorridos)");
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
                    System.out.println("\n--- Índice en árbol binario de búsqueda (libros) ---");
                    System.out.println("1. Buscar por título");
                    System.out.println("2. Recorrido INORDEN (imprimir libros)");
                    System.out.println("3. Recorrido PREORDEN (imprimir libros)");
                    System.out.println("4. Recorrido POSTORDEN (imprimir libros)");
                    System.out.println("5. Calcular altura del árbol");
                    System.out.print("Opción: ");
                    int subBusqueda = leer.nextInt();
                    leer.nextLine();
                    switch (subBusqueda) {
                        case 1:
                            System.out.print("Ingrese el título: ");
                            gestion.buscarLibro(leer.nextLine());
                            break;
                        case 2:
                            gestion.reporteRecorridoInOrden();
                            break;
                        case 3:
                            gestion.reporteRecorridoPreOrden();
                            break;
                        case 4:
                            gestion.reporteRecorridoPostOrden();
                            break;
                        case 5:
                            gestion.reporteAlturaArbol();
                            break;
                        default:
                            System.out.println("Opción no válida.");
                            break;
                    }
                    break;
                case 4:
                    System.out.print("Nombre del usuario: ");
                    String nombreU = leer.nextLine();
                    System.out.print("Ingrese CC: ");
                    String idU = leer.nextLine();
                    gestion.registrarUsuarioEnCola(nombreU, idU);
                    break;
                case 5:
                    System.out.print("Nombre del solicitante: ");
                    String solicitante = leer.nextLine();
                    System.out.print("Identificacion del solicitante: ");
                    String idSolicitanteString = leer.nextLine();
                    gestion.solicitarPrestamo(solicitante, idSolicitanteString);
                    break;
                case 6:
                    System.out.print("ID del libro a prestar: ");
                    String idLibroPrestamo = leer.nextLine();
                    gestion.procesarSiguientePrestamo(idLibroPrestamo);
                    break;
                case 7:
                    System.out.println("\n--- MÓDULO DE GRAFOS ---");
                    System.out.println("1. Libros más prestados (grado de entrada)");
                    System.out.println("2. Usuarios más solicitantes (grado de salida)");
                    System.out.println("3. Recorrido del libro (camino y BFS)");
                    System.out.print("Opción: ");
                    int subGrafo = leer.nextInt();
                    leer.nextLine();
                    switch (subGrafo) {
                        case 1:
                            gestion.mostrarEstadisticasGrafoLibros();
                            break;
                        case 2:
                            gestion.mostrarEstadisticasGrafoUsuarios();
                            break;
                        case 3:
                            System.out.print("ID del libro: ");
                            gestion.mostrarRecorridoLibroEnGrafo(leer.nextLine());
                            break;
                        default:
                            System.out.println("Opción no válida.");
                            break;
                    }
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