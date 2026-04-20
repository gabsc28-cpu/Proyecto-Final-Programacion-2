package Logica;

import Entidades.DetalleNomina;
import Entidades.Nomina;
import Entidades.ResumenPatronal;
import Excepciones.ArchivoInvalidoException;
import Utilidades.ArchivoUtil;
import Utilidades.FormatoUtil;
import Utilidades.RutaAplicacion;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Genera reportes PDF profesionales utilizando iText.
 */
public class ReportePdfService implements ReporteService {

    private static final BaseColor COLOR_PRIMARIO = new BaseColor(15, 38, 74);
    private static final BaseColor COLOR_SECUNDARIO = new BaseColor(226, 232, 240);
    private static final BaseColor COLOR_TEXTO = new BaseColor(30, 41, 59);

    /**
     * Genera el reporte individual de un empleado.
     *
     * @param nomina nomina a documentar
     * @return archivo PDF generado
     * @throws ArchivoInvalidoException si ocurre un error de generacion
     */
    @Override
    public File generarReporteEmpleado(Nomina nomina) throws ArchivoInvalidoException {
        String nombreArchivo = String.format(
                "detalle_nomina_%s_%s.pdf",
                ArchivoUtil.normalizarNombreArchivo(nomina.getEmpleado().getId()),
                DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").format(LocalDateTime.now())
        );
        File destino = RutaAplicacion.obtenerRutaReportes().resolve(nombreArchivo).toFile();

        try (FileOutputStream output = new FileOutputStream(destino)) {
            Document document = new Document(PageSize.A4, 40, 40, 40, 40);
            PdfWriter.getInstance(document, output);
            document.open();

            agregarEncabezado(document, "Reporte individual de nomina", "Detalle del colaborador");
            agregarBloqueInformacionEmpleado(document, nomina);
            agregarResumenNomina(document, nomina);
            agregarTablaDetalles(document, "Deducciones del trabajador", nomina.getDeducciones());
            agregarTablaDetalles(document, "Aportes patronales", nomina.getAportesPatronales());
            agregarPie(document);

            document.close();
            return destino;
        } catch (DocumentException | IOException exception) {
            throw new ArchivoInvalidoException("No fue posible generar el PDF del empleado.", exception);
        }
    }

    /**
     * Genera el reporte consolidado patronal.
     *
     * @param resumen resumen patronal del periodo
     * @return archivo PDF generado
     * @throws ArchivoInvalidoException si ocurre un error de generacion
     */
    @Override
    public File generarReportePatrono(ResumenPatronal resumen) throws ArchivoInvalidoException {
        String nombreArchivo = String.format(
                "reporte_patronal_%s_%s.pdf",
                ArchivoUtil.normalizarNombreArchivo(resumen.getPeriodo()),
                DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").format(LocalDateTime.now())
        );
        File destino = RutaAplicacion.obtenerRutaReportes().resolve(nombreArchivo).toFile();

        try (FileOutputStream output = new FileOutputStream(destino)) {
            Document document = new Document(PageSize.A4.rotate(), 36, 36, 36, 36);
            PdfWriter.getInstance(document, output);
            document.open();

            agregarEncabezado(document, "Reporte patronal de nomina", "Resumen empresarial del periodo " + resumen.getPeriodo());
            agregarTotalesPatronales(document, resumen);
            agregarTablaPatronal(document, resumen);
            agregarPie(document);

            document.close();
            return destino;
        } catch (DocumentException | IOException exception) {
            throw new ArchivoInvalidoException("No fue posible generar el PDF patronal.", exception);
        }
    }

    /**
     * Agrega el encabezado institucional del documento.
     *
     * @param document documento destino
     * @param titulo titulo principal
     * @param subtitulo subtitulo explicativo
     * @throws DocumentException si el documento no puede escribirse
     */
    private void agregarEncabezado(Document document, String titulo, String subtitulo) throws DocumentException {
        Paragraph marca = new Paragraph("Sistema empresarial de nomina", crearFuente(11, Font.NORMAL, BaseColor.GRAY));
        marca.setAlignment(Element.ALIGN_LEFT);
        document.add(marca);

        Paragraph tituloParagraph = new Paragraph(titulo, crearFuente(20, Font.BOLD, COLOR_PRIMARIO));
        tituloParagraph.setSpacingBefore(8);
        tituloParagraph.setSpacingAfter(4);
        document.add(tituloParagraph);

        Paragraph subtituloParagraph = new Paragraph(subtitulo, crearFuente(11, Font.NORMAL, COLOR_TEXTO));
        subtituloParagraph.setSpacingAfter(16);
        document.add(subtituloParagraph);
    }

