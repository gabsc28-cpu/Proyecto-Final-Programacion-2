package Datos;

import Entidades.Empleado;
import Excepciones.ArchivoInvalidoException;
import Utilidades.RutaAplicacion;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Administra la persistencia de empleados en archivos de texto.
 */
public class ArchivoEmpleadoDAO extends BaseArchivoDAO<Empleado> {

    /**
     * Crea el DAO de empleados.
     */
    public ArchivoEmpleadoDAO() {
        super(RutaAplicacion.obtenerRutaEmpleados());
    }

    /**
     * Obtiene el identificador primario del empleado.
     *
     * @param entidad empleado a evaluar
     * @return identificador interno
     */
    @Override
    protected String obtenerId(Empleado entidad) {
        return entidad.getId();
    }

    /**
     * Convierte una linea del archivo en un empleado.
     *
     * @param linea texto persistido
     * @return empleado construido
     * @throws ArchivoInvalidoException si la linea es invalida
     */
    @Override
    protected Empleado mapearLinea(String linea) throws ArchivoInvalidoException {
        String[] partes = linea.split("\\|");
        if (partes.length != 12) {
            throw new ArchivoInvalidoException("El archivo de empleados contiene una linea invalida.");
        }

        return new Empleado(
                partes[0],
                partes[1],
                partes[2],
                partes[3],
                partes[4],
                partes[5],
                partes[6],
                LocalDate.parse(partes[7]),
                new BigDecimal(partes[8]),
                Integer.parseInt(partes[9]),
                new BigDecimal(partes[10]),
                Boolean.parseBoolean(partes[11])
        );
    }

    /**
     * Convierte un empleado en una linea persistible.
     *
     * @param entidad empleado a serializar
     * @return linea lista para escritura
     */
    @Override
    protected String formatearLinea(Empleado entidad) {
        return String.join("|",
                entidad.getId(),
                entidad.getCedula(),
                entidad.getNombreCompleto(),
                entidad.getPuesto(),
                entidad.getDepartamento(),
                entidad.getCorreo(),
                entidad.getTelefono(),
                entidad.getFechaIngreso().toString(),
                entidad.getSalarioBase().toPlainString(),
                String.valueOf(entidad.getHorasExtra()),
                entidad.getBonificacion().toPlainString(),
                String.valueOf(entidad.isActivo())
        );
    }

    /**
     * Busca un empleado por su cedula.
     *
     * @param cedula documento de identidad
     * @return empleado encontrado o vacio
     * @throws ArchivoInvalidoException si ocurre un error de lectura
     */
    public Optional<Empleado> buscarPorCedula(String cedula) throws ArchivoInvalidoException {
        return listar().stream()
                .filter(empleado -> empleado.getCedula().equalsIgnoreCase(cedula))
                .findFirst();
    }
}
