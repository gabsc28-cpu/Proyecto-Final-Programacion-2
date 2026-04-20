package Presentacion;

import Entidades.Nomina;
import Entidades.ResumenPatronal;
import Excepciones.ArchivoInvalidoException;
import Excepciones.CorreoException;
import Logica.CorreoService;
import Logica.ReporteService;
import Logica.ServicioNomina;
import Utilidades.FormatoUtil;
import Utilidades.TemaVisual;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Panel de reportes patronales e individuales.
 */
public class PanelReportes extends JPanel {

    private final ServicioNomina servicioNomina;
    private final ReporteService reporteService;
    private final CorreoService correoService;

    private CampoTextoPersonalizado txtPeriodo;
    private EtiquetaEstado lblEstado;
    private DefaultTableModel tablaModel;
    private JTable tabla;
    private ResumenPatronal resumenActual;

    /**
     * Crea el panel de reportes.
     *
     * @param servicioNomina servicio de nomina
     * @param reporteService servicio de reportes
     * @param correoService servicio de correo
     */
    public PanelReportes(ServicioNomina servicioNomina, ReporteService reporteService, CorreoService correoService) {
        this.servicioNomina = servicioNomina;
        this.reporteService = reporteService;
        this.correoService = correoService;
        initComponents();
        initStyles();
        initEvents();
        refrescar();
    }

    /**
     * Construye la interfaz del panel.
     */
    private void initComponents() {
        setLayout(new BorderLayout());
        setOpaque(false);

        PanelTarjetaPersonalizado contenedor = new PanelTarjetaPersonalizado();
        contenedor.setLayout(new BorderLayout(0, 16));
        contenedor.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel superior = new JPanel(new BorderLayout(14, 0));
        superior.setOpaque(false);

        JLabel titulo = new JLabel("Consolidado patronal y reportes PDF");
        titulo.setFont(TemaVisual.FUENTE_SUBTITULO);
        titulo.setForeground(TemaVisual.TEXTO_PRINCIPAL);
        superior.add(titulo, BorderLayout.WEST);

        JPanel filtros = new JPanel(new GridLayout(1, 4, 12, 0));
        filtros.setOpaque(false);
        txtPeriodo = new CampoTextoPersonalizado();
        txtPeriodo.setText(FormatoUtil.obtenerPeriodoActual());
        BotonPersonalizado btnActualizar = new BotonPersonalizado("Actualizar resumen", BotonPersonalizado.Estilo.PRIMARIO);
        BotonPersonalizado btnPatrono = new BotonPersonalizado("PDF patrono", BotonPersonalizado.Estilo.SECUNDARIO);
        BotonPersonalizado btnDetalle = new BotonPersonalizado("Detalle seleccionado", BotonPersonalizado.Estilo.SECUNDARIO);
        BotonPersonalizado btnDetalleCorreo = new BotonPersonalizado("Detalle + Correo", BotonPersonalizado.Estilo.SECUNDARIO);

        filtros.add(txtPeriodo);
        filtros.add(btnActualizar);
        filtros.add(btnPatrono);
        filtros.add(btnDetalle);
        superior.add(filtros, BorderLayout.CENTER);

        JPanel accionesExtras = new JPanel(new BorderLayout());
        accionesExtras.setOpaque(false);
        accionesExtras.add(btnDetalleCorreo, BorderLayout.CENTER);
        superior.add(accionesExtras, BorderLayout.EAST);

        tablaModel = new DefaultTableModel(new String[]{
            "ID", "Empleado", "Departamento", "Bruto", "Deducciones", "Patronal", "Neto"
        }, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(tablaModel);
        tabla.setRowHeight(30);
        tabla.setFont(TemaVisual.FUENTE_NORMAL);
        tabla.getTableHeader().setFont(TemaVisual.FUENTE_DESTACADA);

        lblEstado = new EtiquetaEstado();

        contenedor.add(superior, BorderLayout.NORTH);
        contenedor.add(new JScrollPane(tabla), BorderLayout.CENTER);
        contenedor.add(lblEstado, BorderLayout.SOUTH);
        add(contenedor, BorderLayout.CENTER);

        btnActualizar.addActionListener(event -> cargarResumen());
        btnPatrono.addActionListener(event -> exportarPatrono());
        btnDetalle.addActionListener(event -> exportarDetalle(false));
        btnDetalleCorreo.addActionListener(event -> exportarDetalle(true));
    }

    /**
     * Aplica estilos del panel.
     */
    private void initStyles() {
    }

    /**
     * Registra eventos del panel.
     */
    private void initEvents() {
    }

    /**
     * Refresca el panel desde su origen de datos.
     */
    public final void refrescar() {
        txtPeriodo.setText(FormatoUtil.obtenerPeriodoActual());
        cargarResumen();
    }

    /**
     * Consulta y pinta el resumen patronal.
     */
    private void cargarResumen() {
        try {
            resumenActual = servicioNomina.generarResumenPatronal(txtPeriodo.getText().trim());
            tablaModel.setRowCount(0);
            for (Nomina nomina : resumenActual.getNominas()) {
                tablaModel.addRow(new Object[]{
                    nomina.getEmpleado().getId(),
                    nomina.getEmpleado().getNombreCompleto(),
                    nomina.getEmpleado().getDepartamento(),
                    FormatoUtil.formatearMoneda(nomina.getSalarioBruto()),
                    FormatoUtil.formatearMoneda(nomina.getTotalDeducciones()),
                    FormatoUtil.formatearMoneda(nomina.getTotalAportesPatronales()),
                    FormatoUtil.formatearMoneda(nomina.getSalarioNeto())
                });
            }
            lblEstado.mostrarInfo("Resumen del periodo " + resumenActual.getPeriodo() + " actualizado.");
        } catch (ArchivoInvalidoException exception) {
            lblEstado.mostrarError(exception.getMessage());
        }
    }

    /**
     * Exporta el reporte patronal en PDF.
     */
    private void exportarPatrono() {
        if (resumenActual == null) {
            cargarResumen();
        }
        if (resumenActual == null) {
            return;
        }

        try {
            File archivo = reporteService.generarReportePatrono(resumenActual);
            lblEstado.mostrarExito("Reporte patronal generado en: " + archivo.getAbsolutePath());
        } catch (ArchivoInvalidoException exception) {
            lblEstado.mostrarError(exception.getMessage());
        }
    }

    /**
     * Exporta el detalle del empleado seleccionado y opcionalmente lo envia.
     *
     * @param enviarCorreo indica si debe enviarse por correo
     */
    private void exportarDetalle(boolean enviarCorreo) {
        if (resumenActual == null) {
            cargarResumen();
        }
        if (tabla.getSelectedRow() < 0 || resumenActual == null) {
            lblEstado.mostrarError("Seleccione una fila del resumen para generar el detalle.");
            return;
        }

        Nomina nomina = resumenActual.getNominas().get(tabla.getSelectedRow());
        try {
            File archivo = reporteService.generarReporteEmpleado(nomina);
            if (enviarCorreo) {
                correoService.enviarReporte(
                        nomina.getEmpleado().getCorreo(),
                        "Detalle de nomina " + nomina.getPeriodo(),
                        "Adjunto encontrara el detalle de nómina generado desde el modulo de reportes.",
                        archivo
                );
                lblEstado.mostrarExito("Detalle generado y enviado correctamente.");
            } else {
                lblEstado.mostrarExito("Detalle generado en: " + archivo.getAbsolutePath());
            }
        } catch (ArchivoInvalidoException | CorreoException exception) {
            lblEstado.mostrarError(exception.getMessage());
        }
    }
}
