package Model;

import java.util.Comparator;

public class ComparadorPorCobertura implements Comparator<Integer> {
    private Grafo grafo;
    private boolean[] dominados;

    public ComparadorPorCobertura(Grafo grafo, boolean[] dominados) {
        this.grafo = grafo;
        this.dominados = dominados;
    }

    @Override
    public int compare(Integer verticeA, Integer verticeB) {
    	return Integer.compare(cobertura(verticeB), cobertura(verticeA));
    }

    private int cobertura(int vertice) {
        int cobertura = 0;
        if (!dominados[vertice]) {
            cobertura++;
        }
        for (int vecino : grafo.vecinos(vertice)) {
            if (!dominados[vecino]) {
                cobertura++;
            }
        }
        return cobertura;
    }
}