package Model;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class LectorXML {
	private String nombreRaiz = "grafo";
	private String verticesRaiz = "cantidad_vertices";
	private String etiquetaArista = "arista";
	private String atributoOrigen = "origen";
	private String atributoDestino = "destino";
	
    public Grafo cargar(File archivo){
    	try {
    		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    		Document documento = builder.parse(archivo);
    		documento.getDocumentElement().normalize();
    		
    		Element raiz = documento.getDocumentElement();
    		verificarRaiz(raiz);
    		
    		Grafo grafo = recorrer(documento);
    		
            return grafo;
            
		} catch (Exception e) {
			throw new IllegalArgumentException("No se pudo cargar el archivo");
		}
        

    }
    
    private Grafo recorrer(Document documento) {
    	Element raiz = documento.getDocumentElement();
    	int vertices = Integer.parseInt(raiz.getAttribute(verticesRaiz));
    	
    	Grafo grafo = new Grafo(vertices);
    	
    	NodeList aristas = documento.getElementsByTagName(etiquetaArista);
    	
    	for(int i = 0; i < aristas.getLength(); i++) {
    		Element arista = (Element) aristas.item(i);
    		int origen = Integer.parseInt(arista.getAttribute(atributoOrigen));
    		int destino = Integer.parseInt(arista.getAttribute(atributoDestino));
    		grafo.agregarArista(origen, destino);
    	}
    	
    	return grafo;
    }
    
    private void verificarRaiz(Element raiz) {
    	if(!raiz.getTagName().equals(nombreRaiz)) {
    		throw new IllegalArgumentException("El elemento raíz debe ser <grafo>");
    	}
    }
}
