package Grafos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Grafo dirigido con lista de adyacencia.
 */
public class GrafoDirigido {

    private final Map<String, List<String>> adyacencia;
    private final Map<String, String> etiquetas;

    public GrafoDirigido() {
        this.adyacencia = new HashMap<>();
        this.etiquetas = new HashMap<>();
    }

    public void agregarVertice(String id, String etiqueta) {
        adyacencia.putIfAbsent(id, new ArrayList<>());
        etiquetas.put(id, etiqueta);
    }

    public boolean existeVertice(String id) {
        return adyacencia.containsKey(id);
    }

    public void agregarArista(String origen, String destino) {
        adyacencia.putIfAbsent(origen, new ArrayList<>());
        adyacencia.putIfAbsent(destino, new ArrayList<>());
        adyacencia.get(origen).add(destino);
        if (!etiquetas.containsKey(origen)) {
            etiquetas.put(origen, origen);
        }
        if (!etiquetas.containsKey(destino)) {
            etiquetas.put(destino, destino);
        }
    }

    public int gradoSalida(String id) {
        if (!adyacencia.containsKey(id)) {
            return 0;
        }
        return adyacencia.get(id).size();
    }

    public int gradoEntrada(String id) {
        int grado = 0;
        for (List<String> vecinos : adyacencia.values()) {
            for (String destino : vecinos) {
                if (destino.equals(id)) {
                    grado++;
                }
            }
        }
        return grado;
    }

    public String obtenerEtiqueta(String id) {
        return etiquetas.getOrDefault(id, id);
    }

    public List<String> vecinos(String id) {
        List<String> lista = adyacencia.get(id);
        if (lista == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(lista);
    }

    public List<String> todosLosVertices() {
        return new ArrayList<>(adyacencia.keySet());
    }

    public List<String> recorridoBFS(String origen) {
        List<String> orden = new ArrayList<>();
        if (!adyacencia.containsKey(origen)) {
            return orden;
        }
        Map<String, Boolean> visitado = new HashMap<>();
        Queue<String> cola = new LinkedList<>();
        cola.add(origen);
        visitado.put(origen, true);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            orden.add(actual);
            for (String vecino : adyacencia.getOrDefault(actual, List.of())) {
                if (!visitado.containsKey(vecino)) {
                    visitado.put(vecino, true);
                    cola.add(vecino);
                }
            }
        }
        return orden;
    }

    public List<String> caminoDesde(String origen) {
        List<String> camino = new ArrayList<>();
        if (!adyacencia.containsKey(origen)) {
            return camino;
        }
        String actual = origen;
        camino.add(actual);
        while (true) {
            List<String> salientes = adyacencia.get(actual);
            if (salientes == null || salientes.isEmpty()) {
                break;
            }
            actual = salientes.get(salientes.size() - 1);
            if (camino.contains(actual)) {
                break;
            }
            camino.add(actual);
        }
        return camino;
    }
}
