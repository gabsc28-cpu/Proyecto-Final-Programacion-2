package Logica;

import Entidades.DetalleNomina;
import Entidades.Empleado;
import Entidades.Nomina;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Calcula la nomina segun la normativa costarricense con tasas vigentes desde
 * enero de 2026.
 */
public class CalculadoraNominaCostaRica extends CalculadoraNominaBase {

    /**
     * Calcula la nomina completa del empleado.
     *
     * @param empleado empleado a calcular
     * @param periodo periodo del calculo
     * @param horasExtra horas extra liquidadas
     * @param bonificacion bonificacion del periodo
     * @return resultado consolidado de nomina
     */
    @Override
    public Nomina calcularNomina(Empleado empleado, String periodo, int horasExtra, BigDecimal bonificacion) {
        BigDecimal montoBonificacion = bonificacion == null ? BigDecimal.ZERO : bonificacion;
        BigDecimal montoHorasExtra = calcularMontoHorasExtra(empleado, Math.max(0, horasExtra));
        BigDecimal salarioBruto = redondear(empleado.getSalarioBase()
                .add(montoHorasExtra)
                .add(montoBonificacion));

        List<DetalleNomina> deducciones = construirDetalles(obtenerPorcentajesTrabajador(), salarioBruto, "TRABAJADOR");
        List<DetalleNomina> aportesPatronales = construirDetalles(obtenerPorcentajesPatronales(), salarioBruto, "PATRONO");

        BigDecimal totalDeducciones = sumarDetalles(deducciones);
        BigDecimal totalAportesPatronales = sumarDetalles(aportesPatronales);
        BigDecimal salarioNeto = redondear(salarioBruto.subtract(totalDeducciones));

        return new Nomina(
                empleado,
                periodo,
                LocalDate.now(),
                empleado.getSalarioBase(),
                horasExtra,
                montoHorasExtra,
                montoBonificacion,
                salarioBruto,
                totalDeducciones,
                totalAportesPatronales,
                salarioNeto,
                deducciones,
                aportesPatronales
        );
    }

    /**
     * Obtiene los porcentajes de deduccion aplicados al trabajador.
     *
     * @return mapa ordenado de deducciones
     */
    private Map<String, BigDecimal> obtenerPorcentajesTrabajador() {
        Map<String, BigDecimal> porcentajes = crearMapaOrdenado();
        porcentajes.put("SEM", new BigDecimal("5.50"));
        porcentajes.put("IVM", new BigDecimal("4.33"));
        porcentajes.put("Banco Popular trabajador", new BigDecimal("1.00"));
        return porcentajes;
    }

    /**
     * Obtiene los porcentajes patronales aplicables.
     *
     * @return mapa ordenado de aportes patronales
     */
    private Map<String, BigDecimal> obtenerPorcentajesPatronales() {
        Map<String, BigDecimal> porcentajes = crearMapaOrdenado();
        porcentajes.put("SEM patrono", new BigDecimal("9.25"));
        porcentajes.put("IVM patrono", new BigDecimal("5.58"));
        porcentajes.put("Banco Popular cuota patronal", new BigDecimal("0.25"));
        porcentajes.put("Asignaciones familiares", new BigDecimal("5.00"));
        porcentajes.put("IMAS", new BigDecimal("0.50"));
        porcentajes.put("INA", new BigDecimal("1.50"));
        porcentajes.put("Banco Popular aporte patronal", new BigDecimal("0.25"));
        porcentajes.put("FCL", new BigDecimal("1.50"));
        porcentajes.put("OPC", new BigDecimal("2.00"));
        porcentajes.put("INS", new BigDecimal("1.00"));
        return porcentajes;
    }
}
