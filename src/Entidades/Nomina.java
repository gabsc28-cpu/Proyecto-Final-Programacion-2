package Entidades;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Modela el resultado consolidado del calculo de nomina de un empleado.
 */
public class Nomina {

    private final Empleado empleado;
    private final String periodo;
    private final LocalDate fechaCalculo;
    private final BigDecimal salarioBase;
    private final int horasExtra;
    private final BigDecimal montoHorasExtra;
    private final BigDecimal bonificacion;
    private final BigDecimal salarioBruto;
    private final BigDecimal totalDeducciones;
    private final BigDecimal totalAportesPatronales;
    private final BigDecimal salarioNeto;
    private final List<DetalleNomina> deducciones;
    private final List<DetalleNomina> aportesPatronales;

    /**
     * Crea un resultado de nomina completo.
     *
     * @param empleado empleado calculado
     * @param periodo periodo del proceso
     * @param fechaCalculo fecha de emision
     * @param salarioBase salario base utilizado
     * @param horasExtra horas extra liquidadas
     * @param montoHorasExtra valor monetario por horas extra
     * @param bonificacion bonificacion adicional
     * @param salarioBruto salario bruto del periodo
     * @param totalDeducciones deducciones del trabajador
     * @param totalAportesPatronales aportes patronales del periodo
     * @param salarioNeto salario neto a pagar
     * @param deducciones detalle de deducciones
     * @param aportesPatronales detalle de aportes patronales
     */
    public Nomina(Empleado empleado, String periodo, LocalDate fechaCalculo, BigDecimal salarioBase,
            int horasExtra, BigDecimal montoHorasExtra, BigDecimal bonificacion, BigDecimal salarioBruto,
            BigDecimal totalDeducciones, BigDecimal totalAportesPatronales, BigDecimal salarioNeto,
            List<DetalleNomina> deducciones, List<DetalleNomina> aportesPatronales) {
        this.empleado = empleado;
        this.periodo = periodo;
        this.fechaCalculo = fechaCalculo;
        this.salarioBase = salarioBase;
        this.horasExtra = horasExtra;
        this.montoHorasExtra = montoHorasExtra;
        this.bonificacion = bonificacion;
        this.salarioBruto = salarioBruto;
        this.totalDeducciones = totalDeducciones;
        this.totalAportesPatronales = totalAportesPatronales;
        this.salarioNeto = salarioNeto;
        this.deducciones = new ArrayList<>(deducciones);
        this.aportesPatronales = new ArrayList<>(aportesPatronales);
    }

    /**
     * Obtiene el empleado calculado.
     *
     * @return empleado asociado
     */
    public Empleado getEmpleado() {
        return empleado;
    }

    /**
     * Obtiene el periodo procesado.
     *
     * @return periodo del calculo
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * Obtiene la fecha del calculo.
     *
     * @return fecha de generacion
     */
    public LocalDate getFechaCalculo() {
        return fechaCalculo;
    }

    /**
     * Obtiene el salario base del calculo.
     *
     * @return salario base
     */
    public BigDecimal getSalarioBase() {
        return salarioBase;
    }

    /**
     * Obtiene la cantidad de horas extra reconocidas.
     *
     * @return horas extra liquidadas
     */
    public int getHorasExtra() {
        return horasExtra;
    }

    /**
     * Obtiene el monto monetario por horas extra.
     *
     * @return monto por horas extra
     */
    public BigDecimal getMontoHorasExtra() {
        return montoHorasExtra;
    }

    /**
     * Obtiene la bonificacion considerada en el calculo.
     *
     * @return monto bonificado
     */
    public BigDecimal getBonificacion() {
        return bonificacion;
    }

    /**
     * Obtiene el salario bruto del periodo.
     *
     * @return salario bruto
     */
    public BigDecimal getSalarioBruto() {
        return salarioBruto;
    }

    /**
     * Obtiene el total de deducciones del trabajador.
     *
     * @return total deducido
     */
    public BigDecimal getTotalDeducciones() {
        return totalDeducciones;
    }

    /**
     * Obtiene el total de aportes patronales.
     *
     * @return total patronal
     */
    public BigDecimal getTotalAportesPatronales() {
        return totalAportesPatronales;
    }

    /**
     * Obtiene el salario neto resultante.
     *
     * @return salario neto a pagar
     */
    public BigDecimal getSalarioNeto() {
        return salarioNeto;
    }

    /**
     * Obtiene el detalle de deducciones del trabajador.
     *
     * @return lista inmutable de deducciones
     */
    public List<DetalleNomina> getDeducciones() {
        return Collections.unmodifiableList(deducciones);
    }

    /**
     * Obtiene el detalle de aportes patronales.
     *
     * @return lista inmutable de aportes patronales
     */
    public List<DetalleNomina> getAportesPatronales() {
        return Collections.unmodifiableList(aportesPatronales);
    }

    /**
     * Obtiene el costo total del empleado para la empresa.
     *
     * @return suma de salario bruto y cargas patronales
     */
    public BigDecimal getCostoTotalEmpresa() {
        return salarioBruto.add(totalAportesPatronales);
    }
}
