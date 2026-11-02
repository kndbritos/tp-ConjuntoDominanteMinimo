package Controller;

import java.io.File;
import java.util.List;
import java.util.Set;

import Model.Grafo;
import Model.LectorXML;
import Model.SolverBacktracking;
import Model.SolverGolosoPorCobertura;

public class Controlador {
    private Grafo grafo;
    private LectorXML lector = new LectorXML();
    public Controlador() {
        grafo = new Grafo(0);
    }
    public void crearGrafo(int cantidadVertices) {
        grafo = new Grafo(cantidadVertices);
    }
    public int agregarVertice() {
        return grafo.agregarVertice();
    }
    public void agregarArista(int verticeA, int verticeB) {
        validarVertice(verticeA);
        validarVertice(verticeB);
        grafo.agregarArista(verticeA, verticeB);
    }
    public void eliminarArista(int verticeA, int verticeB) {
        validarVertice(verticeA);
        validarVertice(verticeB);
        grafo.eliminarArista(verticeA, verticeB);
    }
    public boolean existeArista(int verticeA, int verticeB) {
        return grafo.existeArista(verticeA, verticeB);
    }
    public int cantidadVertices() {
        return grafo.tamanoDeGrafo();
    }
    public void cargarGrafo(File archivo) {
        validarArchivo(archivo);
        grafo = lector.cargar(archivo);
    }
    public List<Integer> resolverGoloso() {
        validarGrafoCargado();
        SolverGolosoPorCobertura solver =
                new SolverGolosoPorCobertura(grafo);
        return solver.resolver();
    }
    public Set<Integer> resolverBacktracking() {
        validarGrafoCargado();
        SolverBacktracking solver = new SolverBacktracking();
        return solver.resolver(grafo);
    }
    private void validarVertice(int vertice) {
        if (vertice < 0 || vertice >= grafo.tamanoDeGrafo()) {
            throw new IllegalArgumentException(
                    "El vertice " + vertice + " no existe");
        }
    }
    private void validarArchivo(File archivo) {
        if (archivo == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un archivo");
        }
    }
    private void validarGrafoCargado() {
        if (grafo.tamanoDeGrafo() == 0) {
            throw new IllegalArgumentException(
                    "Primero debe cargar un grafo");
        }
    }
}
