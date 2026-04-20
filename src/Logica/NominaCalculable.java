package Logica;

import Entidades.Empleado;
import Entidades.Nomina;
import java.math.BigDecimal;

/**
 * Define el comportamiento de calculo de nomina para distintas normativas.
 */
public interface NominaCalculable {

    /**
     * Calcula la nomina de un empleado para un periodo concreto.
     *
     * @param empleado empleado a calcular
     * @param periodo periodo del calculo
     * @param horasExtra horas extra del periodo
     * @param bonificacion bonificacion del periodo
     * @return resultado consolidado de nomina
     */
    Nomina calcularNomina(Empleado empleado, String periodo, int horasExtra, BigDecimal bonificacion);
}
