package Test;

import static org.junit.Assert.*;
import org.junit.Test;

import Model.ObservadorStress;

public class ObservadorStressTest {

    @Test
    public void observadorRecibeCantidadDeVertices() {
        final int[] verticesRecibidos = {0};

        ObservadorStress observador = (vertices, segundos) -> {
            verticesRecibidos[0] = vertices;
        };

        observador.instanciaResuelta(5, 0.25);

        assertEquals(5, verticesRecibidos[0]);
    }

    @Test
    public void observadorRecibeTiempoDeEjecucion() {
        final double[] tiempoRecibido = {0};

        ObservadorStress observador = (vertices, segundos) -> {
            tiempoRecibido[0] = segundos;
        };

        observador.instanciaResuelta(5, 0.25);

        assertEquals(0.25, tiempoRecibido[0], 0.001);
    }

    @Test
    public void observadorRecibeMultiplesResultados() {
        final int[] notificaciones = {0};

        ObservadorStress observador = (vertices, segundos) -> {
            notificaciones[0]++;
        };

        observador.instanciaResuelta(2, 0.01);
        observador.instanciaResuelta(3, 0.02);
        observador.instanciaResuelta(4, 0.03);

        assertEquals(3, notificaciones[0]);
    }
}