package Utilidades;

import java.awt.Color;
import java.awt.Font;

/**
 * Centraliza la paleta y tipografia visual de la aplicacion.
 */
public final class TemaVisual {

    /** Color principal oscuro del sistema. */
    public static final Color AZUL_PRINCIPAL = new Color(15, 38, 74);

    /** Color secundario para estados activos. */
    public static final Color AZUL_SECUNDARIO = new Color(37, 99, 235);

    /** Color acento para detalles visuales premium. */
    public static final Color AZUL_ACENTO = new Color(96, 165, 250);

    /** Color utilizado en superficies de apoyo. */
    public static final Color AZUL_SUAVE = new Color(226, 232, 240);

    /** Fondo general de la aplicacion. */
    public static final Color FONDO_APP = new Color(241, 245, 249);

    /** Fondo claro para tarjetas y contenedores. */
    public static final Color BLANCO_TARJETA = new Color(255, 255, 255);

    /** Color principal del texto. */
    public static final Color TEXTO_PRINCIPAL = new Color(15, 23, 42);

    /** Color secundario del texto. */
    public static final Color TEXTO_SECUNDARIO = new Color(100, 116, 139);

    /** Color de exito. */
    public static final Color VERDE_EXITO = new Color(22, 163, 74);

    /** Color de alerta o error. */
    public static final Color ROJO_ERROR = new Color(220, 38, 38);

    /** Color de advertencia. */
    public static final Color NARANJA = new Color(234, 88, 12);

    /** Fuente base de titulos. */
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 26);

    /** Fuente de mayor presencia para secciones hero. */
    public static final Font FUENTE_HERO = new Font("Segoe UI", Font.BOLD, 34);

    /** Fuente base de subtitulos. */
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 18);

    /** Fuente de apoyo para etiquetas cortas o microcopys. */
    public static final Font FUENTE_LABEL = new Font("Segoe UI Semibold", Font.PLAIN, 12);

    /** Fuente base para texto normal. */
    public static final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);

    /** Fuente base para texto pequeno. */
    public static final Font FUENTE_PEQUENA = new Font("Segoe UI", Font.PLAIN, 12);

    /** Fuente base para texto destacado. */
    public static final Font FUENTE_DESTACADA = new Font("Segoe UI", Font.BOLD, 14);

    /**
     * Constructor privado para evitar instancias.
     */
    private TemaVisual() {
    }
}
