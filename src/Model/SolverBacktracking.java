package Model;

import java.util.HashSet;
import java.util.Set;

public class SolverBacktracking {
	private Grafo grafo;
	private Set<Integer> mejorSolucion;
	private int tamanoMinimo;
	private int primerVertice;
	
	public Set<Integer> resolver(Grafo grafo){
		this.grafo = grafo;
		this.mejorSolucion = null;
		this.tamanoMinimo = Integer.MAX_VALUE;
		this.primerVertice = 0;
		
		if(esGrafoVacio()) {
			return new HashSet<>();
		}
		
		Set<Integer> conjuntoActual = new HashSet<>();
		backtracking(primerVertice, conjuntoActual);
		
		return mejorSolucion;
	}
	
	private void backtracking(int verticeActual, Set<Integer> conjuntoActual) {
		if(alcanzoTamanoMinimo(conjuntoActual)) {
			return;
		}
		
		if(esDominante(conjuntoActual)) {
			tamanoMinimo = conjuntoActual.size();
			mejorSolucion = new HashSet<>(conjuntoActual);
			return;
		}
		
		if(todosLosVerticesEvaluados(verticeActual)) {
			return;
		}
		
		conjuntoActual.add(verticeActual);
		backtracking(verticeActual + 1, conjuntoActual);
		conjuntoActual.remove(verticeActual);
		
		backtracking(verticeActual + 1, conjuntoActual);
	}
	
	private boolean esGrafoVacio() {
		return grafo == null || grafo.tamanoDeGrafo() == 0;
	}
	
	private boolean esDominante(Set<Integer> conjunto) {
		Set<Integer> cubiertos = new HashSet<>(conjunto);
		
		for(int verticeActual : conjunto) {
			for(int vecino : grafo.vecinos(verticeActual)) {
				cubiertos.add(vecino);
			}
		}
		return cubiertos.size() == grafo.tamanoDeGrafo();
	}
	
	private boolean alcanzoTamanoMinimo(Set<Integer> conjuntoActual) {
		return conjuntoActual.size() >= tamanoMinimo;
	}
	
	private boolean todosLosVerticesEvaluados(int verticeActual) {
		return verticeActual == grafo.tamanoDeGrafo();
	}
}