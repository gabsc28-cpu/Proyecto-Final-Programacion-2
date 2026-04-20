package Presentacion;

import Utilidades.TemaVisual;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Item visual reutilizable para beneficios del login.
 */
public class ItemBeneficioLogin extends JPanel {

    /**
     * Tipos de iconografia soportados por el item.
     */
    public enum TipoIcono {
        ESCUDO,
        GRAFICA,
        REPORTE
    }

    private final String titulo;
    private final String descripcion;
    private final TipoIcono tipoIcono;

    /**
     * Crea un item premium de beneficio.
     *
     * @param titulo titulo principal
     * @param descripcion microcopy secundario
     * @param tipoIcono icono visual a utilizar
     */
    public ItemBeneficioLogin(String titulo, String descripcion, TipoIcono tipoIcono) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipoIcono = tipoIcono;
        initComponents();
        initStyles();
        initEvents();
    }

    /**
     * Construye el contenido del item.
     */
    private void initComponents() {
        setOpaque(false);
        setLayout(new BorderLayout(14, 0));
        setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));

        add(new IconoBeneficioPanel(tipoIcono), BorderLayout.WEST);

        JPanel textos = new JPanel(new GridLayout(0, 1, 0, 4));
        textos.setOpaque(false);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(TemaVisual.FUENTE_DESTACADA.deriveFont(15f));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblDescripcion = new JLabel("<html>" + descripcion + "</html>");
        lblDescripcion.setFont(TemaVisual.FUENTE_NORMAL);
        lblDescripcion.setForeground(new Color(219, 234, 254));

        textos.add(lblTitulo);
        textos.add(lblDescripcion);
        add(textos, BorderLayout.CENTER);
    }

    /**
     * Aplica estilos base.
     */
    private void initStyles() {
        setPreferredSize(new Dimension(320, 82));
    }

    /**
     * Registra eventos del componente.
     */
    private void initEvents() {
    }

    /**
     * Dibuja el contenedor glass del item.
     *
     * @param graphics contexto grafico
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(255, 255, 255, 20));
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 24, 24));

        g2.setColor(new Color(255, 255, 255, 32));
        g2.draw(new RoundRectangle2D.Double(0.7, 0.7, getWidth() - 1.4, getHeight() - 1.4, 24, 24));
        g2.dispose();

        super.paintComponent(graphics);
    }

    /**
     * Panel interno que dibuja el icono del beneficio.
     */
    private static class IconoBeneficioPanel extends JPanel {

        private final TipoIcono tipoIcono;

        /**
         * Crea el icono interno del beneficio.
         *
         * @param tipoIcono icono visual a dibujar
         */
        IconoBeneficioPanel(TipoIcono tipoIcono) {
            this.tipoIcono = tipoIcono;
            setOpaque(false);
            setPreferredSize(new Dimension(46, 46));
        }

        /**
         * Dibuja el icono del item.
         *
         * @param graphics contexto grafico
         */
        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g2 = (Graphics2D) graphics.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(255, 255, 255, 26));
            g2.fillOval(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(255, 255, 255, 42));
            g2.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(Color.WHITE);

            switch (tipoIcono) {
                case ESCUDO -> dibujarEscudo(g2);
                case GRAFICA -> dibujarGrafica(g2);
                case REPORTE -> dibujarReporte(g2);
                default -> {
                }
            }

            g2.dispose();
        }

        /**
         * Dibuja un escudo corporativo.
         *
         * @param g2 contexto grafico
         */
        private void dibujarEscudo(Graphics2D g2) {
            Path2D path = new Path2D.Double();
            path.moveTo(23, 10);
            path.lineTo(33, 14);
            path.lineTo(31, 27);
            path.curveTo(30, 33, 26, 36, 23, 38);
            path.curveTo(20, 36, 16, 33, 15, 27);
            path.lineTo(13, 14);
            path.closePath();
            g2.draw(path);
            g2.drawLine(19, 22, 22, 25);
            g2.drawLine(22, 25, 28, 18);
        }

        /**
         * Dibuja un icono de grafica ascendente.
         *
         * @param g2 contexto grafico
         */
        private void dibujarGrafica(Graphics2D g2) {
            g2.drawLine(12, 31, 12, 14);
            g2.drawLine(12, 31, 34, 31);
            g2.drawLine(16, 27, 21, 22);
            g2.drawLine(21, 22, 26, 24);
            g2.drawLine(26, 24, 33, 16);
            g2.drawLine(28, 16, 33, 16);
            g2.drawLine(33, 16, 33, 21);
        }

        /**
         * Dibuja un icono de reporte/documento.
         *
         * @param g2 contexto grafico
         */
        private void dibujarReporte(Graphics2D g2) {
            g2.drawRoundRect(13, 10, 20, 26, 7, 7);
            g2.drawLine(18, 18, 28, 18);
            g2.drawLine(18, 23, 28, 23);
            g2.drawLine(18, 28, 24, 28);
            g2.drawLine(28, 10, 33, 15);
        }
    }
}
