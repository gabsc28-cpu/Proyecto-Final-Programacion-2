package Presentacion;

import Utilidades.TemaVisual;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Hero panel premium para la pantalla de login.
 */
public class PanelHeroLogin extends JPanel {

    /**
     * Crea el hero visual de la pantalla de acceso.
     */
    public PanelHeroLogin() {
        initComponents();
        initStyles();
        initEvents();
    }

    /**
     * Construye la composicion principal del hero.
     */
    private void initComponents() {
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        add(crearEncabezadoMarca(), BorderLayout.NORTH);
        add(crearContenidoCentral(), BorderLayout.CENTER);
        add(crearBloqueInferior(), BorderLayout.SOUTH);
    }

    /**
     * Aplica estilos base del hero.
     */
    private void initStyles() {
        setPreferredSize(new Dimension(430, 0));
        setMinimumSize(new Dimension(390, 540));
    }

    /**
     * Registra eventos del componente.
     */
    private void initEvents() {
    }

    /**
     * Dibuja el fondo premium con degradados y formas abstractas.
     *
     * @param graphics contexto grafico
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(9, 24, 53),
                getWidth(), getHeight(), new Color(20, 58, 120)
        );
        g2.setPaint(gradient);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 12, getHeight() - 12, 30, 30));

        g2.setColor(new Color(255, 255, 255, 18));
        g2.fill(new Ellipse2D.Double(getWidth() - 220, -60, 200, 200));
        g2.fill(new Ellipse2D.Double(-70, getHeight() - 170, 180, 180));
        g2.fill(new Ellipse2D.Double(getWidth() - 140, getHeight() - 120, 120, 120));

        g2.setColor(new Color(148, 163, 184, 20));
        g2.fill(new RoundRectangle2D.Double(34, 150, getWidth() - 110, 150, 34, 34));
        g2.fill(new RoundRectangle2D.Double(80, 92, 124, 36, 18, 18));

        g2.setColor(new Color(255, 255, 255, 24));
        for (int index = 0; index < 6; index++) {
            int x = getWidth() - 80 + (index * 18);
            int y = 160 + (index * 20);
            g2.fillOval(x, y, 4, 4);
        }

        g2.setColor(new Color(255, 255, 255, 28));
        g2.draw(new RoundRectangle2D.Double(0.7, 0.7, getWidth() - 13.4, getHeight() - 13.4, 30, 30));
        g2.dispose();

        super.paintComponent(graphics);
    }

    /**
     * Crea la cabecera con isotipo y marca.
     *
     * @return panel de cabecera
     */
    private JPanel crearEncabezadoMarca() {
        JPanel header = new JPanel(new BorderLayout(16, 0));
        header.setOpaque(false);

        header.add(new LogoPanel(), BorderLayout.WEST);

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JPanel chip = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        chip.setOpaque(false);
        chip.add(crearBadge("Premium payroll suite"));
        chip.add(crearBadge("Costa Rica"));

        JLabel marca = new JLabel("PAY SPHERE");
        marca.setFont(TemaVisual.FUENTE_SUBTITULO.deriveFont(22f));
        marca.setForeground(Color.WHITE);

        JLabel descripcion = new JLabel("Enterprise payroll platform");
        descripcion.setFont(TemaVisual.FUENTE_LABEL);
        descripcion.setForeground(new Color(191, 219, 254));

        textos.add(chip);
        textos.add(Box.createVerticalStrut(12));
        textos.add(marca);
        textos.add(Box.createVerticalStrut(4));
        textos.add(descripcion);

        header.add(textos, BorderLayout.CENTER);
        return header;
    }

    /**
     * Crea el contenido hero central con copy y beneficios.
     *
     * @return panel central del hero
     */
    private JPanel crearContenidoCentral() {
        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(BorderFactory.createEmptyBorder(24, 0, 16, 0));

        JLabel overline = new JLabel("PLATAFORMA DE OPERACION EMPRESARIAL");
        overline.setFont(TemaVisual.FUENTE_LABEL);
        overline.setForeground(new Color(191, 219, 254));
        overline.setAlignmentX(LEFT_ALIGNMENT);

        JLabel titulo = new JLabel("<html>CONTROL DE NÓMINA<br>CON PRECISIÓN</html>");
        titulo.setFont(TemaVisual.FUENTE_HERO);
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("<html>Automatice calculos, reportes y trazabilidad en una experiencia segura, sobria y profesional.</html>");
        subtitulo.setFont(TemaVisual.FUENTE_NORMAL.deriveFont(15f));
        subtitulo.setForeground(new Color(219, 234, 254));
        subtitulo.setAlignmentX(LEFT_ALIGNMENT);

        JPanel metrica = crearTarjetaMetrica();
        metrica.setAlignmentX(LEFT_ALIGNMENT);

        JPanel beneficios = new JPanel(new GridLayout(3, 1, 0, 12));
        beneficios.setOpaque(false);
        beneficios.setAlignmentX(LEFT_ALIGNMENT);
        beneficios.add(new ItemBeneficioLogin("Acceso confiable", "Autenticacion externa y control seguro de ingreso.", ItemBeneficioLogin.TipoIcono.ESCUDO));
        beneficios.add(new ItemBeneficioLogin("Operacion visible", "Dashboard, indicadores y seguimiento del periodo.", ItemBeneficioLogin.TipoIcono.GRAFICA));
        beneficios.add(new ItemBeneficioLogin("Reporteria lista", "PDFs empresariales y distribucion por correo.", ItemBeneficioLogin.TipoIcono.REPORTE));

        contenido.add(overline);
        contenido.add(Box.createVerticalStrut(16));
        contenido.add(titulo);
        contenido.add(Box.createVerticalStrut(12));
        contenido.add(subtitulo);
        contenido.add(Box.createVerticalStrut(20));
        contenido.add(metrica);
        contenido.add(Box.createVerticalStrut(18));
        contenido.add(beneficios);
        return contenido;
    }

