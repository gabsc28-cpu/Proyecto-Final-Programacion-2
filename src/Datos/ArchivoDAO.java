package Datos;

import Excepciones.ArchivoInvalidoException;
import java.util.List;
import java.util.Optional;

/**
 * Define operaciones CRUD genericas sobre archivos de texto.
 *
 * @param <T> tipo de entidad administrada
 */
public interface ArchivoDAO<T> {

    /**
     * Obtiene todas las entidades almacenadas.
     *
     * @return lista completa de registros
     * @throws ArchivoInvalidoException si ocurre un error de lectura
     */
    List<T> listar() throws ArchivoInvalidoException;

    /**
     * Busca una entidad por su identificador.
     *
     * @param id identificador primario
     * @return entidad encontrada o vacia
     * @throws ArchivoInvalidoException si ocurre un error de lectura
     */
    Optional<T> buscarPorId(String id) throws ArchivoInvalidoException;

    /**
     * Almacena una nueva entidad.
     *
     * @param entidad entidad a guardar
     * @throws ArchivoInvalidoException si ocurre un error de escritura o
     * integridad
     */
    void guardar(T entidad) throws ArchivoInvalidoException;

    /**
     * Actualiza una entidad existente.
     *
     * @param entidad entidad con datos actualizados
     * @throws ArchivoInvalidoException si ocurre un error de escritura o
     * integridad
     */
    void actualizar(T entidad) throws ArchivoInvalidoException;

    /**
     * Elimina una entidad por su identificador.
     *
     * @param id identificador a eliminar
     * @throws ArchivoInvalidoException si ocurre un error de escritura
     */
    void eliminar(String id) throws ArchivoInvalidoException;
}
