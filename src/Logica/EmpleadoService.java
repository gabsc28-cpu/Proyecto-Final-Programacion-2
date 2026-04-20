package Logica;

import Datos.ArchivoEmpleadoDAO;
import Entidades.Empleado;
import Excepciones.ArchivoInvalidoException;
import Excepciones.ValidacionException;
import Utilidades.ValidadorUtil;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Orquesta reglas de negocio relacionadas con empleados.
 */
public class EmpleadoService {

    private final ArchivoEmpleadoDAO empleadoDAO;

    /**
     * Crea un servicio de empleados con su DAO por defecto.
     */
    public EmpleadoService() {
        this(new ArchivoEmpleadoDAO());
    }

    /**
     * Crea un servicio de empleados con el DAO indicado.
     *
     * @param empleadoDAO acceso a datos de empleados
     */
    public EmpleadoService(ArchivoEmpleadoDAO empleadoDAO) {
        this.empleadoDAO = empleadoDAO;
    }

    /**
     * Devuelve todos los empleados registrados ordenados por nombre.
     *
     * @return lista completa de empleados
     * @throws ArchivoInvalidoException si ocurre un error de lectura
     */
    public List<Empleado> listarEmpleados() throws ArchivoInvalidoException {
        return empleadoDAO.listar().stream()
                .sorted(Comparator.comparing(Empleado::getNombreCompleto))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve solo los empleados activos.
     *
     * @return lista de empleados activos
     * @throws ArchivoInvalidoException si ocurre un error de lectura
     */
    public List<Empleado> listarActivos() throws ArchivoInvalidoException {
        return listarEmpleados().stream()
                .filter(Empleado::isActivo)
                .collect(Collectors.toList());
    }

    /**
     * Registra un nuevo empleado.
     *
     * @param empleado empleado a guardar
     * @throws ArchivoInvalidoException si ocurre un error de persistencia
     * @throws ValidacionException si la informacion es invalida
     */
    public void guardarEmpleado(Empleado empleado) throws ArchivoInvalidoException, ValidacionException {
        validarEmpleado(empleado, false);
        if (empleado.getId() == null || empleado.getId().trim().isEmpty()) {
            empleado.setId("EMP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        empleadoDAO.guardar(empleado);
    }

    /**
     * Actualiza un empleado existente.
     *
     * @param empleado empleado a actualizar
     * @throws ArchivoInvalidoException si ocurre un error de persistencia
     * @throws ValidacionException si la informacion es invalida
     */
    public void actualizarEmpleado(Empleado empleado) throws ArchivoInvalidoException, ValidacionException {
        validarEmpleado(empleado, true);
        empleadoDAO.actualizar(empleado);
    }

    /**
     * Elimina un empleado por su identificador.
     *
     * @param id identificador interno
     * @throws ArchivoInvalidoException si ocurre un error de persistencia
     */
    public void eliminarEmpleado(String id) throws ArchivoInvalidoException {
        empleadoDAO.eliminar(id);
    }

    /**
     * Valida las reglas funcionales del empleado.
     *
     * @param empleado empleado a evaluar
     * @param actualizacion indicador de actualizacion
     * @throws ArchivoInvalidoException si ocurre un error al consultar datos
     * existentes
     * @throws ValidacionException si la informacion es invalida
     */
    private void validarEmpleado(Empleado empleado, boolean actualizacion)
            throws ArchivoInvalidoException, ValidacionException {
        ValidadorUtil.validarTextoObligatorio(empleado.getCedula(), "cedula");
        ValidadorUtil.validarTextoObligatorio(empleado.getNombreCompleto(), "nombre completo");
        ValidadorUtil.validarTextoObligatorio(empleado.getPuesto(), "puesto");
        ValidadorUtil.validarTextoObligatorio(empleado.getDepartamento(), "departamento");
        ValidadorUtil.validarCorreo(empleado.getCorreo());
        ValidadorUtil.validarTextoObligatorio(empleado.getTelefono(), "telefono");
        ValidadorUtil.validarFecha(empleado.getFechaIngreso());
        ValidadorUtil.validarMontoPositivo(empleado.getSalarioBase(), "salario base");
        ValidadorUtil.validarMontoNoNegativo(empleado.getBonificacion(), "bonificacion");
        ValidadorUtil.validarEnteroNoNegativo(empleado.getHorasExtra(), "horas extra");

        boolean cedulaDuplicada = listarEmpleados().stream()
                .anyMatch(actual -> actual.getCedula().equalsIgnoreCase(empleado.getCedula())
                && (!actualizacion || !actual.getId().equalsIgnoreCase(empleado.getId())));

        if (cedulaDuplicada) {
            throw new ValidacionException("Ya existe un empleado registrado con esa cedula.");
        }
    }
}
