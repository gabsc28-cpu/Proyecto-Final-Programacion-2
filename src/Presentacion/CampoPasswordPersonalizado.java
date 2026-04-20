package Presentacion;

import Utilidades.TemaVisual;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Campo de contrasena estilizado para formularios.
 */
public class CampoPasswordPersonalizado extends JPasswordField {

    private final CompoundBorder bordeNormal;
    private final CompoundBorder bordeError;

    /**
     * Crea un campo de contrasena con apariencia corporativa.
     */
    public CampoPasswordPersonalizado() {
        bordeNormal = new CompoundBorder(
                new LineBorder(new Color(203, 213, 225), 1, true),
                new EmptyBorder(11, 12, 11, 12)
        );
        bordeError = new CompoundBorder(
                new LineBorder(TemaVisual.ROJO_ERROR, 1, true),
                new EmptyBorder(11, 12, 11, 12)
        );
        initComponents();
        initStyles();
        initEvents();
    }

    /**
     * Inicializa propiedades base del componente.
     */
    private void initComponents() {
        setColumns(18);
    }

    /**
     * Aplica el estilo visual del componente.
     */
    private void initStyles() {
        setFont(TemaVisual.FUENTE_NORMAL);
        setForeground(TemaVisual.TEXTO_PRINCIPAL);
        setBackground(Color.WHITE);
        setCaretColor(TemaVisual.AZUL_PRINCIPAL);
        setBorder(bordeNormal);
    }

    /**
     * Registra eventos del componente.
     */
    private void initEvents() {
        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent event) {
                setBorder(bordeNormal);
            }
        });
    }

    /**
     * Marca visualmente el campo como invalido.
     */
    public void mostrarError() {
        setBorder(bordeError);
    }

    /**
     * Restaura el estilo base del campo.
     */
    public void limpiarEstado() {
        setBorder(bordeNormal);
    }
}
