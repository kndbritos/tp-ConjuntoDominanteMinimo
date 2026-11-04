package Model;

public class GeneradorDeGrafos {

    public static Grafo aleatorio(int vertices, Generador generador) {
        Grafo grafo = new Grafo(vertices);

        for (int i = 0; i < vertices; i++) {
            for (int j = i + 1; j < vertices; j++) {
                if (generador.nextBoolean()) {
                    grafo.agregarArista(i, j);
                }
            }
        }
        return grafo;
    }
}