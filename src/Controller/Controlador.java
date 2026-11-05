package Controller;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Model.Grafo;
import Model.LectorXML;
import Model.ObservadorGrafo;
import Model.SolverBacktracking;
import Model.SolverGolosoPorCobertura;
import Model.GeneradorRandom;
import Model.ObservadorStress;
import Model.PruebaDeStress;

public class Controlador {
    private Grafo grafo;
    private List<ObservadorGrafo> observadores = new ArrayList<>();
    private LectorXML lector = new LectorXML();
    public Controlador() {
        grafo = new Grafo(0);
    }
    public void crearGrafo(int cantidadVertices) {
        reemplazarGrafo(new Grafo(cantidadVertices));
    }
    public int agregarVertice() {
        return grafo.agregarVertice();
    }
    public void agregarArista(int verticeA, int verticeB) {
        grafo.agregarArista(verticeA, verticeB);
    }
    public void eliminarArista(int verticeA, int verticeB) {
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
        Grafo nuevoGrafo = lector.cargar(archivo);
        reemplazarGrafo(nuevoGrafo);
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
    public void agregarObservador(ObservadorGrafo observador) {
        if (observador == null) {
            throw new IllegalArgumentException("El observador no puede ser nulo");
        }

        if (!observadores.contains(observador)) {
            observadores.add(observador);
            grafo.agregarObservador(observador);
        }
    }
    private void reemplazarGrafo(Grafo nuevoGrafo) {
        grafo = nuevoGrafo;

        for (ObservadorGrafo observador : observadores) {
            grafo.agregarObservador(observador);
            observador.grafoActualizado();
        }
    }
    public Set<Integer> vecinosDe(int vertice) {
        return grafo.vecinos(vertice);
    }

    public void ejecutarStress(ObservadorStress observador) {
	    if (observador == null) {
	        throw new IllegalArgumentException(
	            "El observador de stress no puede ser nulo"
	        );
	    }
	
	    PruebaDeStress prueba = new PruebaDeStress(new GeneradorRandom(), observador);
	
	    prueba.ejecutar();
    }

}
