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
import javax.swing.SwingWorker;
import javax.swing.JScrollPane;

import java.util.List;
import java.util.Set;

import Controller.Controlador;
import Model.ObservadorGrafo;
import Model.ObservadorStress;

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
    private JButton botonBacktracking;
    private JButton botonStress;
    private JButton botonDetenerStress;
    private SwingWorker<Void, String> trabajadorStress;
    private JTextArea areaResultados;
    
    private PanelGrafo panelGrafo;

    public VentanaPrincipal(Controlador controlador) {

        this.controlador = controlador;

        configurarVentana();
        crearComponentes();
        configurarEventos();

        controlador.agregarObservador(this);
        grafoActualizado();
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

        setSize(1000, 650);

        setMaximumSize(new java.awt.Dimension(1366, 768));
        
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
        botonBacktracking = new JButton ("Resolver Backtracking");
        botonStress = new JButton("Iniciar stress");
        botonDetenerStress = new JButton("Detener stress");
        botonDetenerStress.setEnabled(false);
        
        panelControles.add(new JLabel("Cantidad de vértices:"));
        panelControles.add(campoVertices);

        panelControles.add(new JLabel("Vértice A:"));
        panelControles.add(campoVerticeA);

        panelControles.add(new JLabel("Vértice B:"));
        panelControles.add(campoVerticeB);

        panelControles.add(botonCrearGrafo);
        panelControles.add(botonAgregarVertice);

        panelGrafo = new PanelGrafo();
        
        areaResultados = new JTextArea(4, 30);
        areaResultados.setEditable(false);
        areaResultados.setLineWrap(false);

        JScrollPane scrollResultados = new JScrollPane(areaResultados);
        scrollResultados.setPreferredSize(new java.awt.Dimension(0, 120));
        
        JPanel panelCentral = new JPanel(new BorderLayout());

        panelCentral.add(panelGrafo, BorderLayout.CENTER);
        panelCentral.add(scrollResultados, BorderLayout.SOUTH);

        add(panelControles, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 8));

        panelInferior.add(botonAgregarArista);
        panelInferior.add(botonCargarGrafo);
        panelInferior.add(botonResolver);
        panelInferior.add(botonBacktracking);
        panelInferior.add(botonStress);
        panelInferior.add(botonDetenerStress);

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
                panelGrafo.mostrarSolucion(
                	    new java.util.HashSet<>(resultado)
                	);
                
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

botonBacktracking.addActionListener(e -> {
    botonBacktracking.setEnabled(false);
    botonResolver.setEnabled(false);
    botonCrearGrafo.setEnabled(false);
    botonAgregarVertice.setEnabled(false);
    botonAgregarArista.setEnabled(false);
    botonCargarGrafo.setEnabled(false);
    botonStress.setEnabled(false);

    areaResultados.setText("Calculando Backtracking...\n");

    SwingWorker<Set<Integer>, Void> worker =
            new SwingWorker<Set<Integer>, Void>() {

        @Override
        protected Set<Integer> doInBackground() {
            return controlador.resolverBacktracking();
        }

        @Override
        protected void done() {
            try {
                Set<Integer> resultado = get();

                panelGrafo.mostrarSolucion(resultado);

                areaResultados.setText(
                    "Conjunto dominante mínimo: " + resultado
                    + "\nCantidad de vértices seleccionados: "
                    + resultado.size()
                );

            } catch (Exception ex) {
                mostrarError("Error al resolver Backtracking: "
                        + ex.getMessage());

            } finally {
                botonBacktracking.setEnabled(true);
                botonResolver.setEnabled(true);
                botonCrearGrafo.setEnabled(true);
                botonAgregarVertice.setEnabled(true);
                botonAgregarArista.setEnabled(true);
                botonCargarGrafo.setEnabled(true);
                botonStress.setEnabled(true);
            }
        }
    };

    worker.execute();
});


		botonStress.addActionListener(e -> {
		
		    botonStress.setEnabled(false);
		    botonDetenerStress.setEnabled(true);
		    areaResultados.setText("Iniciando prueba de stress...\n");
		
		    trabajadorStress = new SwingWorker<Void, String>() {
		
		        @Override
		        protected Void doInBackground() {
		
		            ObservadorStress observador = (vertices, segundos) -> {
		                if (!isCancelled()) {
		                    publish(
		                        "Vertices: " + vertices
		                        + " | Tiempo: " + segundos + " segundos"
		                    );
		                }
		            };
		
		            controlador.ejecutarStress(observador);
		            return null;
		        }
		
		        @Override
		        protected void process(List<String> resultados) {
		            for (String resultado : resultados) {
		                areaResultados.append(resultado + "\n");
		            }
		        }
		
		        @Override
		        protected void done() {
		            botonStress.setEnabled(true);
		            botonDetenerStress.setEnabled(false);
		
		            if (isCancelled()) {
		                areaResultados.append(
		                    "\nPrueba de stress detenida.\n"
		                );
		            } else {
		                try {
		                    get();
		                    areaResultados.append(
		                        "\nPrueba de stress finalizada.\n"
		                    );
		                } catch (Exception ex) {
		                    mostrarError(
		                        "Error en stress: " + ex.getMessage()
		                    );
		                }
		            }
		        }
		    };
		
		    trabajadorStress.execute();
		});
		
		botonDetenerStress.addActionListener(e -> {
		    if (trabajadorStress != null
		            && !trabajadorStress.isDone()) {
		        trabajadorStress.cancel(true);
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

        java.util.List<java.util.Set<Integer>> adyacencias =
                new java.util.ArrayList<>();

        for (int i = 0; i < controlador.cantidadVertices(); i++) {
            adyacencias.add(controlador.vecinosDe(i));
        }

        panelGrafo.actualizarGrafo(adyacencias);

        areaResultados.setText(
            "El grafo fue actualizado.\n" +
            "Cantidad de vértices: " + controlador.cantidadVertices()
        );
    }
}