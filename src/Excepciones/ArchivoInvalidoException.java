package Excepciones;

/**
 * Excepcion lanzada cuando un archivo no puede leerse, escribirse o tiene un
 * formato invalido.
 */
public class ArchivoInvalidoException extends Exception {

    /**
     * Crea una nueva excepcion con el mensaje indicado.
     *
     * @param mensaje detalle del error detectado
     */
    public ArchivoInvalidoException(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea una nueva excepcion con el mensaje y la causa original.
     *
     * @param mensaje detalle del error detectado
     * @param causa causa original del problema
     */
    public ArchivoInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
