package Grafos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Modelo.Libro;
import Modelo.Usuario;

/**
 * Grafo de préstamos: usuarios → libros (solicitudes) y cadena temporal por libro (recorrido).
 */
public class GrafoBiblioteca {

    private static final String ORIGEN = "BIBLIOTECA";

    private final GrafoDirigido grafoSolicitudes;
    private final Map<String, GrafoDirigido> grafoRecorridoPorLibro;
    private final Map<String, Integer> conteoPrestamosPorLibro;
    private final Map<String, Integer> conteoSolicitudesPorUsuario;

    public GrafoBiblioteca() {
        this.grafoSolicitudes = new GrafoDirigido();
        this.grafoRecorridoPorLibro = new HashMap<>();
        this.conteoPrestamosPorLibro = new HashMap<>();
        this.conteoSolicitudesPorUsuario = new HashMap<>();
    }

    public void registrarPrestamo(Usuario usuario, Libro libro) {
        String idUsuario = claveUsuario(usuario);
        String idLibro = claveLibro(libro);

        String etiquetaUsuario = usuario.getNombre() + " (CC: " + usuario.getCedula() + ")";
        String etiquetaLibro = libro.getTitulo() + " [ID: " + libro.getId() + "]";

        grafoSolicitudes.agregarVertice(idUsuario, etiquetaUsuario);
        grafoSolicitudes.agregarVertice(idLibro, etiquetaLibro);
        grafoSolicitudes.agregarArista(idUsuario, idLibro);

        conteoSolicitudesPorUsuario.merge(idUsuario, 1, Integer::sum);
        conteoPrestamosPorLibro.merge(idLibro, 1, Integer::sum);

        GrafoDirigido recorrido = grafoRecorridoPorLibro.computeIfAbsent(idLibro, k -> {
            GrafoDirigido g = new GrafoDirigido();
            g.agregarVertice(ORIGEN, "Biblioteca (inicio)");
            return g;
        });

        recorrido.agregarVertice(idUsuario, etiquetaUsuario);

        List<String> caminoActual = recorrido.caminoDesde(ORIGEN);
        String ultimo = caminoActual.isEmpty() ? ORIGEN : caminoActual.get(caminoActual.size() - 1);
        recorrido.agregarArista(ultimo, idUsuario);
    }

    public void mostrarLibrosMasPrestados() {
        System.out.println("\n=== ESTADÍSTICAS (GRAFO) — LIBROS MÁS PRESTADOS ===");
        System.out.println("(Grado de entrada en aristas usuario → libro)\n");

        if (conteoPrestamosPorLibro.isEmpty()) {
            System.out.println("No hay préstamos registrados en el grafo.");
            return;
        }

        List<Map.Entry<String, Integer>> ranking = new ArrayList<>(conteoPrestamosPorLibro.entrySet());
        ranking.sort(Comparator.comparing(Map.Entry<String, Integer>::getValue).reversed());

        int pos = 1;
        for (Map.Entry<String, Integer> entrada : ranking) {
            String idLibro = entrada.getKey();
            int prestamos = entrada.getValue();
            int gradoEntrada = grafoSolicitudes.gradoEntrada(idLibro);
            System.out.println(pos + ". " + grafoSolicitudes.obtenerEtiqueta(idLibro));
            System.out.println("   Préstamos: " + prestamos + " | Grado entrada (grafo): " + gradoEntrada);
            pos++;
        }

        System.out.println("\n--- Vista del grafo de solicitudes (muestra) ---");
        for (Map.Entry<String, Integer> entrada : ranking) {
            String idLibro = entrada.getKey();
            System.out.print("  " + grafoSolicitudes.obtenerEtiqueta(idLibro) + " ← ");
            imprimirPrestatarios(idLibro);
            System.out.println();
        }
    }

    public void mostrarUsuariosMasSolicitantes() {
        System.out.println("\n=== ESTADÍSTICAS (GRAFO) — USUARIOS MÁS SOLICITANTES ===");
        System.out.println("(Grado de salida en aristas usuario → libro)\n");

        if (conteoSolicitudesPorUsuario.isEmpty()) {
            System.out.println("No hay solicitudes registradas en el grafo.");
            return;
        }

        List<Map.Entry<String, Integer>> ranking = new ArrayList<>(conteoSolicitudesPorUsuario.entrySet());
        ranking.sort(Comparator.comparing(Map.Entry<String, Integer>::getValue).reversed());

        int pos = 1;
        for (Map.Entry<String, Integer> entrada : ranking) {
            String idUsuario = entrada.getKey();
            int solicitudes = entrada.getValue();
            int gradoSalida = grafoSolicitudes.gradoSalida(idUsuario);
            System.out.println(pos + ". " + grafoSolicitudes.obtenerEtiqueta(idUsuario));
            System.out.println("   Solicitudes: " + solicitudes + " | Grado salida (grafo): " + gradoSalida);
            pos++;
        }

        System.out.println("\n--- Aristas del grafo (usuario → libro) ---");
        for (Map.Entry<String, Integer> entrada : ranking) {
            String idUsuario = entrada.getKey();
            System.out.print("  " + grafoSolicitudes.obtenerEtiqueta(idUsuario) + " → ");
            for (String destino : grafoSolicitudes.vecinos(idUsuario)) {
                System.out.print(grafoSolicitudes.obtenerEtiqueta(destino) + "; ");
            }
            System.out.println();
        }
    }

    public void mostrarRecorridoLibro(String idLibroBuscado) {
        String idLibro = "L:" + idLibroBuscado;

        System.out.println("\n=== RECORRIDO DEL LIBRO (GRAFO DIRIGIDO) ===");

        GrafoDirigido recorrido = grafoRecorridoPorLibro.get(idLibro);
        if (recorrido == null) {
            System.out.println("No hay historial de préstamos para el libro con ID: " + idLibroBuscado);
            return;
        }

        List<String> camino = recorrido.caminoDesde(ORIGEN);

        System.out.println("Libro: " + grafoSolicitudes.obtenerEtiqueta(idLibro));
        System.out.println("\nCadena de préstamos (camino en el grafo):");
        for (int i = 0; i < camino.size(); i++) {
            if (i > 0) {
                System.out.println("    ↓");
            }
            System.out.println("  " + recorrido.obtenerEtiqueta(camino.get(i)));
        }

        System.out.println("\nRecorrido BFS desde la biblioteca:");
        List<String> bfs = recorrido.recorridoBFS(ORIGEN);
        for (String nodo : bfs) {
            System.out.println("  • " + recorrido.obtenerEtiqueta(nodo));
        }

        System.out.println("\nTotal de préstamos registrados: " + conteoPrestamosPorLibro.getOrDefault(idLibro, 0));
    }

    private void imprimirPrestatarios(String idLibro) {
        boolean primero = true;
        for (String vertice : grafoSolicitudes.todosLosVertices()) {
            if (vertice.startsWith("U:") && grafoSolicitudes.vecinos(vertice).contains(idLibro)) {
                if (!primero) {
                    System.out.print(", ");
                }
                System.out.print(grafoSolicitudes.obtenerEtiqueta(vertice));
                primero = false;
            }
        }
        if (primero) {
            System.out.print("(sin prestatarios)");
        }
    }

    private static String claveUsuario(Usuario usuario) {
        return "U:" + usuario.getCedula();
    }

    private static String claveLibro(Libro libro) {
        return "L:" + libro.getId();
    }

    public boolean tieneDatos() {
        return !conteoPrestamosPorLibro.isEmpty();
    }
}
