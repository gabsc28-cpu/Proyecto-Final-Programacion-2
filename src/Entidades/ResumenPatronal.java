package Entidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa el consolidado patronal de un periodo de nomina.
 */
public class ResumenPatronal {

    private final String periodo;
    private final List<Nomina> nominas;
    private final BigDecimal totalBruto;
    private final BigDecimal totalDeducciones;
    private final BigDecimal totalAportesPatronales;
    private final BigDecimal totalNeto;

    /**
     * Crea un resumen patronal completo.
     *
     * @param periodo periodo consolidado
     * @param nominas detalle de nominas calculadas
     * @param totalBruto total bruto del periodo
     * @param totalDeducciones total deducido a colaboradores
     * @param totalAportesPatronales total de cargas patronales
     * @param totalNeto total neto a pagar
     */
    public ResumenPatronal(String periodo, List<Nomina> nominas, BigDecimal totalBruto,
            BigDecimal totalDeducciones, BigDecimal totalAportesPatronales, BigDecimal totalNeto) {
        this.periodo = periodo;
        this.nominas = new ArrayList<>(nominas);
        this.totalBruto = totalBruto;
        this.totalDeducciones = totalDeducciones;
        this.totalAportesPatronales = totalAportesPatronales;
        this.totalNeto = totalNeto;
    }

    /**
     * Obtiene el periodo del resumen.
     *
     * @return periodo consolidado
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * Obtiene las nominas del consolidado.
     *
     * @return lista inmutable de nominas
     */
    public List<Nomina> getNominas() {
        return Collections.unmodifiableList(nominas);
    }

    /**
     * Obtiene el total bruto del periodo.
     *
     * @return total bruto
     */
    public BigDecimal getTotalBruto() {
        return totalBruto;
    }

    /**
     * Obtiene el total de deducciones del periodo.
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
     * Obtiene el total neto del periodo.
     *
     * @return total neto
     */
    public BigDecimal getTotalNeto() {
        return totalNeto;
    }
}
