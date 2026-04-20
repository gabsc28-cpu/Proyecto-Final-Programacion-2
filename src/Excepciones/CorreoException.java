package Excepciones;

/**
 * Excepcion lanzada cuando ocurre un problema durante el envio de correos.
 */
public class CorreoException extends Exception {

    /**
     * Crea una nueva excepcion con el mensaje indicado.
     *
     * @param mensaje detalle del error detectado
     */
    public CorreoException(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea una nueva excepcion con el mensaje y la causa original.
     *
     * @param mensaje detalle del error detectado
     * @param causa causa original del problema
     */
    public CorreoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
