package Presentacion;

import Entidades.Empleado;
import Excepciones.ArchivoInvalidoException;
import Excepciones.ValidacionException;
import Logica.EmpleadoService;
import Utilidades.FormatoUtil;
import Utilidades.TemaVisual;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * Panel de gestion completa de empleados.
 */
public class PanelEmpleados extends JPanel {

    private final EmpleadoService empleadoService;
    private final Runnable callbackRefresco;

    private CampoTextoPersonalizado txtBuscar;
    private CampoTextoPersonalizado txtCedula;
    private CampoTextoPersonalizado txtNombre;
    private CampoTextoPersonalizado txtPuesto;
    private CampoTextoPersonalizado txtDepartamento;
    private CampoTextoPersonalizado txtCorreo;
    private CampoTextoPersonalizado txtTelefono;
    private CampoTextoPersonalizado txtFechaIngreso;
    private CampoTextoPersonalizado txtSalario;
    private CampoTextoPersonalizado txtHorasExtra;
    private CampoTextoPersonalizado txtBonificacion;
    private JCheckBox chkActivo;
    private EtiquetaEstado lblEstado;
    private DefaultTableModel tablaModel;
    private JTable tabla;
    private TableRowSorter<DefaultTableModel> sorter;
    private String empleadoSeleccionadoId;

