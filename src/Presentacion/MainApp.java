package Presentacion;

import Excepciones.ArchivoInvalidoException;
import Utilidades.RutaAplicacion;
import Utilidades.TemaVisual;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 * Punto de entrada principal de la aplicacion.
 */
public final class MainApp {

    /**
     * Constructor privado para evitar instancias.
     */
    private MainApp() {
    }

    /**
     * Inicia la aplicacion con su apariencia visual y entorno preparado.
     *
     * @param args argumentos de linea de comandos
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                initLookAndFeel();
                RutaAplicacion.inicializarEntorno();
                new LoginFrame().setVisible(true);
            } catch (ArchivoInvalidoException exception) {
                JOptionPane.showMessageDialog(
                        null,
                        exception.getMessage(),
                        "Error de inicializacion",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    /**
     * Configura FlatLaf y estilos globales de la interfaz.
     */
    private static void initLookAndFeel() {
        FlatLightLaf.setup();
        UIManager.put("defaultFont", new FontUIResource(TemaVisual.FUENTE_NORMAL));
        UIManager.put("Component.arc", 18);
        UIManager.put("Button.arc", 18);
        UIManager.put("TextComponent.arc", 18);
        UIManager.put("ScrollBar.width", 10);
    }
}
