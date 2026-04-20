package Logica;

import Entidades.DetalleNomina;
import Entidades.Empleado;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementa operaciones comunes para distintas calculadoras de nomina.
 */
public abstract class CalculadoraNominaBase implements NominaCalculable {

    private static final BigDecimal CIEN = new BigDecimal("100");
    private static final BigDecimal HORAS_MENSUALES = new BigDecimal("240");
    private static final BigDecimal FACTOR_HORA_EXTRA = new BigDecimal("1.5");

    /**
     * Calcula el valor monetario de una cantidad de horas extra.
     *
     * @param empleado empleado evaluado
     * @param horasExtra horas extra liquidadas
     * @return monto monetario resultante
     */
    protected BigDecimal calcularMontoHorasExtra(Empleado empleado, int horasExtra) {
        BigDecimal valorHora = empleado.getSalarioBase()
                .divide(HORAS_MENSUALES, 10, RoundingMode.HALF_UP);
        return redondear(valorHora
                .multiply(new BigDecimal(horasExtra))
                .multiply(FACTOR_HORA_EXTRA));
    }

    /**
     * Construye los detalles monetarios a partir de un conjunto de porcentajes.
     *
     * @param porcentajes rubros con su porcentaje asociado
     * @param base monto base sobre el que se calcula
     * @param tipo tipo del detalle
     * @return lista de detalles calculados
     */
    protected List<DetalleNomina> construirDetalles(Map<String, BigDecimal> porcentajes, BigDecimal base, String tipo) {
        List<DetalleNomina> detalles = new ArrayList<>();
        for (Map.Entry<String, BigDecimal> entrada : porcentajes.entrySet()) {
            detalles.add(new DetalleNomina(
                    entrada.getKey(),
                    entrada.getValue(),
                    calcularPorcentaje(base, entrada.getValue()),
                    tipo
            ));
        }
        return detalles;
    }

    /**
     * Calcula un porcentaje sobre un monto base.
     *
     * @param base monto base
     * @param porcentaje porcentaje a aplicar
     * @return resultado redondeado a dos decimales
     */
    protected BigDecimal calcularPorcentaje(BigDecimal base, BigDecimal porcentaje) {
        return redondear(base.multiply(porcentaje).divide(CIEN, 10, RoundingMode.HALF_UP));
    }

    /**
     * Suma los montos de una lista de detalles.
     *
     * @param detalles lista a sumar
     * @return total monetario
     */
    protected BigDecimal sumarDetalles(List<DetalleNomina> detalles) {
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleNomina detalle : detalles) {
            total = total.add(detalle.getMonto());
        }
        return redondear(total);
    }

    /**
     * Crea un mapa ordenado de porcentajes.
     *
     * @return mapa listo para usarse
     */
    protected Map<String, BigDecimal> crearMapaOrdenado() {
        return new LinkedHashMap<>();
    }

    /**
     * Redondea un monto a dos decimales.
     *
     * @param valor valor a redondear
     * @return valor redondeado
     */
    protected BigDecimal redondear(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_UP);
    }
}
