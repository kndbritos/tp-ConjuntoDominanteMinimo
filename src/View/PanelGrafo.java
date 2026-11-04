
package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PanelGrafo extends JPanel {

    private static final long serialVersionUID = 1L;
    private int cantidadVertices = 0;
    private List<Set<Integer>> adyacencias = new ArrayList<>();
    private Set<Integer> verticesSeleccionados = new HashSet<>();

    public void actualizarGrafo(List<Set<Integer>> nuevasAdyacencias) {
        adyacencias = new ArrayList<>();

        for (Set<Integer> vecinos : nuevasAdyacencias) {
            adyacencias.add(Set.copyOf(vecinos));
        }

        cantidadVertices = adyacencias.size();
        verticesSeleccionados.clear();
        repaint();
    }

    public PanelGrafo() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(500, 350));
    }


@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g.create();

    try {
        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        if (cantidadVertices == 0) {
            return;
        }

        Point[] posiciones = new Point[cantidadVertices];

        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;

        int radio = Math.max(0,
            Math.min(getWidth(), getHeight()) / 2 - 40
        );

        // Distribuir los vértices en círculo
        for (int i = 0; i < cantidadVertices; i++) {
            double angulo =
                2 * Math.PI * i / cantidadVertices - Math.PI / 2;

            int x = centroX + (int) (radio * Math.cos(angulo));
            int y = centroY + (int) (radio * Math.sin(angulo));

            posiciones[i] = new Point(x, y);
        }

        // Dibujar aristas sin repetirlas
        for (int i = 0; i < cantidadVertices; i++) {
            for (int vecino : adyacencias.get(i)) {
                if (i < vecino) {
                    dibujarArista(
                        g2, posiciones[i], posiciones[vecino]
                    );
                }
            }
        }

        // Dibujar vértices
        for (int i = 0; i < cantidadVertices; i++) {
            dibujarVertice(g2, posiciones[i], i);
        }

    } finally {
        g2.dispose();
    }
  }

	public void mostrarSolucion(Set<Integer> solucion) {
	    verticesSeleccionados = new HashSet<>(solucion);
	    repaint();
	}

    private void dibujarArista(Graphics2D g2, Point a, Point b) {
        g2.setColor(Color.GRAY);
        g2.drawLine(a.x, a.y, b.x, b.y);
    }

    private void dibujarVertice(Graphics2D g2, Point posicion, int numero) {
        int radio = 20;

        if (verticesSeleccionados.contains(numero)) {
            g2.setColor(new Color(100, 200, 130)); // Verde
        } else {
            g2.setColor(new Color(210, 230, 255)); // Celeste
        }
        g2.fillOval(
            posicion.x - radio,
            posicion.y - radio,
            radio * 2,
            radio * 2
        );

        g2.setColor(Color.BLACK);
        g2.drawOval(
            posicion.x - radio,
            posicion.y - radio,
            radio * 2,
            radio * 2
        );

        String texto = String.valueOf(numero);
        FontMetrics fm = g2.getFontMetrics();

        int x = posicion.x - fm.stringWidth(texto) / 2;
        int y = posicion.y + (fm.getAscent() - fm.getDescent()) / 2;

        g2.drawString(texto, x, y);
    }
}
