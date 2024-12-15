package Control;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @Autor: Sebastian Murrieta Verduzco - 233463
 */

public class PanelRound extends JPanel {
    
    // Parámetros para los bordes redondeados en cada esquina
    private int roundTopLeft = 0;
    private int roundTopRight = 0;
    private int roundBottomLeft = 0;
    private int roundBottomRight = 0;
    
    // Parámetro para activar o desactivar el cursor de mano
    private boolean cursorHandEnabled = false;

    // Constructor del panel redondeado
    public PanelRound() {
        setOpaque(false);  // El panel no será opaco para que se vea el fondo
        
        // Añadimos un listener de ratón para detectar cuando entra o sale del componente
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Si el parámetro cursorHandEnabled está activado, cambia el cursor a la mano
                if (cursorHandEnabled) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Al salir del componente, si está activado, vuelve al cursor predeterminado
                if (cursorHandEnabled) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    // Métodos getter y setter para modificar el borde redondeado en las esquinas superiores e inferiores
    public int getRoundTopLeft() {
        return roundTopLeft;
    }

    public void setRoundTopLeft(int roundTopLeft) {
        this.roundTopLeft = roundTopLeft;  // Asigna el nuevo valor
        repaint();  // Redibuja el componente con el nuevo valor
    }

    public int getRoundTopRight() {
        return roundTopRight;
    }

    public void setRoundTopRight(int roundTopRight) {
        this.roundTopRight = roundTopRight;
        repaint();  // Redibuja el componente con el nuevo valor
    }

    public int getRoundBottomLeft() {
        return roundBottomLeft;
    }

    public void setRoundBottomLeft(int roundBottomLeft) {
        this.roundBottomLeft = roundBottomLeft;
        repaint();  // Redibuja el componente con el nuevo valor
    }

    public int getRoundBottomRight() {
        return roundBottomRight;
    }

    public void setRoundBottomRight(int roundBottomRight) {
        this.roundBottomRight = roundBottomRight;
        repaint();  // Redibuja el componente con el nuevo valor
    }

    // Método getter para saber si el cursor de mano está activado
    public boolean isCursorHandEnabled() {
        return cursorHandEnabled;
    }

    // Método setter para activar o desactivar el cursor de mano
    public void setCursorHandEnabled(boolean cursorHandEnabled) {
        this.cursorHandEnabled = cursorHandEnabled;
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        // Se crea una instancia de Graphics2D para usar técnicas avanzadas de dibujo
        Graphics2D g2 = (Graphics2D) grphcs.create();
        // Se habilita el antialiasing para suavizar los bordes del dibujo
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Establece el color de fondo
        g2.setColor(getBackground());

        // Se crea el área de la figura con las esquinas redondeadas, comenzando por la esquina superior izquierda
        Area area = new Area(createRoundTopLeft());
        // Si las demás esquinas están configuradas con un valor mayor a 0, se agregan al área
        if (roundTopRight > 0) {
            area.intersect(new Area(createRoundTopRight()));
        }
        if (roundBottomLeft > 0) {
            area.intersect(new Area(createRoundBottomLeft()));
        }
        if (roundBottomRight > 0) {
            area.intersect(new Area(createRoundBottomRight()));
        }
        // Se rellena el área del panel con el color de fondo
        g2.fill(area);
        
        // Libera los recursos gráficos
        g2.dispose();
        // Llama al método de la superclase para terminar de pintar el componente
        super.paintComponent(grphcs);
    }

    // Métodos para crear las formas de las esquinas redondeadas
    // Cada método devuelve un área con una esquina específica redondeada

    // Crea la forma para la esquina superior izquierda
    private Shape createRoundTopLeft() {
        int width = getWidth();  // Ancho del panel
        int height = getHeight();  // Alto del panel
        // Calcula el redondeo de la esquina superior izquierda basado en el tamaño del panel
        int roundX = Math.min(width, roundTopLeft);
        int roundY = Math.min(height, roundTopLeft);
        // Se crea un área para redondear la esquina
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        // Se agregan rectángulos para cubrir las otras áreas y evitar redondear todo el panel
        area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, roundY / 2, width, height - roundY / 2)));
        return area;
    }

    // Crea la forma para la esquina superior derecha
    private Shape createRoundTopRight() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, roundTopRight);
        int roundY = Math.min(height, roundTopRight);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, roundY / 2, width, height - roundY / 2)));
        return area;
    }

    // Crea la forma para la esquina inferior izquierda
    private Shape createRoundBottomLeft() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, roundBottomLeft);
        int roundY = Math.min(height, roundBottomLeft);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
        return area;
    }

    // Crea la forma para la esquina inferior derecha
    private Shape createRoundBottomRight() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, roundBottomRight);
        int roundY = Math.min(height, roundBottomRight);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
        return area;
    }
}
