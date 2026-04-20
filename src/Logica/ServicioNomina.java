package Logica;

import Entidades.Empleado;
import Entidades.Nomina;
import Entidades.ResumenPatronal;
import Excepciones.ArchivoInvalidoException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Coordina calculos individuales y masivos de nomina.
 */
public class ServicioNomina {

    private final EmpleadoService empleadoService;
    private final NominaCalculable calculadora;

    /**
     * Crea un servicio de nomina con dependencias por defecto.
     */
    public ServicioNomina() {
        this(new EmpleadoService(), new CalculadoraNominaCostaRica());
    }

    /**
     * Crea un servicio de nomina con dependencias explicitas.
     *
     * @param empleadoService servicio de empleados
     * @param calculadora estrategia de calculo
     */
    public ServicioNomina(EmpleadoService empleadoService, NominaCalculable calculadora) {
        this.empleadoService = empleadoService;
        this.calculadora = calculadora;
    }

    /**
     * Calcula una nomina personalizada para un empleado.
     *
     * @param empleado empleado a procesar
     * @param periodo periodo del calculo
     * @param horasExtra horas extra del periodo
     * @param bonificacion bonificacion del periodo
     * @return resultado consolidado
     */
    public Nomina calcularNomina(Empleado empleado, String periodo, int horasExtra, BigDecimal bonificacion) {
        return calculadora.calcularNomina(empleado, periodo, horasExtra, bonificacion);
    }

    /**
     * Calcula la nomina usando los valores vigentes almacenados en el empleado.
     *
     * @param empleado empleado a procesar
     * @param periodo periodo del calculo
     * @return resultado consolidado
     */
    public Nomina calcularNominaConDatosRegistrados(Empleado empleado, String periodo) {
        return calcularNomina(empleado, periodo, empleado.getHorasExtra(), empleado.getBonificacion());
    }

    /**
     * Genera la nomina completa de todos los empleados activos.
     *
     * @param periodo periodo a consolidar
     * @return lista de nominas calculadas
     * @throws ArchivoInvalidoException si ocurre un error al consultar
     * empleados
     */
    public List<Nomina> generarNominaEmpresa(String periodo) throws ArchivoInvalidoException {
        List<Nomina> nominas = new ArrayList<>();
        for (Empleado empleado : empleadoService.listarActivos()) {
            nominas.add(calcularNominaConDatosRegistrados(empleado, periodo));
        }
        return nominas;
    }

    /**
     * Construye el resumen patronal de un periodo.
     *
     * @param periodo periodo a consolidar
     * @return resumen patronal del periodo
     * @throws ArchivoInvalidoException si ocurre un error al consultar
     * empleados
     */
    public ResumenPatronal generarResumenPatronal(String periodo) throws ArchivoInvalidoException {
        List<Nomina> nominas = generarNominaEmpresa(periodo);
        BigDecimal totalBruto = BigDecimal.ZERO;
        BigDecimal totalDeducciones = BigDecimal.ZERO;
        BigDecimal totalPatronal = BigDecimal.ZERO;
        BigDecimal totalNeto = BigDecimal.ZERO;

        for (Nomina nomina : nominas) {
            totalBruto = totalBruto.add(nomina.getSalarioBruto());
            totalDeducciones = totalDeducciones.add(nomina.getTotalDeducciones());
            totalPatronal = totalPatronal.add(nomina.getTotalAportesPatronales());
            totalNeto = totalNeto.add(nomina.getSalarioNeto());
        }

        return new ResumenPatronal(periodo, nominas, totalBruto, totalDeducciones, totalPatronal, totalNeto);
    }
}
