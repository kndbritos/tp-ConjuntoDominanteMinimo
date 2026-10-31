package Test;

import static org.junit.Assert.*;
import org.junit.Test;
import Model.Grafo;
import Model.SolverGolosoPorCobertura;

public class SolverGolosoPorCoberturaTest {
    @Test
    public void testGrafoConUnSoloVertice() {
        Grafo grafo = new Grafo(1);
        
        SolverGolosoPorCobertura solver = new SolverGolosoPorCobertura(grafo);

        assertEquals(1, solver.resolver().size());
    }

    @Test
    public void testGrafoLineaSimple() {
    	Grafo grafo = new Grafo(3);
        grafo.agregarArista(0, 1);
        grafo.agregarArista(1, 2);
        
        SolverGolosoPorCobertura solver = new SolverGolosoPorCobertura(grafo);
        assertEquals(1, solver.resolver().size());
    }

    @Test
    public void testGrafoEstrella() {
    	Grafo grafo = new Grafo(5);
    	for (int i = 1; i < 5; i++) {
			grafo.agregarArista(0, i);
		}
    	SolverGolosoPorCobertura solver = new SolverGolosoPorCobertura(grafo);
    	assertEquals(1,solver.resolver().size());    
    }
    @Test 
    public void testGrafoDemostracion() {
    	Grafo grafo = new Grafo(6);
    	grafo.agregarArista(0, 4);
    	grafo.agregarArista(0, 1);
    	grafo.agregarArista(1, 2);
    	grafo.agregarArista(4, 3);
    	grafo.agregarArista(4, 5);
    	SolverGolosoPorCobertura solver = new SolverGolosoPorCobertura(grafo);
    	assertEquals(2,solver.resolver().size());
    }
}