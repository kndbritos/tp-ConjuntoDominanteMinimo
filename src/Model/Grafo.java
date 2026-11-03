package Model;

import java.util.ArrayList;
import java.util.HashSet;


public class Grafo {
	private ArrayList<HashSet<Integer>> vecinos;
	
	private ArrayList<ObservadorGrafo> observadores = new ArrayList<>();
	
	public Grafo(int n){
		if (n < 0) {
	        throw new IllegalArgumentException("La cantidad no puede ser negativa");
		}
		vecinos = new ArrayList<HashSet<Integer>>();
		for(int i=0; i<n; ++i) {
			vecinos.add(new HashSet<Integer>());
		}
	}
	public void agregarVertices(int cantidad) {
	    for (int i = 0; i < cantidad; ++i)
	    	agregarVertice();
	}
	public int agregarVertice() {
	    vecinos.add(new HashSet<Integer>());
	    
	    notificarObservadores();
	    
	    return vecinos.size() - 1;
	}
	public void agregarArista(int verticeA, int verticeB){
		verificarVertice(verticeA);
		verificarVertice(verticeB);
		verificarDistintos(verticeA, verticeB);
		
		boolean agregada = vecinos.get(verticeA).add(verticeB);
		vecinos.get(verticeB).add(verticeA);
		
		if(agregada) {
			notificarObservadores();
		}
	}
	public void eliminarArista(int verticeA, int verticeB) {
	    verificarVertice(verticeA);
	    verificarVertice(verticeB);
	    verificarDistintos(verticeA, verticeB);

	    boolean eliminada = vecinos.get(verticeA).remove(verticeB);
	    vecinos.get(verticeB).remove(verticeA);

	    if (eliminada) {
	        notificarObservadores();
	    }
	}
	public boolean existeArista(int verticeA, int verticeB){
		verificarVertice(verticeA);
		verificarVertice(verticeB);
		verificarDistintos(verticeA, verticeB);
		return vecinos.get(verticeA).contains(verticeB);
	}
	public int tamanoDeGrafo() {
	    return vecinos.size();
	}
	private void verificarVertice(int vertice) {
	    if (vertice < 0 || vertice >= vecinos.size()) {
	        throw new IllegalArgumentException("El vértice " + vertice + " no existe");
	    }
	}
	private void verificarDistintos(int verticeA, int verticeB) {
	    if (verticeA == verticeB) {
	        throw new IllegalArgumentException("No se permiten repetidos");
	    }
	}
	public HashSet<Integer> vecinos(int vertice) {
	    verificarVertice(vertice);
	    return new HashSet<>(vecinos.get(vertice));
	}
	
	public void agregarObservador(ObservadorGrafo observador) {
	    if (observador == null) {
	        throw new IllegalArgumentException("El observador no puede ser nulo");
	    }

	    if (!observadores.contains(observador)) {
	        observadores.add(observador);
	    }
	}

	public void eliminarObservador(ObservadorGrafo observador) {
	    observadores.remove(observador);
	}

	private void notificarObservadores() {
	    for (ObservadorGrafo observador : observadores) {
	        observador.grafoActualizado();
	    }
	}
}
