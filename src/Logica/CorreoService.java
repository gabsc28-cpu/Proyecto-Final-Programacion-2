package Logica;

import Excepciones.CorreoException;
import java.io.File;

/**
 * Define el envio de documentos por correo electronico.
 */
public interface CorreoService {

    /**
     * Envia un archivo adjunto a un destinatario.
     *
     * @param destinatario correo destino
     * @param asunto asunto del mensaje
     * @param mensaje cuerpo del mensaje
     * @param adjunto archivo a adjuntar
     * @throws CorreoException si ocurre un error en el envio
     */
    void enviarReporte(String destinatario, String asunto, String mensaje, File adjunto) throws CorreoException;
}
