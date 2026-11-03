package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Model.Grafo;

public class GrafoTest {

	@Test(expected = IllegalArgumentException.class)
	public void constructorConNumeroNegativoLanzaExcepcion() {
	    Grafo grafo = new Grafo(-1);
	}
	@Test
	public void constructorConCeroVertices() {
	    assertEquals(0, new Grafo(0).tamanoDeGrafo());
	}
	@Test
	public void constructorBase() {
	    new Grafo(2);
	}
	@Test
	public void agregarVertice() {
		Grafo grafo= new Grafo(2);
		grafo.agregarVertice();
	}
	@Test
	public void agregarVertices() {
		Grafo grafo= new Grafo(2);
		grafo.agregarVertices(2);
	}
	@Test
	public void agregarVerticesAumentaTamano() {
	    Grafo grafo = new Grafo(2);
	    grafo.agregarVertices(2);
	    assertEquals(4, grafo.tamanoDeGrafo());
	}
	@Test
	public void agregarCeroVertices() {
	    Grafo grafo = new Grafo(2);
	    grafo.agregarVertices(0);
	    assertEquals(2, grafo.tamanoDeGrafo());
	}
	@Test
	public void conectarUnVerticeNuevo() {
	    Grafo grafo = new Grafo(1);
	    int nuevo = grafo.agregarVertice();
	    grafo.agregarArista(0, nuevo);
	    assertTrue(grafo.existeArista(0, nuevo));
	}
	@Test
	public void agregarArista() {
		Grafo grafo =new Grafo(2);
		grafo.agregarArista(0, 1);
		assertTrue(grafo.existeArista(0, 1));
	}
	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaNegativo() {
		Grafo grafo =new Grafo(2);
		grafo.agregarArista(0, -1);
	}
	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaIgual() {
		Grafo grafo =new Grafo(2);
		grafo.agregarArista(0, 0);
	}
	@Test
	public void aristaEsSimetrica() {
	    Grafo grafo = new Grafo(3);
	    grafo.agregarArista(0, 2);
	    assertTrue(grafo.existeArista(2, 0));
	}

	@Test
	public void agregarAristaDosVecesNoDuplica() {
	    Grafo grafo = new Grafo(2);
	    grafo.agregarArista(0, 1);
	    grafo.agregarArista(0, 1);
	    assertEquals(1, grafo.vecinos(0).size());
	}
	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaVerticeFueraDeRango() {
	    new Grafo(2).agregarArista(0, 2);
	}
	@Test 
	public void eliminarArista() {
		Grafo grafo =new Grafo(2);
		grafo.agregarArista(0, 1);
		grafo.eliminarArista(0, 1);
		assertFalse(grafo.existeArista(0, 1));
	}
	@Test(expected = IllegalArgumentException.class)
	public void eliminarAristaVerticeInvalido() {
	    new Grafo(2).eliminarArista(0, 9);
	}
	@Test
	public void eliminarAristaEsSimetrica() {
	    Grafo grafo = new Grafo(2);
	    grafo.agregarArista(0, 1);
	    grafo.eliminarArista(1, 0);
	    assertFalse(grafo.existeArista(0, 1));
	}
	@Test(expected = IllegalArgumentException.class)
	public void eliminarAristaMismoVertice() {
	    new Grafo(2).eliminarArista(1, 1);
	}
	@Test
	public void tamañoGrafo() {
		Grafo grafo =new Grafo(2);
		assertEquals(grafo.tamanoDeGrafo(),2);
	}
	@Test 
	public void vecinos() {
		Grafo grafo =new Grafo(2);
		grafo.agregarArista(0, 1);
		assertEquals(grafo.vecinos(0).size(),1);
	}
	@Test(expected = IllegalArgumentException.class)
	public void vecinosVerticeInvalido() {
	    new Grafo(2).vecinos(-1);
	}
}
