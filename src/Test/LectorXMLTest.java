package Test;

import static org.junit.Assert.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.junit.Test;

import Model.Grafo;
import Model.LectorXML;

public class LectorXMLTest {

    @Test
    public void cargarGrafoCorrectamente() throws Exception {
        String xml =
            "<grafo cantidad_vertices=\"3\">" +
            "<arista origen=\"0\" destino=\"1\"/>" +
            "</grafo>";

        File archivo = File.createTempFile("grafo", ".xml");
        archivo.deleteOnExit();

        Files.write(archivo.toPath(),
                    xml.getBytes(StandardCharsets.UTF_8));

        LectorXML lector = new LectorXML();
        Grafo grafo = lector.cargar(archivo);

        assertEquals(3, grafo.tamanoDeGrafo());
        assertTrue(grafo.existeArista(0, 1));
        assertFalse(grafo.existeArista(0, 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void rechazarRaizIncorrecta() throws Exception {
        String xml = "<otro cantidad_vertices=\"3\"/>";

        File archivo = File.createTempFile("grafo", ".xml");
        archivo.deleteOnExit();

        Files.write(archivo.toPath(),
                    xml.getBytes(StandardCharsets.UTF_8));

        new LectorXML().cargar(archivo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rechazarArchivoInexistente() {
        File archivo = new File(
            "archivo_que_no_existe_12345.xml"
        );

        new LectorXML().cargar(archivo);
    }
}