package Logica;

import Excepciones.CorreoException;
import Utilidades.RutaAplicacion;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Implementa el envio automatico de reportes PDF por correo usando JavaMail.
 */
public class CorreoNominaService implements CorreoService {

    /**
     * Envia un reporte PDF como adjunto.
     *
     * @param destinatario correo destino
     * @param asunto asunto del mensaje
     * @param mensaje cuerpo del mensaje
     * @param adjunto archivo a adjuntar
     * @throws CorreoException si la configuracion es invalida o el envio falla
     */
    @Override
    public void enviarReporte(String destinatario, String asunto, String mensaje, File adjunto) throws CorreoException {
        Properties config = cargarConfiguracion();
        if (!Boolean.parseBoolean(config.getProperty("mail.enabled", "false"))) {
            throw new CorreoException("El envio de correos esta deshabilitado. Revise data/config-correo.properties.");
        }

        String usuario = obtenerPropiedadObligatoria(config, "smtp.user");
        String password = obtenerPropiedadObligatoria(config, "smtp.password");
        String remitente = obtenerPropiedadObligatoria(config, "mail.from");

        Properties smtp = new Properties();
        smtp.put("mail.smtp.auth", "true");
        smtp.put("mail.smtp.starttls.enable", config.getProperty("mail.smtp.starttls", "true"));
        smtp.put("mail.smtp.host", obtenerPropiedadObligatoria(config, "smtp.host"));
        smtp.put("mail.smtp.port", obtenerPropiedadObligatoria(config, "smtp.port"));

        Session session = Session.getInstance(smtp, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, password);
            }
        });

        try {
            MimeMessage correo = new MimeMessage(session);
            correo.setFrom(new InternetAddress(remitente));
            correo.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            correo.setSubject(asunto, "UTF-8");

            MimeBodyPart texto = new MimeBodyPart();
            texto.setText(mensaje, "UTF-8");

            MimeBodyPart archivo = new MimeBodyPart();
            FileDataSource fuente = new FileDataSource(adjunto);
            archivo.setDataHandler(new DataHandler(fuente));
            archivo.setFileName(adjunto.getName());

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(texto);
            multipart.addBodyPart(archivo);

            correo.setContent(multipart);
            Transport.send(correo);
        } catch (MessagingException exception) {
            throw new CorreoException("No fue posible enviar el correo electronico.", exception);
        }
    }

    /**
     * Carga la configuracion SMTP desde el archivo externo.
     *
     * @return propiedades cargadas
     * @throws CorreoException si el archivo no puede leerse
     */
    private Properties cargarConfiguracion() throws CorreoException {
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(RutaAplicacion.obtenerRutaCorreo())) {
            properties.load(input);
            return properties;
        } catch (IOException exception) {
            throw new CorreoException("No fue posible leer la configuracion de correo.", exception);
        }
    }

    /**
     * Obtiene una propiedad obligatoria de la configuracion.
     *
     * @param properties configuracion cargada
     * @param clave propiedad requerida
     * @return valor encontrado
     * @throws CorreoException si la propiedad no tiene contenido
     */
    private String obtenerPropiedadObligatoria(Properties properties, String clave) throws CorreoException {
        String valor = properties.getProperty(clave, "").trim();
        if (valor.isEmpty()) {
            throw new CorreoException("La propiedad " + clave + " debe configurarse antes de enviar correos.");
        }
        return valor;
    }
}
