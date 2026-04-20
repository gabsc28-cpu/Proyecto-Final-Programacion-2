package Logica;

import Datos.ArchivoUsuarioDAO;
import Entidades.Usuario;
import Excepciones.ArchivoInvalidoException;
import Excepciones.AutenticacionFallidaException;
import Utilidades.HashUtil;

/**
 * Gestiona el proceso de autenticacion del sistema.
 */
public class AutenticacionService {

    private final ArchivoUsuarioDAO usuarioDAO;

    /**
     * Crea un servicio de autenticacion con su DAO por defecto.
     */
    public AutenticacionService() {
        this(new ArchivoUsuarioDAO());
    }

    /**
     * Crea un servicio de autenticacion con el DAO indicado.
     *
     * @param usuarioDAO acceso a datos de usuarios
     */
    public AutenticacionService(ArchivoUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * Valida credenciales contra el archivo de usuarios.
     *
     * @param username nombre de acceso
     * @param password contrasena en texto plano
     * @return usuario autenticado
     * @throws AutenticacionFallidaException si las credenciales son invalidas
     * @throws ArchivoInvalidoException si ocurre un error al leer usuarios
     */
    public Usuario autenticar(String username, char[] password)
            throws AutenticacionFallidaException, ArchivoInvalidoException {
        if (username == null || username.trim().isEmpty() || password == null || password.length == 0) {
            throw new AutenticacionFallidaException("Debe ingresar usuario y contrasena.");
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username.trim())
                .orElseThrow(() -> new AutenticacionFallidaException("Las credenciales no son correctas."));

        if (!usuario.isActivo()) {
            throw new AutenticacionFallidaException("El usuario se encuentra inactivo. Consulte al administrador.");
        }

        String hashIngresado = HashUtil.generarSha256(new String(password));
        if (!hashIngresado.equalsIgnoreCase(usuario.getPasswordHash())) {
            throw new AutenticacionFallidaException("Las credenciales no son correctas.");
        }

        return usuario;
    }
}
