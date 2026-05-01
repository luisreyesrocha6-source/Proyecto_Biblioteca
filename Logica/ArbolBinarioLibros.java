package Logica;

import java.util.ArrayList;
import java.util.List;
import Modelo.Libro;

/**
 * Árbol binario de búsqueda que indexa libros por título (sin distinguir mayúsculas)
 * y desempata por ID para mantener un orden total ante títulos repetidos.
 */
public class ArbolBinarioLibros {

    private Nodo raiz;

    private static final class Nodo {
        final Libro libro;
        Nodo izquierda;
        Nodo derecha;

        Nodo(Libro libro) {
            this.libro = libro;
        }
    }

    public void insertar(Libro libro) {
        raiz = insertar(raiz, libro);
    }

    private Nodo insertar(Nodo nodo, Libro libro) {
        if (nodo == null) {
            return new Nodo(libro);
        }
        int cmpTitulo = libro.getTitulo().compareToIgnoreCase(nodo.libro.getTitulo());
        if (cmpTitulo < 0) {
            nodo.izquierda = insertar(nodo.izquierda, libro);
        } else if (cmpTitulo > 0) {
            nodo.derecha = insertar(nodo.derecha, libro);
        } else {
            int cmpId = libro.getId().compareToIgnoreCase(nodo.libro.getId());
            if (cmpId < 0) {
                nodo.izquierda = insertar(nodo.izquierda, libro);
            } else if (cmpId > 0) {
                nodo.derecha = insertar(nodo.derecha, libro);
            }
        }
        return nodo;
    }

    /**
     * Recupera todos los libros cuyo título coincide (ignorando mayúsculas).
     * Recorre solo las ramas del ABB que pueden contener coincidencias.
     */
    public List<Libro> buscarPorTitulo(String tituloBuscado) {
        List<Libro> resultado = new ArrayList<>();
        buscarPorTitulo(raiz, tituloBuscado, resultado);
        return resultado;
    }

    private void buscarPorTitulo(Nodo nodo, String tituloBuscado, List<Libro> resultado) {
        if (nodo == null) {
            return;
        }
        int cmp = tituloBuscado.compareToIgnoreCase(nodo.libro.getTitulo());
        if (cmp < 0) {
            buscarPorTitulo(nodo.izquierda, tituloBuscado, resultado);
        } else if (cmp > 0) {
            buscarPorTitulo(nodo.derecha, tituloBuscado, resultado);
        } else {
            resultado.add(nodo.libro);
            buscarPorTitulo(nodo.izquierda, tituloBuscado, resultado);
            buscarPorTitulo(nodo.derecha, tituloBuscado, resultado);
        }
    }

    /** Recorrido inorden: títulos en orden lexicográfico (útil para listados). */
    public List<Libro> recorridoInOrden() {
        List<Libro> lista = new ArrayList<>();
        inOrden(raiz, lista);
        return lista;
    }

    private void inOrden(Nodo nodo, List<Libro> lista) {
        if (nodo == null) {
            return;
        }
        inOrden(nodo.izquierda, lista);
        lista.add(nodo.libro);
        inOrden(nodo.derecha, lista);
    }

    public boolean estaVacio() {
        return raiz == null;
    }
}
