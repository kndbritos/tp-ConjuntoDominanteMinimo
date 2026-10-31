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
	public void agregarArista() {
		Grafo grafo =new Grafo(2);
		grafo.agregarArista(0, 1);
		assertTrue(grafo.existeArista(0, 1));
	}
	@Test 
	public void EliminarArista() {
		Grafo grafo =new Grafo(2);
		grafo.agregarArista(0, 1);
		grafo.eliminarArista(0, 1);
		assertFalse(grafo.existeArista(0, 1));
	}
	@Test
	public void TamañoGrafo() {
		Grafo grafo =new Grafo(2);
		assertEquals(grafo.tamanoDeGrafo(),2);
	}
	@Test
	public void GradoVertice() {
		Grafo grafo =new Grafo(2);
		grafo.agregarArista(0, 1);
		assertEquals(grafo.gradoDeVertice(0),1);
	}
	

}
