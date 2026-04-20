package Logica;

import Entidades.Nomina;
import Entidades.ResumenPatronal;
import Excepciones.ArchivoInvalidoException;
import java.io.File;

/**
 * Define la generacion de reportes formales del sistema.
 */
public interface ReporteService {

    /**
     * Genera el reporte individual de un empleado.
     *
     * @param nomina nomina a documentar
     * @return archivo PDF generado
     * @throws ArchivoInvalidoException si ocurre un error de generacion
     */
    File generarReporteEmpleado(Nomina nomina) throws ArchivoInvalidoException;

    /**
     * Genera el reporte consolidado patronal.
     *
     * @param resumen resumen patronal del periodo
     * @return archivo PDF generado
     * @throws ArchivoInvalidoException si ocurre un error de generacion
     */
    File generarReportePatrono(ResumenPatronal resumen) throws ArchivoInvalidoException;
}
