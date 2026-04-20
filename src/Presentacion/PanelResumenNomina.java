package Presentacion;

import Entidades.DetalleNomina;
import Entidades.Empleado;
import Entidades.Nomina;
import Utilidades.FormatoUtil;
import Utilidades.TemaVisual;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Resume visualmente el resultado de una liquidacion de nomina.
 */
public class PanelResumenNomina extends PanelTarjetaPersonalizado {

    private JLabel lblNombre;
    private JLabel lblCedula;
    private JLabel lblPuesto;
    private JLabel lblPeriodo;
    private JLabel lblBruto;
    private JLabel lblDeducciones;
    private JLabel lblPatronal;
    private JLabel lblNeto;
    private JLabel lblCostoEmpresa;
    private JLabel lblDetalleDeducciones;
    private JLabel lblDetalleAportes;

    /**
     * Crea el panel visual de resumen.
     */
    public PanelResumenNomina() {
        initComponents();
        initStyles();
        initEvents();
        limpiar();
    }

    /**
     * Construye la interfaz principal del resumen.
     */
    private void initComponents() {
        setLayout(new BorderLayout(0, 18));
        setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);
    }

    /**
     * Aplica estilos base del componente.
     */
    private void initStyles() {
    }

    /**
     * Registra eventos del componente.
     */
    private void initEvents() {
    }

    /**
     * Limpia el estado visual del resumen.
     */
    public final void limpiar() {
        lblNombre.setText("-");
        lblCedula.setText("-");
        lblPuesto.setText("-");
        lblPeriodo.setText("-");
        lblBruto.setText("-");
        lblDeducciones.setText("-");
        lblPatronal.setText("-");
        lblNeto.setText("-");
        lblCostoEmpresa.setText("-");
        lblDetalleDeducciones.setText("<html><span style='color:#64748b;'>Seleccione un empleado para comenzar.</span></html>");
        lblDetalleAportes.setText("<html><span style='color:#64748b;'>Seleccione un empleado para comenzar.</span></html>");
    }

    /**
     * Muestra una vista previa basada en el empleado seleccionado.
     *
     * @param empleado empleado actual
     * @param periodo periodo visible
     */
    public void mostrarVistaPrevia(Empleado empleado, String periodo) {
        lblNombre.setText(empleado.getNombreCompleto());
        lblCedula.setText(empleado.getCedula());
        lblPuesto.setText(empleado.getPuesto());
        lblPeriodo.setText(periodo == null || periodo.isBlank() ? "-" : periodo);
        lblBruto.setText("Pendiente");
        lblDeducciones.setText("Pendiente");
        lblPatronal.setText("Pendiente");
        lblNeto.setText("Pendiente");
        lblCostoEmpresa.setText("Pendiente");
        lblDetalleDeducciones.setText("<html><span style='color:#64748b;'>Calcule la nomina para ver el detalle de deducciones.</span></html>");
        lblDetalleAportes.setText("<html><span style='color:#64748b;'>Calcule la nomina para ver el detalle patronal.</span></html>");
    }

    /**
     * Muestra el resultado de una nomina calculada.
     *
     * @param nomina nomina calculada
     */
    public void mostrarNomina(Nomina nomina) {
        lblNombre.setText(nomina.getEmpleado().getNombreCompleto());
        lblCedula.setText(nomina.getEmpleado().getCedula());
        lblPuesto.setText(nomina.getEmpleado().getPuesto());
        lblPeriodo.setText(nomina.getPeriodo());
        lblBruto.setText(FormatoUtil.formatearMoneda(nomina.getSalarioBruto()));
        lblDeducciones.setText(FormatoUtil.formatearMoneda(nomina.getTotalDeducciones()));
        lblPatronal.setText(FormatoUtil.formatearMoneda(nomina.getTotalAportesPatronales()));
        lblNeto.setText(FormatoUtil.formatearMoneda(nomina.getSalarioNeto()));
        lblCostoEmpresa.setText(FormatoUtil.formatearMoneda(nomina.getCostoTotalEmpresa()));
        lblDetalleDeducciones.setText(construirDetalleHtml(nomina.getDeducciones()));
        lblDetalleAportes.setText(construirDetalleHtml(nomina.getAportesPatronales()));
    }

    /**
     * Crea la cabecera del resumen.
     *
     * @return panel de cabecera
     */
    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setOpaque(false);

        JLabel titulo = new JLabel("Resumen de liquidacion");
        titulo.setFont(TemaVisual.FUENTE_SUBTITULO);
        titulo.setForeground(TemaVisual.TEXTO_PRINCIPAL);

        JLabel descripcion = new JLabel("<html>Visualice los totales principales y el detalle financiero sin saturar la pantalla.</html>");
        descripcion.setFont(TemaVisual.FUENTE_NORMAL);
        descripcion.setForeground(TemaVisual.TEXTO_SECUNDARIO);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(descripcion, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea el contenido central del resumen.
     *
     * @return panel con bloques compactos
     */
    private JPanel crearContenido() {
        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));

        contenido.add(crearBloqueEmpleado());
        contenido.add(Box.createVerticalStrut(14));
        contenido.add(crearBloqueTotales());
        contenido.add(Box.createVerticalStrut(14));
        contenido.add(crearBloqueDetalle("Deducciones del trabajador", true));
        contenido.add(Box.createVerticalStrut(12));
        contenido.add(crearBloqueDetalle("Aportes patronales", false));
        return contenido;
    }

    /**
     * Crea el bloque compacto de datos del empleado.
     *
     * @return panel de datos
     */
    private JPanel crearBloqueEmpleado() {
        PanelTarjetaPersonalizado panel = new PanelTarjetaPersonalizado(new Color(248, 250, 252));
        panel.setLayout(new GridLayout(2, 2, 12, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        lblNombre = crearValorLabel();
        lblCedula = crearValorLabel();
        lblPuesto = crearValorLabel();
        lblPeriodo = crearValorLabel();

        panel.add(crearDatoInfo("Empleado", lblNombre));
        panel.add(crearDatoInfo("Periodo", lblPeriodo));
        panel.add(crearDatoInfo("Cedula", lblCedula));
        panel.add(crearDatoInfo("Puesto", lblPuesto));
        return panel;
    }

    /**
     * Crea el bloque compacto de totales.
     *
     * @return panel de totales
     */
    private JPanel crearBloqueTotales() {
        PanelTarjetaPersonalizado panel = new PanelTarjetaPersonalizado(new Color(248, 250, 252));
        panel.setLayout(new BorderLayout(0, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel principal = new JPanel(new BorderLayout(0, 4));
        principal.setOpaque(false);

        JLabel etiquetaNeto = new JLabel("Salario neto");
        etiquetaNeto.setFont(TemaVisual.FUENTE_NORMAL);
        etiquetaNeto.setForeground(TemaVisual.TEXTO_SECUNDARIO);

        lblNeto = new JLabel("-");
        lblNeto.setFont(TemaVisual.FUENTE_TITULO);
        lblNeto.setForeground(TemaVisual.AZUL_PRINCIPAL);

        principal.add(etiquetaNeto, BorderLayout.NORTH);
        principal.add(lblNeto, BorderLayout.CENTER);

        JPanel secundarios = new JPanel(new GridLayout(2, 2, 12, 10));
        secundarios.setOpaque(false);

        lblBruto = crearValorLabel();
        lblDeducciones = crearValorLabel();
        lblPatronal = crearValorLabel();
        lblCostoEmpresa = crearValorLabel();

        secundarios.add(crearDatoInfo("Salario bruto", lblBruto));
        secundarios.add(crearDatoInfo("Total deducciones", lblDeducciones));
        secundarios.add(crearDatoInfo("Total patronal", lblPatronal));
        secundarios.add(crearDatoInfo("Costo empresa", lblCostoEmpresa));

        panel.add(principal, BorderLayout.NORTH);
        panel.add(secundarios, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea un bloque compacto de detalle.
     *
     * @param titulo titulo visible
     * @param deducciones indica si es el bloque de deducciones
     * @return panel de detalle
     */
    private JPanel crearBloqueDetalle(String titulo, boolean deducciones) {
        PanelTarjetaPersonalizado panel = new PanelTarjetaPersonalizado();
        panel.setLayout(new BorderLayout(0, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(TemaVisual.FUENTE_DESTACADA);
        tituloLabel.setForeground(TemaVisual.AZUL_PRINCIPAL);

        JLabel detalle = new JLabel();
        detalle.setFont(TemaVisual.FUENTE_NORMAL);
        detalle.setForeground(TemaVisual.TEXTO_PRINCIPAL);
        detalle.setVerticalAlignment(JLabel.TOP);

        if (deducciones) {
            lblDetalleDeducciones = detalle;
        } else {
            lblDetalleAportes = detalle;
        }

        panel.add(tituloLabel, BorderLayout.NORTH);
        panel.add(detalle, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea un panel de informacion simple.
     *
     * @param etiqueta texto descriptivo
     * @param valor etiqueta de valor
     * @return panel compacto
     */
    private JPanel crearDatoInfo(String etiqueta, JLabel valor) {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setOpaque(false);

        JLabel titulo = new JLabel(etiqueta);
        titulo.setFont(TemaVisual.FUENTE_PEQUENA);
        titulo.setForeground(TemaVisual.TEXTO_SECUNDARIO);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(valor, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea una etiqueta de valor con estilo principal.
     *
     * @return etiqueta configurada
     */
    private JLabel crearValorLabel() {
        JLabel label = new JLabel("-");
        label.setFont(TemaVisual.FUENTE_DESTACADA.deriveFont(15f));
        label.setForeground(TemaVisual.TEXTO_PRINCIPAL);
        return label;
    }

    /**
     * Convierte una lista de detalles a HTML compacto.
     *
     * @param detalles detalles de nomina
     * @return texto HTML listo para una etiqueta
     */
    private String construirDetalleHtml(List<DetalleNomina> detalles) {
        StringBuilder html = new StringBuilder("<html>");
        for (DetalleNomina detalle : detalles) {
            html.append("<div style='margin-bottom:6px;'>")
                    .append("<b>").append(detalle.getConcepto()).append("</b>")
                    .append(" (").append(detalle.getPorcentaje()).append("%)")
                    .append(" - ").append(FormatoUtil.formatearMoneda(detalle.getMonto()))
                    .append("</div>");
        }
        html.append("</html>");
        return html.toString();
    }
}
