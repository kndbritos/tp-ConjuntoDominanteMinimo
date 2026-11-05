package Test;

import static org.junit.Assert.*;
import org.junit.Test;

import Model.Grafo;
import Model.Generador;
import Model.GeneradorDeGrafos;

public class GeneradorDeGrafosTest {

    @Test
    public void generaCantidadCorrectaDeVertices() {
        Generador generador = () -> false;

        Grafo grafo =
            GeneradorDeGrafos.aleatorio(5, generador);

        assertEquals(5, grafo.tamanoDeGrafo());
    }

    @Test
    public void generadorSiempreFalsoNoCreaAristas() {
        Generador generador = () -> false;

        Grafo grafo =
            GeneradorDeGrafos.aleatorio(4, generador);

        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                assertFalse(grafo.existeArista(i, j));
            }
        }
    }

    @Test
    public void generadorSiempreVerdaderoCreaGrafoCompleto() {
        Generador generador = () -> true;

        Grafo grafo =
            GeneradorDeGrafos.aleatorio(4, generador);

        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                assertTrue(grafo.existeArista(i, j));
            }
        }
    }
}