package Logica;

import Entidades.Empleado;
import Entidades.ResumenPatronal;
import Excepciones.ArchivoInvalidoException;
import Utilidades.FormatoUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Consolida indicadores visibles en el dashboard principal.
 */
public class DashboardService {

    private final EmpleadoService empleadoService;
    private final ServicioNomina servicioNomina;

    /**
     * Crea un servicio de dashboard con dependencias por defecto.
     */
    public DashboardService() {
        this(new EmpleadoService(), new ServicioNomina());
    }

    /**
     * Crea un servicio de dashboard con dependencias explicitas.
     *
     * @param empleadoService servicio de empleados
     * @param servicioNomina servicio de nomina
     */
    public DashboardService(EmpleadoService empleadoService, ServicioNomina servicioNomina) {
        this.empleadoService = empleadoService;
        this.servicioNomina = servicioNomina;
    }

    /**
     * Obtiene la cantidad de empleados activos.
     *
     * @return cantidad de colaboradores activos
     * @throws ArchivoInvalidoException si ocurre un error de lectura
     */
    public int contarEmpleadosActivos() throws ArchivoInvalidoException {
        return empleadoService.listarActivos().size();
    }

    /**
     * Obtiene el salario promedio de empleados activos.
     *
     * @return salario promedio del personal
     * @throws ArchivoInvalidoException si ocurre un error de lectura
     */
    public BigDecimal obtenerSalarioPromedio() throws ArchivoInvalidoException {
        List<Empleado> activos = empleadoService.listarActivos();
        if (activos.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal acumulado = BigDecimal.ZERO;
        for (Empleado empleado : activos) {
            acumulado = acumulado.add(empleado.getSalarioBase());
        }
        return acumulado.divide(new BigDecimal(activos.size()), 2, RoundingMode.HALF_UP);
    }

    /**
     * Genera un resumen patronal para el periodo actual.
     *
     * @return resumen consolidado del periodo actual
     * @throws ArchivoInvalidoException si ocurre un error de lectura
     */
    public ResumenPatronal obtenerResumenPeriodoActual() throws ArchivoInvalidoException {
        return servicioNomina.generarResumenPatronal(FormatoUtil.obtenerPeriodoActual());
    }

    /**
     * Devuelve los ultimos empleados ordenados por fecha de ingreso.
     *
     * @return lista de empleados recientes
     * @throws ArchivoInvalidoException si ocurre un error de lectura
     */
    public List<Empleado> listarIngresosRecientes() throws ArchivoInvalidoException {
        return empleadoService.listarEmpleados().stream()
                .sorted(Comparator.comparing(Empleado::getFechaIngreso).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}
