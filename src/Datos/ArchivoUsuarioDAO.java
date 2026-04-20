package Datos;

import Entidades.Usuario;
import Excepciones.ArchivoInvalidoException;
import Utilidades.RutaAplicacion;
import java.util.Optional;

/**
 * Administra la persistencia de usuarios en archivos de texto.
 */
public class ArchivoUsuarioDAO extends BaseArchivoDAO<Usuario> {

    /**
     * Crea el DAO de usuarios.
     */
    public ArchivoUsuarioDAO() {
        super(RutaAplicacion.obtenerRutaUsuarios());
    }

    /**
     * Obtiene el identificador principal de un usuario.
     *
     * @param entidad usuario a evaluar
     * @return nombre de usuario
     */
    @Override
    protected String obtenerId(Usuario entidad) {
        return entidad.getUsername();
    }

    /**
     * Convierte una linea del archivo en un usuario.
     *
     * @param linea texto persistido
     * @return usuario construido
     * @throws ArchivoInvalidoException si la linea es invalida
     */
    @Override
    protected Usuario mapearLinea(String linea) throws ArchivoInvalidoException {
        String[] partes = linea.split("\\|");
        if (partes.length != 5) {
            throw new ArchivoInvalidoException("El archivo de usuarios contiene una linea invalida.");
        }

        return new Usuario(
                partes[0],
                partes[1],
                partes[2],
                partes[3],
                Boolean.parseBoolean(partes[4])
        );
    }

    /**
     * Convierte un usuario en su representacion persistible.
     *
     * @param entidad usuario a serializar
     * @return linea del archivo
     */
    @Override
    protected String formatearLinea(Usuario entidad) {
        return String.join("|",
                entidad.getUsername(),
                entidad.getPasswordHash(),
                entidad.getRol(),
                entidad.getNombreMostrar(),
                String.valueOf(entidad.isActivo())
        );
    }

    /**
     * Busca un usuario por nombre de acceso.
     *
     * @param username nombre de acceso
     * @return usuario encontrado o vacio
     * @throws ArchivoInvalidoException si ocurre un error de lectura
     */
    public Optional<Usuario> buscarPorUsername(String username) throws ArchivoInvalidoException {
        return buscarPorId(username);
    }
}
