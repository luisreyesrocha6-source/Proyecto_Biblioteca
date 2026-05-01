package Logica;

import java.util.ArrayList;
import java.util.List;
import Modelo.Libro;

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

    public void imprimirInOrden() {
        if (raiz == null) {
            System.out.println("(Árbol vacío — no hay libros en el índice.)");
            return;
        }
        System.out.println("--- Recorrido INORDEN (izq → raíz → der) ---");
        imprimirInOrdenRec(raiz);
    }

    private void imprimirInOrdenRec(Nodo nodo) {
        if (nodo == null) {
            return;
        }
        imprimirInOrdenRec(nodo.izquierda);
        System.out.println(nodo.libro);
        imprimirInOrdenRec(nodo.derecha);
    }

        public void imprimirPreOrden() {
        if (raiz == null) {
            System.out.println("(Árbol vacío — no hay libros en el índice.)");
            return;
        }
        System.out.println("--- Recorrido PREORDEN (raíz → izq → der) ---");
        imprimirPreOrdenRec(raiz);
    }

    private void imprimirPreOrdenRec(Nodo nodo) {
        if (nodo == null) {
            return;
        }
        System.out.println(nodo.libro);
        imprimirPreOrdenRec(nodo.izquierda);
        imprimirPreOrdenRec(nodo.derecha);
    }

    public void imprimirPostOrden() {
        if (raiz == null) {
            System.out.println("(Árbol vacío — no hay libros en el índice.)");
            return;
        }
        System.out.println("--- Recorrido POSTORDEN (izq → der → raíz) ---");
        imprimirPostOrdenRec(raiz);
    }

    private void imprimirPostOrdenRec(Nodo nodo) {
        if (nodo == null) {
            return;
        }
        imprimirPostOrdenRec(nodo.izquierda);
        imprimirPostOrdenRec(nodo.derecha);
        System.out.println(nodo.libro);
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        int altIzq = altura(nodo.izquierda);
        int altDer = altura(nodo.derecha);
        return 1 + Math.max(altIzq, altDer);
    }

    public boolean estaVacio() {
        return raiz == null;
    }
}
