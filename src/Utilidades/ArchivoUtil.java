package Utilidades;

import Excepciones.ArchivoInvalidoException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.List;

/**
 * Proporciona utilidades para lectura, escritura y preparacion de archivos.
 */
public final class ArchivoUtil {

    /**
     * Constructor privado para evitar instancias.
     */
    private ArchivoUtil() {
    }

    /**
     * Copia un recurso del classpath hacia un archivo de destino si aun no
     * existe.
     *
     * @param recurso ruta del recurso dentro del classpath
     * @param destino archivo destino en disco
     * @throws ArchivoInvalidoException si ocurre un error de E/S
     */
    public static void copiarRecursoSiNoExiste(String recurso, Path destino)
            throws ArchivoInvalidoException {
        if (Files.exists(destino)) {
            return;
        }

        try {
            Files.createDirectories(destino.getParent());
            try (InputStream input = ArchivoUtil.class.getResourceAsStream(recurso)) {
                if (input == null) {
                    Files.writeString(destino, "", StandardCharsets.UTF_8);
                    return;
                }
                Files.copy(input, destino);
            }
        } catch (IOException exception) {
            throw new ArchivoInvalidoException(
                    "No fue posible crear el archivo base: " + destino.getFileName(),
                    exception
            );
        }
    }

    /**
     * Lee todas las lineas de un archivo usando UTF-8.
     *
     * @param ruta archivo a leer
     * @return lista de lineas del archivo
     * @throws ArchivoInvalidoException si ocurre un error de lectura
     */
    public static List<String> leerLineas(Path ruta) throws ArchivoInvalidoException {
        try {
            if (!Files.exists(ruta)) {
                Files.createDirectories(ruta.getParent());
                Files.writeString(ruta, "", StandardCharsets.UTF_8);
            }
            return Files.readAllLines(ruta, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new ArchivoInvalidoException("No fue posible leer el archivo " + ruta, exception);
        }
    }

    /**
     * Escribe todas las lineas indicadas sobre un archivo usando UTF-8.
     *
     * @param ruta archivo a escribir
     * @param lineas contenido a almacenar
     * @throws ArchivoInvalidoException si ocurre un error de escritura
     */
    public static void escribirLineas(Path ruta, List<String> lineas) throws ArchivoInvalidoException {
        try {
            Files.createDirectories(ruta.getParent());
            Files.write(ruta, lineas, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new ArchivoInvalidoException("No fue posible escribir el archivo " + ruta, exception);
        }
    }

    /**
     * Genera un nombre de archivo seguro removiendo caracteres especiales.
     *
     * @param valor texto original
     * @return texto normalizado para su uso como nombre de archivo
     */
    public static String normalizarNombreArchivo(String valor) {
        String textoNormalizado = Normalizer.normalize(valor, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return textoNormalizado
                .replaceAll("[^a-zA-Z0-9-_]+", "_")
                .replaceAll("_+", "_")
                .replaceAll("^_|_$", "")
                .toLowerCase();
    }
}
