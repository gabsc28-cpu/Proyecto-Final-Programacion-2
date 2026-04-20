package Utilidades;

import Excepciones.ValidacionException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Centraliza conversiones y formatos visibles para el usuario.
 */
public final class FormatoUtil {

    private static final Locale LOCALE_CR = new Locale("es", "CR");
    private static final NumberFormat FORMATO_MONEDA = NumberFormat.getCurrencyInstance(LOCALE_CR);
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_PERIODO = DateTimeFormatter.ofPattern("MM/yyyy");

    /**
     * Constructor privado para evitar instancias.
     */
    private FormatoUtil() {
    }

    /**
     * Convierte un monto numerico a texto monetario en colones.
     *
     * @param monto valor a formatear
     * @return representacion monetaria
     */
    public static String formatearMoneda(BigDecimal monto) {
        return FORMATO_MONEDA.format(monto);
    }

    /**
     * Convierte una fecha local a su representacion visible.
     *
     * @param fecha fecha a formatear
     * @return fecha en formato dd/MM/yyyy
     */
    public static String formatearFecha(LocalDate fecha) {
        return fecha == null ? "-" : fecha.format(FORMATO_FECHA);
    }

    /**
     * Obtiene el periodo actual en formato MM/yyyy.
     *
     * @return texto del periodo actual
     */
    public static String obtenerPeriodoActual() {
        return LocalDate.now().format(FORMATO_PERIODO);
    }

    /**
     * Convierte texto numerico a {@link BigDecimal}.
     *
     * @param texto valor recibido desde la interfaz
     * @return valor decimal parseado
     * @throws ValidacionException si el valor no es numerico
     */
    public static BigDecimal parsearBigDecimal(String texto) throws ValidacionException {
        if (texto == null || texto.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }

        String valorLimpio = texto.trim()
                .replace("¢", "")
                .replace(" ", "");

        if (valorLimpio.contains(",") && !valorLimpio.contains(".")) {
            valorLimpio = valorLimpio.replace(",", ".");
        } else {
            valorLimpio = valorLimpio.replace(",", "");
        }

        try {
            return new BigDecimal(valorLimpio);
        } catch (NumberFormatException exception) {
            throw new ValidacionException("El valor numerico ingresado no es valido.");
        }
    }
}
