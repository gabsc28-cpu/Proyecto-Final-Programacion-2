package Presentacion;

import Utilidades.TemaVisual;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

/**
 * Isotipo corporativo dibujado con Java2D para el hero del login.
 */
public class LogoPanel extends JPanel {

    /**
     * Crea el isotipo visual del panel de acceso.
     */
    public LogoPanel() {
        initComponents();
        initStyles();
        initEvents();
    }

    /**
     * Inicializa propiedades base del componente.
     */
    private void initComponents() {
        setOpaque(false);
        setPreferredSize(new Dimension(92, 92));
        setMinimumSize(new Dimension(92, 92));
    }

    /**
     * Aplica el estilo base del isotipo.
     */
    private void initStyles() {
    }

    /**
     * Registra eventos del componente.
     */
    private void initEvents() {
    }

    /**
     * Dibuja el isotipo con capas geometricas premium.
     *
     * @param graphics contexto grafico
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight()) - 8;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        g2.setPaint(new GradientPaint(x, y, new Color(255, 255, 255, 75), x + size, y + size, new Color(255, 255, 255, 12)));
        g2.fill(new Ellipse2D.Double(x - 2, y - 2, size + 4, size + 4));

        g2.setPaint(new GradientPaint(x, y, TemaVisual.AZUL_ACENTO, x + size, y + size, Color.WHITE));
        g2.fill(new RoundRectangle2D.Double(x, y, size, size, 28, 28));

        g2.setColor(new Color(8, 19, 45, 42));
        g2.setStroke(new BasicStroke(2.2f));
        g2.draw(new RoundRectangle2D.Double(x, y, size, size, 28, 28));

        int innerX = x + 16;
        int innerY = y + 16;
        int innerW = size - 32;
        int innerH = size - 32;

        g2.setColor(new Color(15, 38, 74, 220));
        g2.fill(new RoundRectangle2D.Double(innerX, innerY, innerW * 0.52, innerH, 18, 18));

        g2.setColor(new Color(255, 255, 255, 220));
        g2.fill(new RoundRectangle2D.Double(innerX + innerW * 0.36, innerY + 10, innerW * 0.46, innerH - 20, 16, 16));

        g2.setColor(new Color(255, 255, 255, 210));
        g2.fillOval(innerX + 8, innerY + 12, 10, 10);
        g2.fillOval(innerX + 8, innerY + 28, 10, 10);
        g2.fillOval(innerX + 8, innerY + 44, 10, 10);

        g2.setColor(TemaVisual.AZUL_SECUNDARIO);
        g2.fillRoundRect(innerX + (int) (innerW * 0.44), innerY + 17, 10, innerH - 34, 8, 8);

        g2.setColor(new Color(255, 255, 255, 165));
        g2.fill(new Ellipse2D.Double(x + 12, y + 10, 18, 18));

        g2.dispose();
    }
}