    /**
     * Crea el bloque inferior con refuerzo visual.
     *
     * @return panel inferior decorativo
     */
    private JPanel crearBloqueInferior() {
        JPanel footer = new JPanel(new BorderLayout(18, 0));
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        footer.add(crearTarjetaMini("99.9%", "Disponibilidad operativa"), BorderLayout.WEST);
        footer.add(crearTarjetaMini("PDF + SMTP", "Flujo documental integrado"), BorderLayout.CENTER);
        return footer;
    }

    /**
     * Crea una tarjeta de metricas glass.
     *
     * @return tarjeta principal de metricas
     */
    private JPanel crearTarjetaMetrica() {
        JPanel card = new JPanel(new BorderLayout());
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 126));

        JPanel internos = new JPanel(new GridLayout(1, 2, 14, 0));
        internos.setOpaque(false);

        internos.add(crearIndicador("Calculo", "CCSS 2026"));
        internos.add(crearIndicador("Arquitectura", "6 capas"));

        JLabel nota = new JLabel("Disenado para una experiencia tipo banca y dashboards SaaS modernos.");
        nota.setFont(TemaVisual.FUENTE_PEQUENA);
        nota.setForeground(new Color(219, 234, 254));

        card.add(internos, BorderLayout.CENTER);
        card.add(Box.createRigidArea(new Dimension(0, 12)), BorderLayout.NORTH);
        card.add(nota, BorderLayout.SOUTH);

        return new ContenedorGlass(card, new Insets(0, 0, 0, 0), 26);
    }

    /**
     * Crea un indicador interno de la tarjeta metrica.
     *
     * @param etiqueta texto auxiliar
     * @param valor valor principal
     * @return panel indicador
     */
    private JPanel crearIndicador(String etiqueta, String valor) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 0, 4));
        panel.setOpaque(false);

        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(TemaVisual.FUENTE_LABEL);
        lblEtiqueta.setForeground(new Color(191, 219, 254));

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(TemaVisual.FUENTE_SUBTITULO.deriveFont(20f));
        lblValor.setForeground(Color.WHITE);

        panel.add(lblEtiqueta);
        panel.add(lblValor);
        return panel;
    }

    /**
     * Crea una tarjeta mini inferior.
     *
     * @param valor valor principal
     * @param etiqueta descripcion secundaria
     * @return tarjeta visual
     */
    private JPanel crearTarjetaMini(String valor, String etiqueta) {
        JPanel contenido = new JPanel(new GridLayout(0, 1, 0, 4));
        contenido.setOpaque(false);
        contenido.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(TemaVisual.FUENTE_DESTACADA.deriveFont(16f));
        lblValor.setForeground(Color.WHITE);

        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(TemaVisual.FUENTE_PEQUENA);
        lblEtiqueta.setForeground(new Color(219, 234, 254));

        contenido.add(lblValor);
        contenido.add(lblEtiqueta);
        return new ContenedorGlass(contenido, new Insets(0, 0, 0, 0), 22);
    }

    /**
     * Crea un badge textual.
     *
     * @param texto texto del badge
     * @return panel decorativo
     */
    private JPanel crearBadge(String texto) {
        JPanel badge = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
        badge.setOpaque(false);
        JLabel label = new JLabel(texto);
        label.setFont(TemaVisual.FUENTE_LABEL);
        label.setForeground(Color.WHITE);
        badge.add(label);
        return new ContenedorGlass(badge, new Insets(0, 0, 0, 0), 18);
    }

    /**
     * Contenedor reutilizable para bloques glass dentro del hero.
     */
    private static class ContenedorGlass extends JPanel {

        private final int radio;

        /**
         * Crea un contenedor glass para el hero.
         *
         * @param contenido panel de contenido
         * @param insets espaciado interno
         * @param radio radio de borde
         */
        ContenedorGlass(JPanel contenido, Insets insets, int radio) {
            this.radio = radio;
            setOpaque(false);
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createEmptyBorder(insets.top, insets.left, insets.bottom, insets.right));
            add(contenido, BorderLayout.CENTER);
        }

        /**
         * Dibuja el fondo glass.
         *
         * @param graphics contexto grafico
         */
        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g2 = (Graphics2D) graphics.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(255, 255, 255, 18));
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radio, radio));
            g2.setColor(new Color(255, 255, 255, 36));
            g2.draw(new RoundRectangle2D.Double(0.6, 0.6, getWidth() - 1.2, getHeight() - 1.2, radio, radio));
            g2.dispose();
            super.paintComponent(graphics);
        }
    }
}
