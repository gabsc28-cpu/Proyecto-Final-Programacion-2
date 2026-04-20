package Utilidades;

import Excepciones.ArchivoInvalidoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Define y prepara las rutas fisicas utilizadas por la aplicacion.
 */
public final class RutaAplicacion {

    private static final Path BASE_DIR = Paths.get(System.getProperty("user.dir"));
    private static final Path DATA_DIR = BASE_DIR.resolve("data");
    private static final Path REPORTES_DIR = BASE_DIR.resolve("reportes");
    private static final Path USUARIOS_FILE = DATA_DIR.resolve("usuarios.txt");
    private static final Path EMPLEADOS_FILE = DATA_DIR.resolve("empleados.txt");
    private static final Path CORREO_FILE = DATA_DIR.resolve("config-correo.properties");

    /**
     * Constructor privado para evitar instancias.
     */
    private RutaAplicacion() {
    }

    /**
     * Prepara directorios y archivos base necesarios para la ejecucion del
     * sistema.
     *
     * @throws ArchivoInvalidoException si ocurre un error durante la
     * inicializacion
     */
    public static void inicializarEntorno() throws ArchivoInvalidoException {
        try {
            Files.createDirectories(DATA_DIR);
            Files.createDirectories(REPORTES_DIR);
        } catch (IOException exception) {
            throw new ArchivoInvalidoException("No fue posible preparar la estructura del proyecto.", exception);
        }

        ArchivoUtil.copiarRecursoSiNoExiste("/Utilidades/usuarios_seed.txt", USUARIOS_FILE);
        ArchivoUtil.copiarRecursoSiNoExiste("/Utilidades/empleados_seed.txt", EMPLEADOS_FILE);
        ArchivoUtil.copiarRecursoSiNoExiste("/Utilidades/correo_seed.properties", CORREO_FILE);
    }

    /**
     * Obtiene la ruta base actual del proyecto.
     *
     * @return ruta base de ejecucion
     */
    public static Path obtenerBaseDir() {
        return BASE_DIR;
    }

    /**
     * Obtiene la ruta del archivo de usuarios.
     *
     * @return ruta de almacenamiento de usuarios
     */
    public static Path obtenerRutaUsuarios() {
        return USUARIOS_FILE;
    }

    /**
     * Obtiene la ruta del archivo de empleados.
     *
     * @return ruta de almacenamiento de empleados
     */
    public static Path obtenerRutaEmpleados() {
        return EMPLEADOS_FILE;
    }

    /**
     * Obtiene la ruta del archivo de configuracion de correo.
     *
     * @return ruta de configuracion SMTP
     */
    public static Path obtenerRutaCorreo() {
        return CORREO_FILE;
    }

    /**
     * Obtiene la ruta del directorio de reportes.
     *
     * @return carpeta de reportes
     */
    public static Path obtenerRutaReportes() {
        return REPORTES_DIR;
    }
}
