package Utilidades;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Genera hashes para proteger credenciales del sistema.
 */
public final class HashUtil {

    /**
     * Constructor privado para evitar instancias.
     */
    private HashUtil() {
    }

    /**
     * Genera un hash SHA-256 para el texto indicado.
     *
     * @param valor texto original
     * @return hash en formato hexadecimal
     */
    public static String generarSha256(String valor) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(valor.getBytes(StandardCharsets.UTF_8));
            StringBuilder resultado = new StringBuilder();
            for (byte elemento : hash) {
                resultado.append(String.format("%02x", elemento));
            }
            return resultado.toString();
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 no esta disponible en el entorno.", exception);
        }
    }
}
