package Entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa a un colaborador dentro del sistema de nomina.
 */
public class Empleado {

    private String id;
    private String cedula;
    private String nombreCompleto;
    private String puesto;
    private String departamento;
    private String correo;
    private String telefono;
    private LocalDate fechaIngreso;
    private BigDecimal salarioBase;
    private int horasExtra;
    private BigDecimal bonificacion;
    private boolean activo;

    /**
     * Crea un empleado vacio.
     */
    public Empleado() {
    }

    /**
     * Crea un empleado con todos sus atributos.
     *
     * @param id identificador unico
     * @param cedula documento de identidad
     * @param nombreCompleto nombre visible del empleado
     * @param puesto puesto actual
     * @param departamento area funcional
     * @param correo correo corporativo o personal
     * @param telefono telefono de contacto
     * @param fechaIngreso fecha de ingreso
     * @param salarioBase salario base mensual
     * @param horasExtra horas extra registradas
     * @param bonificacion bonificacion adicional del periodo
     * @param activo indicador de estado laboral
     */
    public Empleado(String id, String cedula, String nombreCompleto, String puesto, String departamento,
            String correo, String telefono, LocalDate fechaIngreso, BigDecimal salarioBase, int horasExtra,
            BigDecimal bonificacion, boolean activo) {
        this.id = id;
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.puesto = puesto;
        this.departamento = departamento;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaIngreso = fechaIngreso;
        this.salarioBase = salarioBase;
        this.horasExtra = horasExtra;
        this.bonificacion = bonificacion;
        this.activo = activo;
    }

    /**
     * Obtiene el identificador unico del empleado.
     *
     * @return identificador interno
     */
    public String getId() {
        return id;
    }

    /**
     * Define el identificador unico del empleado.
     *
     * @param id identificador interno
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene la cedula del empleado.
     *
     * @return documento de identidad
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Define la cedula del empleado.
     *
     * @param cedula documento de identidad
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * Obtiene el nombre completo del empleado.
     *
     * @return nombre del colaborador
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Define el nombre completo del empleado.
     *
     * @param nombreCompleto nombre del colaborador
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Obtiene el puesto del empleado.
     *
     * @return puesto actual
     */
    public String getPuesto() {
        return puesto;
    }

    /**
     * Define el puesto del empleado.
     *
     * @param puesto puesto actual
     */
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    /**
     * Obtiene el departamento del empleado.
     *
     * @return departamento actual
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Define el departamento del empleado.
     *
     * @param departamento departamento actual
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * Obtiene el correo del empleado.
     *
     * @return direccion de correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Define el correo del empleado.
     *
     * @param correo direccion de correo
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el telefono del empleado.
     *
     * @return telefono de contacto
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Define el telefono del empleado.
     *
     * @param telefono telefono de contacto
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la fecha de ingreso del empleado.
     *
     * @return fecha de ingreso
     */
    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * Define la fecha de ingreso del empleado.
     *
     * @param fechaIngreso fecha de ingreso
     */
    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * Obtiene el salario base mensual.
     *
     * @return salario base
     */
    public BigDecimal getSalarioBase() {
        return salarioBase;
    }

    /**
     * Define el salario base mensual.
     *
     * @param salarioBase salario base
     */
    public void setSalarioBase(BigDecimal salarioBase) {
        this.salarioBase = salarioBase;
    }

    /**
     * Obtiene las horas extra registradas.
     *
     * @return cantidad de horas extra
     */
    public int getHorasExtra() {
        return horasExtra;
    }

    /**
     * Define las horas extra registradas.
     *
     * @param horasExtra cantidad de horas extra
     */
    public void setHorasExtra(int horasExtra) {
        this.horasExtra = horasExtra;
    }

    /**
     * Obtiene la bonificacion vigente del periodo.
     *
     * @return bonificacion aplicada
     */
    public BigDecimal getBonificacion() {
        return bonificacion;
    }

    /**
     * Define la bonificacion vigente del periodo.
     *
     * @param bonificacion bonificacion aplicada
     */
    public void setBonificacion(BigDecimal bonificacion) {
        this.bonificacion = bonificacion;
    }

    /**
     * Indica si el empleado se encuentra activo.
     *
     * @return {@code true} si el empleado esta activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Define el estado del empleado.
     *
     * @param activo estado actual del colaborador
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Devuelve el texto visible para controles de seleccion.
     *
     * @return nombre amigable del empleado
     */
    @Override
    public String toString() {
        return nombreCompleto + " (" + id + ")";
    }
}