    /**
     * Crea el panel de empleados.
     *
     * @param empleadoService servicio de empleados
     * @param callbackRefresco accion para refrescar modulos dependientes
     */
    public PanelEmpleados(EmpleadoService empleadoService, Runnable callbackRefresco) {
        this.empleadoService = empleadoService;
        this.callbackRefresco = callbackRefresco;
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

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, crearPanelFormulario(), crearPanelTabla());
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setOpaque(false);
        splitPane.setResizeWeight(0.42);
        add(splitPane, BorderLayout.CENTER);
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
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                filtrarTabla();
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                filtrarTabla();
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                filtrarTabla();
            }
        });
    }

    /**
     * Refresca tabla y formulario.
     */
    public final void refrescar() {
        try {
            List<Empleado> empleados = empleadoService.listarEmpleados();
            tablaModel.setRowCount(0);
            for (Empleado empleado : empleados) {
                tablaModel.addRow(new Object[]{
                    empleado.getId(),
                    empleado.getCedula(),
                    empleado.getNombreCompleto(),
                    empleado.getPuesto(),
                    empleado.getDepartamento(),
                    FormatoUtil.formatearMoneda(empleado.getSalarioBase()),
                    empleado.getHorasExtra(),
                    FormatoUtil.formatearMoneda(empleado.getBonificacion()),
                    empleado.isActivo() ? "Activo" : "Inactivo"
                });
            }
            lblEstado.mostrarInfo("Se cargaron " + empleados.size() + " empleados.");
        } catch (ArchivoInvalidoException exception) {
            lblEstado.mostrarError(exception.getMessage());
        }
    }

    /**
     * Crea el formulario lateral del modulo.
     *
     * @return panel listo para uso
     */
    private Component crearPanelFormulario() {
        PanelTarjetaPersonalizado contenedor = new PanelTarjetaPersonalizado();
        contenedor.setLayout(new BorderLayout());
        contenedor.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JLabel titulo = new JLabel("Formulario maestro de empleados");
        titulo.setFont(TemaVisual.FUENTE_SUBTITULO);
        titulo.setForeground(TemaVisual.TEXTO_PRINCIPAL);
        contenedor.add(titulo, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setOpaque(false);
        formulario.setBorder(BorderFactory.createEmptyBorder(18, 0, 18, 0));

        txtCedula = new CampoTextoPersonalizado();
        txtNombre = new CampoTextoPersonalizado();
        txtPuesto = new CampoTextoPersonalizado();
        txtDepartamento = new CampoTextoPersonalizado();
        txtCorreo = new CampoTextoPersonalizado();
        txtTelefono = new CampoTextoPersonalizado();
        txtFechaIngreso = new CampoTextoPersonalizado();
        txtFechaIngreso.setText(LocalDate.now().toString());
        txtSalario = new CampoTextoPersonalizado();
        txtHorasExtra = new CampoTextoPersonalizado();
        txtBonificacion = new CampoTextoPersonalizado();
        chkActivo = new JCheckBox("Empleado activo");
        chkActivo.setOpaque(false);
        chkActivo.setSelected(true);
        chkActivo.setFont(TemaVisual.FUENTE_NORMAL);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 14, 0);

        formulario.add(crearSeccion("Identificacion", new JComponent[]{
            crearCampoConEtiqueta("Cedula", txtCedula),
            crearCampoConEtiqueta("Nombre completo", txtNombre)
        }), c);

        c.gridy++;
        formulario.add(crearSeccion("Puesto y area", new JComponent[]{
            crearCampoConEtiqueta("Puesto", txtPuesto),
            crearCampoConEtiqueta("Departamento", txtDepartamento)
        }), c);

        c.gridy++;
        formulario.add(crearSeccion("Contacto", new JComponent[]{
            crearCampoConEtiqueta("Correo", txtCorreo),
            crearCampoConEtiqueta("Telefono", txtTelefono)
        }), c);

        c.gridy++;
        formulario.add(crearSeccion("Condiciones laborales", new JComponent[]{
            crearCampoConEtiqueta("Fecha ingreso (yyyy-MM-dd)", txtFechaIngreso),
            crearCampoConEtiqueta("Salario base", txtSalario),
            crearCampoConEtiqueta("Horas extra", txtHorasExtra),
            crearCampoConEtiqueta("Bonificacion", txtBonificacion)
        }), c);

        c.gridy++;
        c.insets = new Insets(0, 0, 10, 0);
        formulario.add(chkActivo, c);

        lblEstado = new EtiquetaEstado();
        c.gridy++;
        formulario.add(lblEstado, c);

        JPanel acciones = new JPanel(new GridLayout(2, 2, 12, 12));
        acciones.setOpaque(false);
        BotonPersonalizado btnGuardar = new BotonPersonalizado("Guardar", BotonPersonalizado.Estilo.PRIMARIO);
        BotonPersonalizado btnActualizar = new BotonPersonalizado("Actualizar", BotonPersonalizado.Estilo.SECUNDARIO);
        BotonPersonalizado btnEliminar = new BotonPersonalizado("Eliminar", BotonPersonalizado.Estilo.PELIGRO);
        BotonPersonalizado btnLimpiar = new BotonPersonalizado("Limpiar", BotonPersonalizado.Estilo.SECUNDARIO);

        btnGuardar.addActionListener(event -> guardarEmpleado());
        btnActualizar.addActionListener(event -> actualizarEmpleado());
        btnEliminar.addActionListener(event -> eliminarEmpleado());
        btnLimpiar.addActionListener(event -> limpiarFormulario());

        acciones.add(btnGuardar);
        acciones.add(btnActualizar);
        acciones.add(btnEliminar);
        acciones.add(btnLimpiar);

        c.gridy++;
        c.insets = new Insets(8, 0, 0, 0);
        formulario.add(acciones, c);

        JScrollPane scrollPane = new JScrollPane(formulario);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contenedor.add(scrollPane, BorderLayout.CENTER);
        return contenedor;
    }

    /**
     * Crea el panel derecho con buscador y tabla.
     *
     * @return panel listo para uso
     */
    private Component crearPanelTabla() {
        PanelTarjetaPersonalizado contenedor = new PanelTarjetaPersonalizado();
        contenedor.setLayout(new BorderLayout(0, 16));
        contenedor.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel superior = new JPanel(new BorderLayout(12, 0));
        superior.setOpaque(false);
        JLabel titulo = new JLabel("Listado de colaboradores");
        titulo.setFont(TemaVisual.FUENTE_SUBTITULO);
        titulo.setForeground(TemaVisual.TEXTO_PRINCIPAL);
        superior.add(titulo, BorderLayout.WEST);

        txtBuscar = new CampoTextoPersonalizado();
        txtBuscar.setToolTipText("Buscar por nombre, cedula o departamento");
        superior.add(txtBuscar, BorderLayout.EAST);

        tablaModel = new DefaultTableModel(new String[]{
            "ID", "Cedula", "Nombre", "Puesto", "Departamento", "Salario", "Horas extra", "Bonificacion", "Estado"
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
        tabla.setSelectionBackground(new java.awt.Color(219, 234, 254));
        tabla.setSelectionForeground(TemaVisual.TEXTO_PRINCIPAL);

        sorter = new TableRowSorter<>(tablaModel);
        tabla.setRowSorter(sorter);

        tabla.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                cargarEmpleadoSeleccionado();
            }
        });

        contenedor.add(superior, BorderLayout.NORTH);
        contenedor.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return contenedor;
    }

    /**
     * Crea un panel de seccion con dos columnas.
     *
     * @param titulo titulo visible
     * @param componentes componentes a ubicar
     * @return panel de seccion
     */
    private JPanel crearSeccion(String titulo, JComponent[] componentes) {
        PanelTarjetaPersonalizado panel = new PanelTarjetaPersonalizado(new java.awt.Color(248, 250, 252));
        panel.setLayout(new BorderLayout(0, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(TemaVisual.FUENTE_DESTACADA);
        tituloLabel.setForeground(TemaVisual.AZUL_PRINCIPAL);
        panel.add(tituloLabel, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 1, 0, 10));
        grid.setOpaque(false);
        for (JComponent componente : componentes) {
            grid.add(componente);
        }
        panel.add(grid, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea un campo con su etiqueta.
     *
     * @param etiqueta nombre visible
     * @param campo componente de entrada
     * @return panel listo para uso
     */
    private JComponent crearCampoConEtiqueta(String etiqueta, JComponent campo) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setOpaque(false);

        JLabel label = new JLabel(etiqueta);
        label.setFont(TemaVisual.FUENTE_NORMAL);
        label.setForeground(TemaVisual.TEXTO_SECUNDARIO);

        panel.add(label, BorderLayout.NORTH);
        panel.add(campo, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Guarda un nuevo empleado.
     */
    private void guardarEmpleado() {
        limpiarValidaciones();
        try {
            Empleado empleado = construirEmpleadoDesdeFormulario(false);
            empleadoService.guardarEmpleado(empleado);
            lblEstado.mostrarExito("Empleado registrado correctamente.");
            limpiarFormulario();
            refrescar();
            callbackRefresco.run();
        } catch (ArchivoInvalidoException | ValidacionException exception) {
            marcarCampoSegunMensaje(exception.getMessage());
            lblEstado.mostrarError(exception.getMessage());
        }
    }

    /**
     * Actualiza el empleado seleccionado.
     */
    private void actualizarEmpleado() {
        if (empleadoSeleccionadoId == null) {
            lblEstado.mostrarError("Seleccione un empleado de la tabla para actualizar.");
            return;
        }

        limpiarValidaciones();
        try {
            Empleado empleado = construirEmpleadoDesdeFormulario(true);
            empleado.setId(empleadoSeleccionadoId);
            empleadoService.actualizarEmpleado(empleado);
            lblEstado.mostrarExito("Empleado actualizado correctamente.");
            refrescar();
            callbackRefresco.run();
        } catch (ArchivoInvalidoException | ValidacionException exception) {
            marcarCampoSegunMensaje(exception.getMessage());
            lblEstado.mostrarError(exception.getMessage());
        }
    }

    /**
     * Elimina el empleado seleccionado.
     */
    private void eliminarEmpleado() {
        if (empleadoSeleccionadoId == null) {
            lblEstado.mostrarError("Seleccione un empleado antes de eliminar.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "Desea eliminar el empleado seleccionado?",
                "Confirmar eliminacion",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            empleadoService.eliminarEmpleado(empleadoSeleccionadoId);
            lblEstado.mostrarExito("Empleado eliminado correctamente.");
            limpiarFormulario();
            refrescar();
            callbackRefresco.run();
        } catch (ArchivoInvalidoException exception) {
            lblEstado.mostrarError(exception.getMessage());
        }
    }

    /**
     * Limpia el formulario y el estado visual.
     */
    private void limpiarFormulario() {
        empleadoSeleccionadoId = null;
        txtCedula.setText("");
        txtNombre.setText("");
        txtPuesto.setText("");
        txtDepartamento.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtFechaIngreso.setText(LocalDate.now().toString());
        txtSalario.setText("");
        txtHorasExtra.setText("0");
        txtBonificacion.setText("0");
        chkActivo.setSelected(true);
        limpiarValidaciones();
        lblEstado.mostrarInfo("Formulario listo para un nuevo registro.");
    }

    /**
     * Construye un empleado a partir del formulario.
     *
     * @param actualizacion indica si se trata de una actualizacion
     * @return empleado construido
     * @throws ValidacionException si los datos no son validos
     */
    private Empleado construirEmpleadoDesdeFormulario(boolean actualizacion) throws ValidacionException {
        Empleado empleado = new Empleado();
        empleado.setCedula(txtCedula.getText().trim());
        empleado.setNombreCompleto(txtNombre.getText().trim());
        empleado.setPuesto(txtPuesto.getText().trim());
        empleado.setDepartamento(txtDepartamento.getText().trim());
        empleado.setCorreo(txtCorreo.getText().trim());
        empleado.setTelefono(txtTelefono.getText().trim());
        empleado.setFechaIngreso(LocalDate.parse(txtFechaIngreso.getText().trim()));
        empleado.setSalarioBase(FormatoUtil.parsearBigDecimal(txtSalario.getText()));
        empleado.setHorasExtra(parsearEntero(txtHorasExtra.getText(), "horas extra"));
        empleado.setBonificacion(FormatoUtil.parsearBigDecimal(txtBonificacion.getText()));
        empleado.setActivo(chkActivo.isSelected());
        if (actualizacion) {
            empleado.setId(empleadoSeleccionadoId);
        }
        return empleado;
    }

    /**
     * Convierte un texto a entero validado.
     *
     * @param valor texto recibido
     * @param nombreCampo nombre del campo
     * @return entero parseado
     * @throws ValidacionException si el valor no es entero
     */
    private int parsearEntero(String valor, String nombreCampo) throws ValidacionException {
        try {
            return Integer.parseInt(valor.trim().isEmpty() ? "0" : valor.trim());
        } catch (NumberFormatException exception) {
            throw new ValidacionException("El campo " + nombreCampo + " debe ser numerico.");
        }
    }

    /**
     * Filtra visualmente la tabla segun el texto ingresado.
     */
    private void filtrarTabla() {
        String texto = txtBuscar.getText();
        if (texto == null || texto.trim().isEmpty()) {
            sorter.setRowFilter(null);
            return;
        }
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
    }

    /**
     * Carga el empleado actualmente seleccionado en el formulario.
     */
    private void cargarEmpleadoSeleccionado() {
        int filaVista = tabla.getSelectedRow();
        if (filaVista < 0) {
            return;
        }

        int filaModelo = tabla.convertRowIndexToModel(filaVista);
        empleadoSeleccionadoId = tablaModel.getValueAt(filaModelo, 0).toString();

        try {
            Empleado empleado = empleadoService.listarEmpleados().stream()
                    .filter(actual -> actual.getId().equalsIgnoreCase(empleadoSeleccionadoId))
                    .findFirst()
                    .orElse(null);

            if (empleado == null) {
                return;
            }

            txtCedula.setText(empleado.getCedula());
            txtNombre.setText(empleado.getNombreCompleto());
            txtPuesto.setText(empleado.getPuesto());
            txtDepartamento.setText(empleado.getDepartamento());
            txtCorreo.setText(empleado.getCorreo());
            txtTelefono.setText(empleado.getTelefono());
            txtFechaIngreso.setText(empleado.getFechaIngreso().toString());
            txtSalario.setText(empleado.getSalarioBase().toPlainString());
            txtHorasExtra.setText(String.valueOf(empleado.getHorasExtra()));
            txtBonificacion.setText(empleado.getBonificacion().toPlainString());
            chkActivo.setSelected(empleado.isActivo());
            lblEstado.mostrarInfo("Empleado cargado para edicion.");
        } catch (ArchivoInvalidoException exception) {
            lblEstado.mostrarError(exception.getMessage());
        }
    }

    /**
     * Limpia el estado de validacion visual del formulario.
     */
    private void limpiarValidaciones() {
        txtCedula.limpiarEstado();
        txtNombre.limpiarEstado();
        txtPuesto.limpiarEstado();
        txtDepartamento.limpiarEstado();
        txtCorreo.limpiarEstado();
        txtTelefono.limpiarEstado();
        txtFechaIngreso.limpiarEstado();
        txtSalario.limpiarEstado();
        txtHorasExtra.limpiarEstado();
        txtBonificacion.limpiarEstado();
    }

    /**
     * Marca el campo mas relacionado con el mensaje de error recibido.
     *
     * @param mensaje detalle de error devuelto por la capa logica
     */
    private void marcarCampoSegunMensaje(String mensaje) {
        String texto = mensaje.toLowerCase();
        if (texto.contains("cedula")) {
            txtCedula.mostrarError();
        } else if (texto.contains("nombre")) {
            txtNombre.mostrarError();
        } else if (texto.contains("puesto")) {
            txtPuesto.mostrarError();
        } else if (texto.contains("departamento")) {
            txtDepartamento.mostrarError();
        } else if (texto.contains("correo")) {
            txtCorreo.mostrarError();
        } else if (texto.contains("telefono")) {
            txtTelefono.mostrarError();
        } else if (texto.contains("fecha")) {
            txtFechaIngreso.mostrarError();
        } else if (texto.contains("salario")) {
            txtSalario.mostrarError();
        } else if (texto.contains("horas")) {
            txtHorasExtra.mostrarError();
        } else if (texto.contains("bonificacion")) {
            txtBonificacion.mostrarError();
        }
    }
}
