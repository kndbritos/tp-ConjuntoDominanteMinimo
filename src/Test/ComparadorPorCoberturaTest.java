package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Model.ComparadorPorCobertura;
import Model.Grafo;

public class ComparadorPorCoberturaTest {

	@Test
	public void compararVerticeConMayorCobertura() {
		Grafo grafo = new Grafo(4);
		grafo.agregarArista(0, 1);
		grafo.agregarArista(0, 2);
		grafo.agregarArista(0, 3);
		
		boolean[] dominados = new boolean[4];
		
		ComparadorPorCobertura comparador = new ComparadorPorCobertura(grafo, dominados);
		
		assertTrue(comparador.compare(0, 1) < 0);
	}
	
	@Test
	public void compararVerticesConIgualCobertura() {
		Grafo grafo = new Grafo(3);
		grafo.agregarArista(0, 1);
		grafo.agregarArista(1, 2);
		
		boolean[] dominados = new boolean[3];
		
		ComparadorPorCobertura comparador = new ComparadorPorCobertura(grafo, dominados);
		
		assertEquals(0, comparador.compare(0, 2));
	}
	
	@Test
	public void compararVerticeConMenorCobertura() {
		Grafo grafo = new Grafo(4);
		grafo.agregarArista(0, 1);
		grafo.agregarArista(0, 2);
		grafo.agregarArista(0, 3);
		
		boolean[] dominados = new boolean[4];
		
		ComparadorPorCobertura comparador = new ComparadorPorCobertura(grafo, dominados);
		
		assertTrue(comparador.compare(1, 0) > 0);
	}

}
