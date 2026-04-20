package Excepciones;

/**
 * Excepcion lanzada cuando un dato de entrada no cumple las reglas de negocio.
 */
public class ValidacionException extends Exception {

    /**
     * Crea una nueva excepcion con el mensaje indicado.
     *
     * @param mensaje detalle del error detectado
     */
    public ValidacionException(String mensaje) {
        super(mensaje);
    }
}
