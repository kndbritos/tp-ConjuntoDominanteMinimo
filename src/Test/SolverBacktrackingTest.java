package Test;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import Model.Grafo;
import Model.SolverBacktracking;

public class SolverBacktrackingTest {

	private SolverBacktracking solver = new SolverBacktracking();
	
	@Test
	public void resolverGrafoNulo() {
		Set<Integer> solucion = solver.resolver(null);
		
		assertNotNull(solucion);
		assertTrue(solucion.isEmpty());
	}

	@Test
	public void resolverGrafoVacio() {
		Grafo grafo = new Grafo(0);
		
		Set<Integer> solucion = solver.resolver(grafo);
		
		assertTrue(solucion.isEmpty());
	}
	
	@Test
	public void resolverUnVerticeAislado() {
		Grafo grafo = new Grafo(1);
		
		Set<Integer> solucion = solver.resolver(grafo);
		
		assertEquals(1, solucion.size());
	}
	
	@Test
	public void resolverDosVerticesAislados() {
		Grafo grafo = new Grafo(2);
		
		Set<Integer> solucion = solver.resolver(grafo);
		
		assertEquals(2, solucion.size());
	}
	
	@Test
	public void resolverDosVerticesConectados() {
		Grafo grafo = new Grafo(2);
		
		grafo.agregarArista(0, 1);
		
		Set<Integer> solucion = solver.resolver(grafo);
		
		assertEquals(1, solucion.size());
	}
	
	@Test
	public void resolverTresVertices() {
		Grafo grafo = new Grafo(3);
		
		grafo.agregarArista(0, 1);
		grafo.agregarArista(1, 2);
		
		Set<Integer> solucion = solver.resolver(grafo);
		
		assertEquals(Set.of(1), solucion);
	}
	
	@Test
	public void resolverCuatroVertices() {
		Grafo grafo = new Grafo(4);
		
		grafo.agregarArista(0, 1);
		grafo.agregarArista(1, 2);
		grafo.agregarArista(2, 3);
		
		Set<Integer> solucion = solver.resolver(grafo);
		
		assertEquals(2, solucion.size());
	}
	
	@Test
	public void resolverGrafoCompleto() {
		Grafo grafo = new Grafo(4);
		
		grafo.agregarArista(0, 1);
		grafo.agregarArista(0, 2);
		grafo.agregarArista(0, 3);
		grafo.agregarArista(1, 2);
		grafo.agregarArista(1, 3);
		grafo.agregarArista(2, 3);
		
		Set<Integer> solucion = solver.resolver(grafo);
		
		assertEquals(1, solucion.size());
	}
}
