package Presentacion;

import Utilidades.TemaVisual;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * Panel reusable con bordes redondeados y sombra suave.
 */
public class PanelTarjetaPersonalizado extends JPanel {

    private final Color colorFondo;

    /**
     * Crea una tarjeta con fondo blanco.
     */
    public PanelTarjetaPersonalizado() {
        this(TemaVisual.BLANCO_TARJETA);
    }

    /**
     * Crea una tarjeta con el color indicado.
     *
     * @param colorFondo color de fondo deseado
     */
    public PanelTarjetaPersonalizado(Color colorFondo) {
        this.colorFondo = colorFondo;
        initComponents();
        initStyles();
        initEvents();
    }

    /**
     * Inicializa propiedades base del componente.
     */
    private void initComponents() {
        setOpaque(false);
    }

    /**
     * Aplica el estilo base del componente.
     */
    private void initStyles() {
        setBackground(colorFondo);
    }

    /**
     * Registra eventos del componente.
     */
    private void initEvents() {
    }

    /**
     * Dibuja el panel con sombra y bordes redondeados.
     *
     * @param graphics contexto grafico
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(15, 23, 42, 20));
        g2.fillRoundRect(6, 8, getWidth() - 12, getHeight() - 12, 24, 24);

        g2.setColor(colorFondo);
        g2.fillRoundRect(0, 0, getWidth() - 12, getHeight() - 12, 24, 24);
        g2.dispose();

        super.paintComponent(graphics);
    }
}
