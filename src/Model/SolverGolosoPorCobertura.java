package Model;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public class SolverGolosoPorCobertura {
    private Grafo grafo;
    public SolverGolosoPorCobertura(Grafo grafo) {
        this.grafo = grafo;
    }
    public List<Integer> resolver() {
        List<Integer> solucion = new ArrayList<>();
        int total = grafo.tamanoDeGrafo();
        boolean[] dominados = new boolean[total];
        int cantidadDominados = 0;
        while (cantidadDominados < total) {
            int mejor = mejorVertice(dominados);
            solucion.add(mejor);
            cantidadDominados += dominar(mejor, dominados);
        }
        return solucion;
    }
    private int dominar(int vertice, boolean[] dominados) {
        int nuevos = 0;
        if (!dominados[vertice]) {
            dominados[vertice] = true;
            nuevos++;
        }
        for (int vecino : grafo.vecinos(vertice)) {
            if (!dominados[vecino]) {
                dominados[vecino] = true;
                nuevos++;
            }
        }
        return nuevos;
    }
    private int mejorVertice(boolean[] dominados) {
        Comparator<Integer> comparador = new ComparadorPorCobertura(grafo, dominados);
        int mejor = 0;
        for (int vertice = 1; vertice < grafo.tamanoDeGrafo(); vertice++) {
            if (comparador.compare(vertice, mejor) < 0) {
                mejor = vertice;
            }
        }
        return mejor;
    }
}