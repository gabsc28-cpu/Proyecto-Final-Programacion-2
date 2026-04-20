package Entidades;

/**
 * Representa a un usuario autorizado para ingresar al sistema.
 */
public class Usuario {

    private String username;
    private String passwordHash;
    private String rol;
    private String nombreMostrar;
    private boolean activo;

    /**
     * Crea un usuario vacio.
     */
    public Usuario() {
    }

    /**
     * Crea un usuario con todos sus datos.
     *
     * @param username nombre de acceso
     * @param passwordHash hash de la contrasena
     * @param rol rol funcional del usuario
     * @param nombreMostrar nombre visible en la interfaz
     * @param activo indicador de estado
     */
    public Usuario(String username, String passwordHash, String rol, String nombreMostrar, boolean activo) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.nombreMostrar = nombreMostrar;
        this.activo = activo;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return nombre de acceso
     */
    public String getUsername() {
        return username;
    }

    /**
     * Define el nombre de usuario.
     *
     * @param username nombre de acceso
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene el hash de la contrasena.
     *
     * @return hash protegido
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Define el hash de la contrasena.
     *
     * @param passwordHash hash protegido
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return rol funcional
     */
    public String getRol() {
        return rol;
    }

    /**
     * Define el rol del usuario.
     *
     * @param rol rol funcional
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el nombre visible del usuario.
     *
     * @return nombre para cabeceras y saludos
     */
    public String getNombreMostrar() {
        return nombreMostrar;
    }

    /**
     * Define el nombre visible del usuario.
     *
     * @param nombreMostrar nombre para cabeceras y saludos
     */
    public void setNombreMostrar(String nombreMostrar) {
        this.nombreMostrar = nombreMostrar;
    }

    /**
     * Indica si el usuario se encuentra activo.
     *
     * @return {@code true} si el usuario esta habilitado
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Define el estado del usuario.
     *
     * @param activo estado actual del usuario
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
