package Presentacion;

import Entidades.Empleado;
import Entidades.Nomina;
import Excepciones.ArchivoInvalidoException;
import Excepciones.CorreoException;
import Excepciones.ValidacionException;
import Logica.CorreoService;
import Logica.EmpleadoService;
import Logica.ReporteService;
import Logica.ServicioNomina;
import Utilidades.FormatoUtil;
import Utilidades.TemaVisual;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Panel de calculo individual de nomina.
 */
public class PanelNomina extends JPanel {

    private final EmpleadoService empleadoService;
    private final ServicioNomina servicioNomina;
    private final ReporteService reporteService;
    private final CorreoService correoService;

    private JComboBox<Empleado> cmbEmpleado;
    private CampoTextoPersonalizado txtPeriodo;
    private CampoTextoPersonalizado txtHorasExtra;
    private CampoTextoPersonalizado txtBonificacion;
    private EtiquetaEstado lblEstado;
    private BotonPersonalizado btnPdf;
    private BotonPersonalizado btnPdfCorreo;
    private Nomina nominaActual;
    private PanelResumenNomina panelResumen;

    /**
     * Crea el panel de calculo de nomina.
     *
     * @param empleadoService servicio de empleados
     * @param servicioNomina servicio de nomina
     * @param reporteService servicio de reportes
     * @param correoService servicio de correo
     */
    public PanelNomina(EmpleadoService empleadoService, ServicioNomina servicioNomina,
            ReporteService reporteService, CorreoService correoService) {
        this.empleadoService = empleadoService;
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

        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.add(crearCabeceraVista());
        contenido.add(Box.createVerticalStrut(18));
        contenido.add(crearCuerpoPrincipal());

        JScrollPane scrollPane = new JScrollPane(contenido);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);
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
        cmbEmpleado.addActionListener(event -> cargarDatosEmpleado());
    }

    /**
     * Recarga el combo de empleados activos.
     */
    public final void refrescar() {
        try {
            List<Empleado> empleados = empleadoService.listarActivos();
            DefaultComboBoxModel<Empleado> model = new DefaultComboBoxModel<>();
            for (Empleado empleado : empleados) {
                model.addElement(empleado);
            }
            cmbEmpleado.setModel(model);
            txtPeriodo.setText(FormatoUtil.obtenerPeriodoActual());
            cargarDatosEmpleado();
            lblEstado.mostrarInfo("Seleccione un empleado y calcule su nomina.");
        } catch (ArchivoInvalidoException exception) {
            lblEstado.mostrarError(exception.getMessage());
        }
    }

    /**
     * Carga horas extra y bonificacion del empleado seleccionado.
     */
    private void cargarDatosEmpleado() {
        Empleado empleado = (Empleado) cmbEmpleado.getSelectedItem();
        nominaActual = null;
        btnPdf.setEnabled(false);
        btnPdfCorreo.setEnabled(false);

        if (empleado == null) {
            txtHorasExtra.setText("0");
            txtBonificacion.setText("0");
            panelResumen.limpiar();
            return;
        }

        txtHorasExtra.setText(String.valueOf(empleado.getHorasExtra()));
        txtBonificacion.setText(empleado.getBonificacion().toPlainString());
        panelResumen.mostrarVistaPrevia(empleado, txtPeriodo.getText().trim());
    }

    /**
     * Calcula la nomina actual segun los datos visibles.
     */
    private void calcularNominaActual() {
        Empleado empleado = (Empleado) cmbEmpleado.getSelectedItem();
        if (empleado == null) {
            lblEstado.mostrarError("No hay empleados activos disponibles para calcular.");
            return;
        }

        try {
            int horasExtra = parsearEntero(txtHorasExtra.getText());
            BigDecimal bonificacion = FormatoUtil.parsearBigDecimal(txtBonificacion.getText());
            nominaActual = servicioNomina.calcularNomina(empleado, txtPeriodo.getText().trim(), horasExtra, bonificacion);
            btnPdf.setEnabled(true);
            btnPdfCorreo.setEnabled(true);
            panelResumen.mostrarNomina(nominaActual);
            lblEstado.mostrarExito("Nomina calculada correctamente.");
        } catch (ValidacionException exception) {
            lblEstado.mostrarError(exception.getMessage());
        }
    }

    /**
     * Genera el reporte PDF y opcionalmente lo envia por correo.
     *
     * @param enviarCorreo indica si debe enviarse al colaborador
     */
    private void generarPdf(boolean enviarCorreo) {
        if (nominaActual == null) {
            calcularNominaActual();
        }
        if (nominaActual == null) {
            return;
        }

        try {
            File archivo = reporteService.generarReporteEmpleado(nominaActual);
            if (enviarCorreo) {
                correoService.enviarReporte(
                        nominaActual.getEmpleado().getCorreo(),
                        "Detalle de nomina " + nominaActual.getPeriodo(),
                        "Adjunto encontrara el detalle de su nomina generado por el sistema empresarial.",
                        archivo
                );
                lblEstado.mostrarExito("PDF generado y enviado por correo correctamente.");
            } else {
                lblEstado.mostrarExito("PDF generado en: " + archivo.getAbsolutePath());
            }
        } catch (ArchivoInvalidoException | CorreoException exception) {
            lblEstado.mostrarError(exception.getMessage());
        }
    }

    /**
     * Crea la cabecera superior de la vista.
     *
     * @return panel de cabecera
     */
    private JPanel crearCabeceraVista() {
        PanelTarjetaPersonalizado panel = new PanelTarjetaPersonalizado();
        panel.setLayout(new BorderLayout(0, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 104));

        JLabel titulo = new JLabel("Calculo individual de nomina");
        titulo.setFont(TemaVisual.FUENTE_SUBTITULO.deriveFont(21f));
        titulo.setForeground(TemaVisual.TEXTO_PRINCIPAL);

        JLabel descripcion = new JLabel("<html>Procese el periodo del colaborador, revise el resultado y emita la salida documental desde una vista unificada y sobria.</html>");
        descripcion.setFont(TemaVisual.FUENTE_NORMAL);
        descripcion.setForeground(TemaVisual.TEXTO_SECUNDARIO);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(descripcion, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea el cuerpo principal con formulario y resumen.
     *
     * @return panel principal de la vista
     */
    private JPanel crearCuerpoPrincipal() {
        JPanel cuerpo = new JPanel(new GridBagLayout());
        cuerpo.setOpaque(false);
        cuerpo.setAlignmentX(LEFT_ALIGNMENT);

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;

        c.gridx = 0;
        c.weightx = 0.38;
        c.insets = new Insets(0, 0, 0, 16);
        cuerpo.add(crearPanelEntrada(), c);

        c.gridx = 1;
        c.weightx = 0.62;
        c.insets = new Insets(0, 16, 0, 0);
        cuerpo.add(crearPanelResumen(), c);
        return cuerpo;
    }

    /**
     * Crea la tarjeta de entrada de datos y acciones.
     *
     * @return panel de captura
     */
    private JPanel crearPanelEntrada() {
        PanelTarjetaPersonalizado panel = new PanelTarjetaPersonalizado();
        panel.setLayout(new BorderLayout(0, 18));
        panel.setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));
        panel.setPreferredSize(new Dimension(360, 0));

        JPanel encabezado = new JPanel(new BorderLayout(0, 6));
        encabezado.setOpaque(false);

        JLabel titulo = new JLabel("Datos del periodo");
        titulo.setFont(TemaVisual.FUENTE_SUBTITULO);
        titulo.setForeground(TemaVisual.TEXTO_PRINCIPAL);

        JLabel descripcion = new JLabel("<html>Complete los valores variables del colaborador antes de calcular la liquidacion.</html>");
        descripcion.setFont(TemaVisual.FUENTE_NORMAL);
        descripcion.setForeground(TemaVisual.TEXTO_SECUNDARIO);

        encabezado.add(titulo, BorderLayout.NORTH);
        encabezado.add(descripcion, BorderLayout.CENTER);

        JPanel formulario = new JPanel();
        formulario.setOpaque(false);
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));

        cmbEmpleado = new JComboBox<>();
        txtPeriodo = new CampoTextoPersonalizado();
        txtPeriodo.setText(FormatoUtil.obtenerPeriodoActual());
        txtHorasExtra = new CampoTextoPersonalizado();
        txtBonificacion = new CampoTextoPersonalizado();

        formulario.add(crearCampoConEtiqueta("Empleado", cmbEmpleado));
        formulario.add(Box.createVerticalStrut(14));
        formulario.add(crearCampoConEtiqueta("Periodo", txtPeriodo));
        formulario.add(Box.createVerticalStrut(14));
        formulario.add(crearCampoConEtiqueta("Horas extra", txtHorasExtra));
        formulario.add(Box.createVerticalStrut(14));
        formulario.add(crearCampoConEtiqueta("Bonificacion", txtBonificacion));

        JPanel acciones = new JPanel();
        acciones.setOpaque(false);
        acciones.setLayout(new BoxLayout(acciones, BoxLayout.Y_AXIS));

        BotonPersonalizado btnCalcular = new BotonPersonalizado("Calcular nomina", BotonPersonalizado.Estilo.PRIMARIO);
        btnPdf = new BotonPersonalizado("Generar PDF", BotonPersonalizado.Estilo.SECUNDARIO);
        btnPdfCorreo = new BotonPersonalizado("PDF + correo", BotonPersonalizado.Estilo.SECUNDARIO);
        btnPdf.setEnabled(false);
        btnPdfCorreo.setEnabled(false);

        JPanel filaSecundaria = new JPanel(new GridLayout(1, 2, 12, 0));
        filaSecundaria.setOpaque(false);
        filaSecundaria.add(btnPdf);
        filaSecundaria.add(btnPdfCorreo);

        lblEstado = new EtiquetaEstado();

        acciones.add(btnCalcular);
        acciones.add(Box.createVerticalStrut(12));
        acciones.add(filaSecundaria);
        acciones.add(Box.createVerticalStrut(14));
        acciones.add(lblEstado);

        panel.add(encabezado, BorderLayout.NORTH);
        panel.add(formulario, BorderLayout.CENTER);
        panel.add(acciones, BorderLayout.SOUTH);

        btnCalcular.addActionListener(event -> calcularNominaActual());
        btnPdf.addActionListener(event -> generarPdf(false));
        btnPdfCorreo.addActionListener(event -> generarPdf(true));
        return panel;
    }

    /**
     * Crea la columna de resumen.
     *
     * @return panel de resumen visual
     */
    private JPanel crearPanelResumen() {
        panelResumen = new PanelResumenNomina();
        return panelResumen;
    }

    /**
     * Crea un campo con etiqueta.
     *
     * @param etiqueta nombre visible
     * @param componente componente de entrada
     * @return panel listo para uso
     */
    private JPanel crearCampoConEtiqueta(String etiqueta, Component componente) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));

        JLabel label = new JLabel(etiqueta);
        label.setFont(TemaVisual.FUENTE_NORMAL);
        label.setForeground(TemaVisual.TEXTO_SECUNDARIO);

        panel.add(label, BorderLayout.NORTH);
        panel.add(componente, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Convierte texto a entero validado.
     *
     * @param texto valor ingresado
     * @return entero parseado
     * @throws ValidacionException si el texto no es numerico
     */
    private int parsearEntero(String texto) throws ValidacionException {
        try {
            return Integer.parseInt(texto.trim().isEmpty() ? "0" : texto.trim());
        } catch (NumberFormatException exception) {
            throw new ValidacionException("Las horas extra deben ser un numero entero.");
        }
    }
}
