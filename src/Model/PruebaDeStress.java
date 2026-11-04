package Model;

public class PruebaDeStress {

    private Generador generador;
    private ObservadorStress observador;

    public PruebaDeStress(Generador generador, ObservadorStress observador) {
        this.generador = generador;
        this.observador = observador;
    }
    public void ejecutar() {
        for (int n = 2; !Thread.currentThread().isInterrupted(); n++) {
            Grafo grafo = GeneradorDeGrafos.aleatorio(n, generador);
            long inicio = System.currentTimeMillis();
            new SolverBacktracking().resolver(grafo);
            long fin = System.currentTimeMillis();

            if (!Thread.currentThread().isInterrupted()) {
                observador.instanciaResuelta(n, (fin - inicio) / 1000.0);
            }
        }
    }
}