    /**
     * Agrega el bloque descriptivo del empleado.
     *
     * @param document documento destino
     * @param nomina nomina analizada
     * @throws DocumentException si el documento no puede escribirse
     */
    private void agregarBloqueInformacionEmpleado(Document document, Nomina nomina) throws DocumentException {
        PdfPTable tabla = new PdfPTable(new float[]{1.4f, 2.6f, 1.3f, 2.2f});
        tabla.setWidthPercentage(100);
        tabla.setSpacingAfter(18);

        agregarCeldaClaveValor(tabla, "Empleado", nomina.getEmpleado().getNombreCompleto());
        agregarCeldaClaveValor(tabla, "Periodo", nomina.getPeriodo());
        agregarCeldaClaveValor(tabla, "Cedula", nomina.getEmpleado().getCedula());
        agregarCeldaClaveValor(tabla, "Fecha", FormatoUtil.formatearFecha(nomina.getFechaCalculo()));
        agregarCeldaClaveValor(tabla, "Puesto", nomina.getEmpleado().getPuesto());
        agregarCeldaClaveValor(tabla, "Departamento", nomina.getEmpleado().getDepartamento());
        agregarCeldaClaveValor(tabla, "Correo", nomina.getEmpleado().getCorreo());
        agregarCeldaClaveValor(tabla, "Telefono", nomina.getEmpleado().getTelefono());

        document.add(tabla);
    }

    /**
     * Agrega una tabla resumen del calculo individual.
     *
     * @param document documento destino
     * @param nomina nomina analizada
     * @throws DocumentException si el documento no puede escribirse
     */
    private void agregarResumenNomina(Document document, Nomina nomina) throws DocumentException {
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setSpacingAfter(18);
        tabla.setWidths(new float[]{2.3f, 1.2f});

        agregarFilaResumen(tabla, "Salario base", FormatoUtil.formatearMoneda(nomina.getSalarioBase()));
        agregarFilaResumen(tabla, "Horas extra (" + nomina.getHorasExtra() + ")", FormatoUtil.formatearMoneda(nomina.getMontoHorasExtra()));
        agregarFilaResumen(tabla, "Bonificacion", FormatoUtil.formatearMoneda(nomina.getBonificacion()));
        agregarFilaResumen(tabla, "Salario bruto", FormatoUtil.formatearMoneda(nomina.getSalarioBruto()));
        agregarFilaResumen(tabla, "Deducciones", FormatoUtil.formatearMoneda(nomina.getTotalDeducciones()));
        agregarFilaResumen(tabla, "Aportes patronales", FormatoUtil.formatearMoneda(nomina.getTotalAportesPatronales()));
        agregarFilaResumen(tabla, "Salario neto", FormatoUtil.formatearMoneda(nomina.getSalarioNeto()));
        agregarFilaResumen(tabla, "Costo total empresa", FormatoUtil.formatearMoneda(nomina.getCostoTotalEmpresa()));

        document.add(tabla);
    }

    /**
     * Agrega una tabla de detalles monetarios.
     *
     * @param document documento destino
     * @param titulo titulo de la seccion
     * @param detalles lineas a mostrar
     * @throws DocumentException si el documento no puede escribirse
     */
    private void agregarTablaDetalles(Document document, String titulo, List<DetalleNomina> detalles) throws DocumentException {
        Paragraph subtitulo = new Paragraph(titulo, crearFuente(14, Font.BOLD, COLOR_PRIMARIO));
        subtitulo.setSpacingBefore(8);
        subtitulo.setSpacingAfter(8);
        document.add(subtitulo);

        PdfPTable tabla = new PdfPTable(new float[]{3.4f, 1.2f, 1.4f});
        tabla.setWidthPercentage(100);
        tabla.setSpacingAfter(12);

        tabla.addCell(crearEncabezadoTabla("Concepto"));
        tabla.addCell(crearEncabezadoTabla("Porcentaje"));
        tabla.addCell(crearEncabezadoTabla("Monto"));

        for (DetalleNomina detalle : detalles) {
            tabla.addCell(crearCeldaContenido(detalle.getConcepto(), Element.ALIGN_LEFT));
            tabla.addCell(crearCeldaContenido(detalle.getPorcentaje().toPlainString() + "%", Element.ALIGN_CENTER));
            tabla.addCell(crearCeldaContenido(FormatoUtil.formatearMoneda(detalle.getMonto()), Element.ALIGN_RIGHT));
        }

        document.add(tabla);
    }

