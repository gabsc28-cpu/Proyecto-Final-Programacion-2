package Datos;

import Excepciones.ArchivoInvalidoException;
import Utilidades.ArchivoUtil;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementa operaciones CRUD basicas sobre archivos delimitados por lineas.
 *
 * @param <T> tipo de entidad administrada
 */
public abstract class BaseArchivoDAO<T> implements ArchivoDAO<T> {

    private final Path rutaArchivo;

    /**
     * Crea un DAO basado en archivo.
     *
     * @param rutaArchivo ruta fisica del archivo
     */
    protected BaseArchivoDAO(Path rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    /**
     * Obtiene la ruta fisica del archivo administrado.
     *
     * @return ruta del archivo asociado
     */
    protected Path getRutaArchivo() {
        return rutaArchivo;
    }

    /**
     * Obtiene el identificador primario de la entidad.
     *
     * @param entidad entidad a evaluar
     * @return identificador textual
     */
    protected abstract String obtenerId(T entidad);

    /**
     * Convierte una linea del archivo en una entidad.
     *
     * @param linea texto de entrada
     * @return entidad construida
     * @throws ArchivoInvalidoException si el formato es invalido
     */
    protected abstract T mapearLinea(String linea) throws ArchivoInvalidoException;

    /**
     * Convierte una entidad en una linea del archivo.
     *
     * @param entidad entidad a serializar
     * @return linea lista para persistencia
     */
    protected abstract String formatearLinea(T entidad);

    /**
     * Obtiene todas las entidades almacenadas.
     *
     * @return lista completa de registros
     * @throws ArchivoInvalidoException si ocurre un error de lectura
     */
    @Override
    public List<T> listar() throws ArchivoInvalidoException {
        List<String> lineas = ArchivoUtil.leerLineas(rutaArchivo);
        List<T> resultado = new ArrayList<>();

        for (String linea : lineas) {
            if (linea == null || linea.trim().isEmpty() || linea.trim().startsWith("#")) {
                continue;
            }
            resultado.add(mapearLinea(linea));
        }

        return resultado;
    }

    /**
     * Busca una entidad por su identificador.
     *
     * @param id identificador primario
     * @return entidad encontrada o vacia
     * @throws ArchivoInvalidoException si ocurre un error de lectura
     */
    @Override
    public Optional<T> buscarPorId(String id) throws ArchivoInvalidoException {
        return listar().stream()
                .filter(entidad -> obtenerId(entidad).equalsIgnoreCase(id))
                .findFirst();
    }

    /**
     * Almacena una nueva entidad.
     *
     * @param entidad entidad a guardar
     * @throws ArchivoInvalidoException si ocurre un error de integridad o
     * escritura
     */
    @Override
    public void guardar(T entidad) throws ArchivoInvalidoException {
        List<T> entidades = listar();
        boolean existe = entidades.stream()
                .anyMatch(actual -> obtenerId(actual).equalsIgnoreCase(obtenerId(entidad)));

        if (existe) {
            throw new ArchivoInvalidoException("Ya existe un registro con el identificador " + obtenerId(entidad));
        }

        entidades.add(entidad);
        persistir(entidades);
    }

    /**
     * Actualiza una entidad existente.
     *
     * @param entidad entidad con datos actualizados
     * @throws ArchivoInvalidoException si el registro no existe o no puede
     * persistirse
     */
    @Override
    public void actualizar(T entidad) throws ArchivoInvalidoException {
        List<T> entidades = listar();
        boolean actualizado = false;

        for (int indice = 0; indice < entidades.size(); indice++) {
            if (obtenerId(entidades.get(indice)).equalsIgnoreCase(obtenerId(entidad))) {
                entidades.set(indice, entidad);
                actualizado = true;
                break;
            }
        }

        if (!actualizado) {
            throw new ArchivoInvalidoException("No se encontro el registro solicitado para actualizar.");
        }

        persistir(entidades);
    }

    /**
     * Elimina una entidad por su identificador.
     *
     * @param id identificador a eliminar
     * @throws ArchivoInvalidoException si ocurre un error de escritura
     */
    @Override
    public void eliminar(String id) throws ArchivoInvalidoException {
        List<T> entidades = listar();
        entidades.removeIf(entidad -> obtenerId(entidad).equalsIgnoreCase(id));
        persistir(entidades);
    }

    /**
     * Persiste la coleccion completa en disco.
     *
     * @param entidades entidades a almacenar
     * @throws ArchivoInvalidoException si ocurre un error de escritura
     */
    protected void persistir(List<T> entidades) throws ArchivoInvalidoException {
        List<String> lineas = new ArrayList<>();
        for (T entidad : entidades) {
            lineas.add(formatearLinea(entidad));
        }
        ArchivoUtil.escribirLineas(rutaArchivo, lineas);
    }
}
