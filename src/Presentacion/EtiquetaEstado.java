package Presentacion;

import Utilidades.TemaVisual;
import javax.swing.JLabel;

/**
 * Etiqueta reutilizable para mensajes de estado visual.
 */
public class EtiquetaEstado extends JLabel {

    /**
     * Crea una nueva etiqueta de estado.
     */
    public EtiquetaEstado() {
        initComponents();
        initStyles();
        initEvents();
    }

    /**
     * Inicializa propiedades base.
     */
    private void initComponents() {
        setText(" ");
    }

    /**
     * Aplica estilo base.
     */
    private void initStyles() {
        setFont(TemaVisual.FUENTE_NORMAL);
        setForeground(TemaVisual.TEXTO_SECUNDARIO);
    }

    /**
     * Registra eventos del componente.
     */
    private void initEvents() {
    }

    /**
     * Muestra un mensaje de error.
     *
     * @param mensaje detalle visible
     */
    public void mostrarError(String mensaje) {
        setForeground(TemaVisual.ROJO_ERROR);
        setText(mensaje);
    }

    /**
     * Muestra un mensaje de exito.
     *
     * @param mensaje detalle visible
     */
    public void mostrarExito(String mensaje) {
        setForeground(TemaVisual.VERDE_EXITO);
        setText(mensaje);
    }

    /**
     * Muestra un mensaje informativo.
     *
     * @param mensaje detalle visible
     */
    public void mostrarInfo(String mensaje) {
        setForeground(TemaVisual.TEXTO_SECUNDARIO);
        setText(mensaje);
    }

    /**
     * Limpia el texto visible.
     */
    public void limpiar() {
        setText(" ");
    }
}