    /**
     * Agrega totales del reporte patronal.
     *
     * @param document documento destino
     * @param resumen resumen patronal
     * @throws DocumentException si el documento no puede escribirse
     */
    private void agregarTotalesPatronales(Document document, ResumenPatronal resumen) throws DocumentException {
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setSpacingAfter(14);

        tabla.addCell(crearTarjetaPDF("Empleados", String.valueOf(resumen.getNominas().size())));
        tabla.addCell(crearTarjetaPDF("Bruto total", FormatoUtil.formatearMoneda(resumen.getTotalBruto())));
        tabla.addCell(crearTarjetaPDF("Cargas patronales", FormatoUtil.formatearMoneda(resumen.getTotalAportesPatronales())));
        tabla.addCell(crearTarjetaPDF("Neto total", FormatoUtil.formatearMoneda(resumen.getTotalNeto())));

        document.add(tabla);
    }

    /**
     * Agrega la tabla consolidada del reporte patronal.
     *
     * @param document documento destino
     * @param resumen resumen patronal
     * @throws DocumentException si el documento no puede escribirse
     */
    private void agregarTablaPatronal(Document document, ResumenPatronal resumen) throws DocumentException {
        PdfPTable tabla = new PdfPTable(new float[]{1.2f, 2.4f, 1.8f, 1.5f, 1.5f, 1.5f, 1.5f});
        tabla.setWidthPercentage(100);

        tabla.addCell(crearEncabezadoTabla("ID"));
        tabla.addCell(crearEncabezadoTabla("Empleado"));
        tabla.addCell(crearEncabezadoTabla("Departamento"));
        tabla.addCell(crearEncabezadoTabla("Bruto"));
        tabla.addCell(crearEncabezadoTabla("Deducciones"));
        tabla.addCell(crearEncabezadoTabla("Patronal"));
        tabla.addCell(crearEncabezadoTabla("Neto"));

        for (Nomina nomina : resumen.getNominas()) {
            tabla.addCell(crearCeldaContenido(nomina.getEmpleado().getId(), Element.ALIGN_LEFT));
            tabla.addCell(crearCeldaContenido(nomina.getEmpleado().getNombreCompleto(), Element.ALIGN_LEFT));
            tabla.addCell(crearCeldaContenido(nomina.getEmpleado().getDepartamento(), Element.ALIGN_LEFT));
            tabla.addCell(crearCeldaContenido(FormatoUtil.formatearMoneda(nomina.getSalarioBruto()), Element.ALIGN_RIGHT));
            tabla.addCell(crearCeldaContenido(FormatoUtil.formatearMoneda(nomina.getTotalDeducciones()), Element.ALIGN_RIGHT));
            tabla.addCell(crearCeldaContenido(FormatoUtil.formatearMoneda(nomina.getTotalAportesPatronales()), Element.ALIGN_RIGHT));
            tabla.addCell(crearCeldaContenido(FormatoUtil.formatearMoneda(nomina.getSalarioNeto()), Element.ALIGN_RIGHT));
        }

        PdfPCell totalLabel = crearCeldaContenido("Totales del periodo", Element.ALIGN_RIGHT);
        totalLabel.setColspan(3);
        totalLabel.setBackgroundColor(COLOR_SECUNDARIO);
        totalLabel.setPhrase(new Phrase("Totales del periodo", crearFuente(10, Font.BOLD, COLOR_TEXTO)));
        tabla.addCell(totalLabel);
        tabla.addCell(crearCeldaTotal(FormatoUtil.formatearMoneda(resumen.getTotalBruto())));
        tabla.addCell(crearCeldaTotal(FormatoUtil.formatearMoneda(resumen.getTotalDeducciones())));
        tabla.addCell(crearCeldaTotal(FormatoUtil.formatearMoneda(resumen.getTotalAportesPatronales())));
        tabla.addCell(crearCeldaTotal(FormatoUtil.formatearMoneda(resumen.getTotalNeto())));

        document.add(tabla);
    }

    /**
     * Agrega una nota al pie del documento.
     *
     * @param document documento destino
     * @throws DocumentException si el documento no puede escribirse
     */
    private void agregarPie(Document document) throws DocumentException {
        Paragraph pie = new Paragraph();
        pie.setSpacingBefore(12);
        pie.add(new Chunk("Calculo basado en tasas CCSS vigentes desde enero 2026 para patronos y trabajadores.", crearFuente(9, Font.NORMAL, BaseColor.GRAY)));
        document.add(pie);
    }

