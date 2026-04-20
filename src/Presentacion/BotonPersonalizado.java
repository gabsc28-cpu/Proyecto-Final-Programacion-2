package Presentacion;

import Utilidades.TemaVisual;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Define un boton reutilizable con estilo corporativo.
 */
public class BotonPersonalizado extends JButton {

    /**
     * Define variantes visuales del boton.
     */
    public enum Estilo {
        PRIMARIO,
        SECUNDARIO,
        PELIGRO,
        MENU
    }

    private final Estilo estilo;
    private Color colorBase;
    private Color colorHover;
    private Color colorSeleccionado;
    private Color colorTexto;
    private boolean seleccionado;

    /**
     * Crea un boton estilizado.
     *
     * @param texto texto visible
     * @param estilo variante visual deseada
     */
    public BotonPersonalizado(String texto, Estilo estilo) {
        super(texto);
        this.estilo = estilo;
        initComponents();
        initStyles();
        initEvents();
    }

    /**
     * Inicializa propiedades base del componente.
     */
    private void initComponents() {
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setMargin(new Insets(0, 0, 0, 0));
    }

    /**
     * Aplica el estilo visual del boton.
     */
    private void initStyles() {
        switch (estilo) {
            case PRIMARIO -> {
                colorBase = TemaVisual.AZUL_SECUNDARIO;
                colorHover = new Color(29, 78, 216);
                colorSeleccionado = colorHover;
                colorTexto = Color.WHITE;
            }
            case SECUNDARIO -> {
                colorBase = Color.WHITE;
                colorHover = new Color(239, 246, 255);
                colorSeleccionado = new Color(219, 234, 254);
                colorTexto = TemaVisual.AZUL_PRINCIPAL;
                setBorder(BorderFactory.createLineBorder(new Color(191, 219, 254), 1, true));
            }
            case PELIGRO -> {
                colorBase = TemaVisual.ROJO_ERROR;
                colorHover = new Color(185, 28, 28);
                colorSeleccionado = colorHover;
                colorTexto = Color.WHITE;
            }
            case MENU -> {
                colorBase = TemaVisual.AZUL_PRINCIPAL;
                colorHover = new Color(30, 64, 110);
                colorSeleccionado = TemaVisual.AZUL_SECUNDARIO;
                colorTexto = Color.WHITE;
                setHorizontalAlignment(SwingConstants.LEFT);
            }
            default -> throw new IllegalStateException("Estilo no soportado.");
        }

        setFont(TemaVisual.FUENTE_DESTACADA);
        setBackground(colorBase);
        setForeground(colorTexto);
        setOpaque(true);
        setBorder(new EmptyBorder(13, 18, 13, 18));
    }

    /**
     * Registra el comportamiento visual de hover.
     */
    private void initEvents() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                if (isEnabled() && !seleccionado) {
                    setBackground(colorHover);
                }
            }

            @Override
            public void mouseExited(MouseEvent event) {
                if (isEnabled() && !seleccionado) {
                    setBackground(colorBase);
                }
            }
        });
    }

    /**
     * Cambia el estado visual de seleccion del boton.
     *
     * @param seleccionado nuevo estado visual
     */
    public void establecerSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
        setBackground(seleccionado ? colorSeleccionado : colorBase);
    }
}
