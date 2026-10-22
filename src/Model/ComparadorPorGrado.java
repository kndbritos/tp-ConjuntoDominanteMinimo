package Model;

import java.util.Comparator;

public class ComparadorPorGrado implements Comparator<Integer> {
    private Grafo grafo;

    public ComparadorPorGrado(Grafo grafo) {
        this.grafo = grafo;
    }

    @Override
    public int compare(Integer uno, Integer otro) {
        return Integer.compare(grafo.gradoDeVertice(otro), grafo.gradoDeVertice(uno));
    }
}