    /**
     * Agrega una fila clave-valor a una tabla de informacion.
     *
     * @param tabla tabla destino
     * @param clave etiqueta del dato
     * @param valor valor visible
     */
    private void agregarCeldaClaveValor(PdfPTable tabla, String clave, String valor) {
        PdfPCell celdaClave = new PdfPCell(new Phrase(clave, crearFuente(10, Font.BOLD, COLOR_PRIMARIO)));
        celdaClave.setBorderColor(COLOR_SECUNDARIO);
        celdaClave.setPadding(8);
        celdaClave.setBackgroundColor(COLOR_SECUNDARIO);
        tabla.addCell(celdaClave);

        PdfPCell celdaValor = new PdfPCell(new Phrase(valor, crearFuente(10, Font.NORMAL, COLOR_TEXTO)));
        celdaValor.setBorderColor(COLOR_SECUNDARIO);
        celdaValor.setPadding(8);
        tabla.addCell(celdaValor);
    }

    /**
     * Agrega una fila dentro del resumen de nomina.
     *
     * @param tabla tabla destino
     * @param etiqueta nombre del rubro
     * @param valor valor visible
     */
    private void agregarFilaResumen(PdfPTable tabla, String etiqueta, String valor) {
        PdfPCell celdaEtiqueta = new PdfPCell(new Phrase(etiqueta, crearFuente(10, Font.BOLD, COLOR_TEXTO)));
        celdaEtiqueta.setPadding(10);
        celdaEtiqueta.setBorderColor(COLOR_SECUNDARIO);
        celdaEtiqueta.setBackgroundColor(COLOR_SECUNDARIO);
        tabla.addCell(celdaEtiqueta);

        PdfPCell celdaValor = new PdfPCell(new Phrase(valor, crearFuente(10, Font.NORMAL, COLOR_TEXTO)));
        celdaValor.setPadding(10);
        celdaValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
        celdaValor.setBorderColor(COLOR_SECUNDARIO);
        tabla.addCell(celdaValor);
    }

    /**
     * Crea una tarjeta simple para el resumen patronal.
     *
     * @param titulo titulo de la tarjeta
     * @param valor valor destacado
     * @return celda compuesta
     */
    private PdfPCell crearTarjetaPDF(String titulo, String valor) {
        Paragraph contenido = new Paragraph();
        contenido.add(new Chunk(titulo + "\n", crearFuente(9, Font.NORMAL, BaseColor.GRAY)));
        contenido.add(new Chunk(valor, crearFuente(13, Font.BOLD, COLOR_PRIMARIO)));

        PdfPCell celda = new PdfPCell(contenido);
        celda.setPadding(14);
        celda.setBorderColor(COLOR_SECUNDARIO);
        celda.setBackgroundColor(new BaseColor(248, 250, 252));
        return celda;
    }

    /**
     * Crea una celda de encabezado para tablas.
     *
     * @param texto texto del encabezado
     * @return celda formateada
     */
    private PdfPCell crearEncabezadoTabla(String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, crearFuente(10, Font.BOLD, BaseColor.WHITE)));
        celda.setBackgroundColor(COLOR_PRIMARIO);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(8);
        celda.setBorderColor(COLOR_PRIMARIO);
        return celda;
    }

    /**
     * Crea una celda de contenido comun.
     *
     * @param texto texto visible
     * @param alineacion alineacion requerida
     * @return celda formateada
     */
    private PdfPCell crearCeldaContenido(String texto, int alineacion) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, crearFuente(9, Font.NORMAL, COLOR_TEXTO)));
        celda.setHorizontalAlignment(alineacion);
        celda.setPadding(8);
        celda.setBorderColor(COLOR_SECUNDARIO);
        return celda;
    }

    /**
     * Crea una celda de total para el pie de tabla.
     *
     * @param texto valor total
     * @return celda formateada
     */
    private PdfPCell crearCeldaTotal(String texto) {
        PdfPCell celda = crearCeldaContenido(texto, Element.ALIGN_RIGHT);
        celda.setBackgroundColor(COLOR_SECUNDARIO);
        celda.setPhrase(new Phrase(texto, crearFuente(9, Font.BOLD, COLOR_TEXTO)));
        return celda;
    }

    /**
     * Crea una fuente de iText configurada.
     *
     * @param tamano tamano deseado
     * @param estilo estilo de fuente
     * @param color color de texto
     * @return fuente configurada
     */
    private Font crearFuente(int tamano, int estilo, BaseColor color) {
        return FontFactory.getFont(FontFactory.HELVETICA, tamano, estilo, color);
    }
}
