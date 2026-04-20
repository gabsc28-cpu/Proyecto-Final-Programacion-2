package Entidades;

import java.math.BigDecimal;

/**
 * Representa una linea de detalle dentro del calculo de nomina.
 */
public class DetalleNomina {

    private String concepto;
    private BigDecimal porcentaje;
    private BigDecimal monto;
    private String tipo;

    /**
     * Crea un detalle vacio.
     */
    public DetalleNomina() {
    }

    /**
     * Crea un detalle con todos sus atributos.
     *
     * @param concepto nombre del rubro
     * @param porcentaje porcentaje aplicado
     * @param monto monto calculado
     * @param tipo tipo del rubro
     */
    public DetalleNomina(String concepto, BigDecimal porcentaje, BigDecimal monto, String tipo) {
        this.concepto = concepto;
        this.porcentaje = porcentaje;
        this.monto = monto;
        this.tipo = tipo;
    }

    /**
     * Obtiene el concepto del detalle.
     *
     * @return nombre del rubro
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * Define el concepto del detalle.
     *
     * @param concepto nombre del rubro
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * Obtiene el porcentaje aplicado.
     *
     * @return porcentaje del rubro
     */
    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    /**
     * Define el porcentaje aplicado.
     *
     * @param porcentaje porcentaje del rubro
     */
    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    /**
     * Obtiene el monto calculado.
     *
     * @return monto monetario
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * Define el monto calculado.
     *
     * @param monto monto monetario
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    /**
     * Obtiene el tipo del rubro.
     *
     * @return tipo del detalle
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define el tipo del rubro.
     *
     * @param tipo tipo del detalle
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
