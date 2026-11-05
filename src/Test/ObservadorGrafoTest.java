package Test;

import static org.junit.Assert.*;
import org.junit.Test;

import Model.Grafo;
import Model.ObservadorGrafo;

public class ObservadorGrafoTest {

    @Test
    public void agregarVerticeNotifica() {
        Grafo grafo = new Grafo(2);
        final int[] contador = {0};

        ObservadorGrafo observador = () -> contador[0]++;

        grafo.agregarObservador(observador);
        grafo.agregarVertice();

        assertEquals(1, contador[0]);
    }

    @Test
    public void agregarAristaNotifica() {
        Grafo grafo = new Grafo(3);
        final int[] contador = {0};

        grafo.agregarObservador(() -> contador[0]++);

        grafo.agregarArista(0, 1);

        assertEquals(1, contador[0]);
    }

    @Test
    public void aristaDuplicadaNoNotificaDosVeces() {
        Grafo grafo = new Grafo(3);
        final int[] contador = {0};

        grafo.agregarObservador(() -> contador[0]++);

        grafo.agregarArista(0, 1);
        grafo.agregarArista(0, 1);

        assertEquals(1, contador[0]);
    }

    @Test
    public void eliminarObservadorDejaDeNotificar() {
        Grafo grafo = new Grafo(2);
        final int[] contador = {0};

        ObservadorGrafo observador = () -> contador[0]++;

        grafo.agregarObservador(observador);
        grafo.agregarVertice();

        grafo.eliminarObservador(observador);
        grafo.agregarVertice();

        assertEquals(1, contador[0]);
    }
}