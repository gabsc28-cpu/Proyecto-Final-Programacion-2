package Excepciones;

/**
 * Excepcion lanzada cuando la autenticacion del usuario falla.
 */
public class AutenticacionFallidaException extends Exception {

    /**
     * Crea una nueva excepcion con el mensaje indicado.
     *
     * @param mensaje detalle del error de autenticacion
     */
    public AutenticacionFallidaException(String mensaje) {
        super(mensaje);
    }
}
