package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Controller.Controlador;
import Model.ObservadorGrafo;

public class VentanaPrincipal extends JFrame implements ObservadorGrafo {

    private static final long serialVersionUID = 1L;

    private Controlador controlador;

    private JTextField campoVertices;
    private JTextField campoVerticeA;
    private JTextField campoVerticeB;

    private JButton botonCrearGrafo;
    private JButton botonAgregarVertice;
    private JButton botonAgregarArista;
    private JButton botonResolver;
    private JButton botonCargarGrafo;

    private JTextArea areaResultados;

    public VentanaPrincipal(Controlador controlador) {

        this.controlador = controlador;

        configurarVentana();
        crearComponentes();
        configurarEventos();

        controlador.agregarObservador(this);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            Controlador controlador = new Controlador();

            VentanaPrincipal ventana = new VentanaPrincipal(controlador);

            ventana.setVisible(true);
        });

    }
    
    private void configurarVentana() {

        setTitle("Conjunto Dominante Mínimo");

        setSize(750, 550);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
    }

    private void crearComponentes() {

        JPanel panelControles = new JPanel(new GridLayout(4, 2, 10, 10));

        campoVertices = new JTextField();
        campoVerticeA = new JTextField();
        campoVerticeB = new JTextField();

        botonCrearGrafo = new JButton("Crear grafo");
        botonAgregarVertice = new JButton("Agregar vértice");
        botonAgregarArista = new JButton("Agregar arista");
        botonResolver = new JButton("Resolver goloso");
        botonCargarGrafo = new JButton("Cargar grafo XML");
        
        panelControles.add(new JLabel("Cantidad de vértices:"));
        panelControles.add(campoVertices);

        panelControles.add(new JLabel("Vértice A:"));
        panelControles.add(campoVerticeA);

        panelControles.add(new JLabel("Vértice B:"));
        panelControles.add(campoVerticeB);

        panelControles.add(botonCrearGrafo);
        panelControles.add(botonAgregarVertice);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);

        add(panelControles, BorderLayout.NORTH);
        add(areaResultados, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();

        panelInferior.add(botonAgregarArista);
        panelInferior.add(botonCargarGrafo);
        panelInferior.add(botonResolver);

        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void configurarEventos() {

        botonCrearGrafo.addActionListener(e -> {
            try {
                int cantidad = Integer.parseInt(campoVertices.getText().trim());
                controlador.crearGrafo(cantidad);

            } catch (NumberFormatException ex) {
                mostrarError("Ingresá una cantidad válida de vértices.");

            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            }
        });

        botonAgregarVertice.addActionListener(e -> {
            controlador.agregarVertice();
        });

        botonAgregarArista.addActionListener(e -> {
            try {
                int verticeA = Integer.parseInt(campoVerticeA.getText().trim());
                int verticeB = Integer.parseInt(campoVerticeB.getText().trim());

                controlador.agregarArista(verticeA, verticeB);

            } catch (NumberFormatException ex) {
                mostrarError("Los vértices deben ser números enteros.");

            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            }
        });

        botonResolver.addActionListener(e -> {
            try {
                java.util.List<Integer> resultado = controlador.resolverGoloso();

                areaResultados.setText(
                    "Conjunto dominante obtenido: " + resultado
                    + "\nCantidad de vértices seleccionados: " + resultado.size()
                );

            } catch (IllegalArgumentException ex) {
                mostrarError(ex.getMessage());
            }
        });
        
        botonCargarGrafo.addActionListener(e -> {

            JFileChooser selector = new JFileChooser();

            selector.setDialogTitle("Seleccionar archivo del grafo");

            selector.setFileFilter(
                new FileNameExtensionFilter("Archivos XML", "xml")
            );

            int resultado = selector.showOpenDialog(this);

            if (resultado == JFileChooser.APPROVE_OPTION) {

                File archivo = selector.getSelectedFile();

                try {
                    controlador.cargarGrafo(archivo);

                } catch (Exception ex) {
                    mostrarError("No se pudo cargar el archivo: "
                            + ex.getMessage());
                }
            }
        });
    }
    
    private void mostrarError(String mensaje) {
    	javax.swing.JOptionPane.showMessageDialog(
    	        this,
    	        mensaje,
    	        "Error",
    	        javax.swing.JOptionPane.ERROR_MESSAGE
    	    );
	}
	@Override
    public void grafoActualizado() {

        areaResultados.setText(
            "El grafo fue actualizado.\n" +
            "Cantidad de vértices: " + controlador.cantidadVertices()
        );
    }
}