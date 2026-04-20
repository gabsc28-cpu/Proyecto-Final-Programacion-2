package Utilidades;

import Excepciones.ValidacionException;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Reune reglas de validacion reutilizables para formularios y servicios.
 */
public final class ValidadorUtil {

    /**
     * Constructor privado para evitar instancias.
     */
    private ValidadorUtil() {
    }

    /**
     * Valida que un texto obligatorio tenga contenido.
     *
     * @param valor texto recibido
     * @param nombreCampo etiqueta del campo
     * @throws ValidacionException si el texto esta vacio
     */
    public static void validarTextoObligatorio(String valor, String nombreCampo) throws ValidacionException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo " + nombreCampo + " es obligatorio.");
        }
    }

    /**
     * Valida que el correo tenga un formato basico correcto.
     *
     * @param correo direccion a validar
     * @throws ValidacionException si el correo es invalido
     */
    public static void validarCorreo(String correo) throws ValidacionException {
        validarTextoObligatorio(correo, "correo");
        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new ValidacionException("El correo electronico no tiene un formato valido.");
        }
    }

    /**
     * Valida que un numero decimal no sea negativo.
     *
     * @param valor numero a validar
     * @param nombreCampo etiqueta visible del campo
     * @throws ValidacionException si el valor es nulo o negativo
     */
    public static void validarMontoNoNegativo(BigDecimal valor, String nombreCampo) throws ValidacionException {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidacionException("El campo " + nombreCampo + " no puede ser negativo.");
        }
    }

    /**
     * Valida que un monto sea mayor que cero.
     *
     * @param valor numero a validar
     * @param nombreCampo etiqueta visible del campo
     * @throws ValidacionException si el valor es nulo o no es positivo
     */
    public static void validarMontoPositivo(BigDecimal valor, String nombreCampo) throws ValidacionException {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacionException("El campo " + nombreCampo + " debe ser mayor a cero.");
        }
    }

    /**
     * Valida que un entero no sea negativo.
     *
     * @param valor numero recibido
     * @param nombreCampo etiqueta visible del campo
     * @throws ValidacionException si el valor es negativo
     */
    public static void validarEnteroNoNegativo(int valor, String nombreCampo) throws ValidacionException {
        if (valor < 0) {
            throw new ValidacionException("El campo " + nombreCampo + " no puede ser negativo.");
        }
    }

    /**
     * Valida que la fecha de ingreso este presente.
     *
     * @param fecha fecha a validar
     * @throws ValidacionException si no se ha indicado una fecha valida
     */
    public static void validarFecha(LocalDate fecha) throws ValidacionException {
        if (fecha == null) {
            throw new ValidacionException("La fecha de ingreso es obligatoria.");
        }
    }
}
