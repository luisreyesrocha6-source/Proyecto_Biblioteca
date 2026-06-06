package Grafos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Grafo dirigido genérico basado en lista de adyacencia.
 * Cada vértice se identifica por una clave String y tiene una etiqueta legible.
 */
public class GrafoDirigido {

    // Clave → lista de claves destino (lista de adyacencia)
    private final Map<String, List<String>> adyacencia;

    // Clave → etiqueta legible para mostrar en pantalla
    private final Map<String, String> etiquetas;

    public GrafoDirigido() {
        this.adyacencia = new HashMap<>();
        this.etiquetas = new HashMap<>();
    }

    /**
     * Agrega un vértice con su etiqueta. Si ya existe, no hace nada.
     */
    public void agregarVertice(String clave, String etiqueta) {
        adyacencia.putIfAbsent(clave, new ArrayList<>());
        etiquetas.putIfAbsent(clave, etiqueta);
    }

    /**
     * Agrega una arista dirigida de origen → destino.
     * Si alguno de los vértices no existe, lo crea sin etiqueta.
     * No agrega aristas duplicadas.
     */
    public void agregarArista(String origen, String destino) {
        adyacencia.putIfAbsent(origen, new ArrayList<>());
        adyacencia.putIfAbsent(destino, new ArrayList<>());

        List<String> vecinos = adyacencia.get(origen);
        if (!vecinos.contains(destino)) {
            vecinos.add(destino);
        }
    }

    /**
     * Retorna la etiqueta legible de un vértice.
     * Si no tiene etiqueta registrada, retorna la misma clave.
     */
    public String obtenerEtiqueta(String clave) {
        return etiquetas.getOrDefault(clave, clave);
    }

    /**
     * Retorna la lista de vecinos (destinos) de un vértice.
     */
    public List<String> vecinos(String clave) {
        return adyacencia.getOrDefault(clave, new ArrayList<>());
    }

    /**
     * Retorna todas las claves de vértices del grafo.
     */
    public Set<String> todosLosVertices() {
        return adyacencia.keySet();
    }

    /**
     * Grado de salida: número de aristas que salen del vértice.
     */
    public int gradoSalida(String clave) {
        return adyacencia.getOrDefault(clave, new ArrayList<>()).size();
    }

    /**
     * Grado de entrada: número de aristas que llegan al vértice.
     */
    public int gradoEntrada(String clave) {
        int contador = 0;
        for (List<String> vecinos : adyacencia.values()) {
            if (vecinos.contains(clave)) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Recorre el camino lineal desde el nodo origen siguiendo las aristas.
     * Útil para grafos en forma de cadena (cada nodo tiene un solo sucesor).
     * Retorna la lista ordenada de claves desde el origen hasta el último nodo.
     */
    public List<String> caminoDesde(String origen) {
        List<String> camino = new ArrayList<>();
        Set<String> visitados = new HashSet<>();

        String actual = origen;
        while (actual != null && !visitados.contains(actual)) {
            camino.add(actual);
            visitados.add(actual);

            List<String> siguientes = adyacencia.getOrDefault(actual, new ArrayList<>());
            actual = siguientes.isEmpty() ? null : siguientes.get(0);
        }

        return camino;
    }

    /**
     * Recorrido BFS (Breadth-First Search) desde el nodo origen.
     * Retorna la lista de claves en orden de visita por amplitud.
     */
    public List<String> recorridoBFS(String origen) {
        List<String> resultado = new ArrayList<>();
        Set<String> visitados = new HashSet<>();
        Queue<String> cola = new LinkedList<>();

        if (!adyacencia.containsKey(origen)) {
            return resultado;
        }

        cola.add(origen);
        visitados.add(origen);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            resultado.add(actual);

            for (String vecino : adyacencia.getOrDefault(actual, new ArrayList<>())) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }

        return resultado;
    }

    /**
     * Indica si el grafo no tiene vértices.
     */
    public boolean estaVacio() {
        return adyacencia.isEmpty();
    }
}
