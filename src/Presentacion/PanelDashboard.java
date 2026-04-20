package Presentacion;

import Entidades.Empleado;
import Entidades.ResumenPatronal;
import Excepciones.ArchivoInvalidoException;
import Logica.DashboardService;
import Utilidades.FormatoUtil;
import Utilidades.TemaVisual;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 * Panel principal de indicadores ejecutivos.
 */
public class PanelDashboard extends JPanel {

    private final DashboardService dashboardService;
    private JLabel lblEmpleadosActivos;
    private JLabel lblBruto;
    private JLabel lblPatronal;
    private JLabel lblNeto;
    private JLabel lblPromedio;
    private EtiquetaEstado lblEstado;
    private DefaultTableModel tablaModel;

    /**
     * Crea el panel de dashboard.
     *
     * @param dashboardService servicio de indicadores
     */
    public PanelDashboard(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
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

        DashboardScrollContainer contenido = new DashboardScrollContainer();
        contenido.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        contenido.add(crearSeccionMetricas());
        contenido.add(Box.createVerticalStrut(18));
        contenido.add(crearSeccionResumen());
        contenido.add(Box.createVerticalStrut(18));
        contenido.add(crearSeccionTabla());

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
    }

    /**
     * Refresca los datos visibles del dashboard.
     */
    public final void refrescar() {
        try {
            ResumenPatronal resumen = dashboardService.obtenerResumenPeriodoActual();
            List<Empleado> recientes = dashboardService.listarIngresosRecientes();

            lblEmpleadosActivos.setText(String.valueOf(dashboardService.contarEmpleadosActivos()));
            lblBruto.setText(FormatoUtil.formatearMoneda(resumen.getTotalBruto()));
            lblPatronal.setText(FormatoUtil.formatearMoneda(resumen.getTotalAportesPatronales()));
            lblNeto.setText(FormatoUtil.formatearMoneda(resumen.getTotalNeto()));
            lblPromedio.setText(FormatoUtil.formatearMoneda(dashboardService.obtenerSalarioPromedio()));
            lblEstado.mostrarInfo("Resumen actualizado para el periodo " + resumen.getPeriodo() + ".");

            tablaModel.setRowCount(0);
            for (Empleado empleado : recientes) {
                tablaModel.addRow(new Object[]{
                    empleado.getId(),
                    empleado.getNombreCompleto(),
                    empleado.getDepartamento(),
                    FormatoUtil.formatearFecha(empleado.getFechaIngreso()),
                    FormatoUtil.formatearMoneda(empleado.getSalarioBase())
                });
            }
        } catch (ArchivoInvalidoException exception) {
            lblEstado.mostrarError(exception.getMessage());
        }
    }

    /**
     * Crea la seccion superior de metricas.
     *
     * @return panel de metricas
     */
    private JPanel crearSeccionMetricas() {
        JPanel seccion = new JPanel();
        seccion.setOpaque(false);
        seccion.setLayout(new BoxLayout(seccion, BoxLayout.Y_AXIS));
        seccion.setAlignmentX(LEFT_ALIGNMENT);

        JLabel titulo = new JLabel("Metricas principales");
        titulo.setFont(TemaVisual.FUENTE_SUBTITULO);
        titulo.setForeground(TemaVisual.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("Vista general del comportamiento actual de la nomina empresarial.");
        subtitulo.setFont(TemaVisual.FUENTE_NORMAL);
        subtitulo.setForeground(TemaVisual.TEXTO_SECUNDARIO);
        subtitulo.setAlignmentX(LEFT_ALIGNMENT);

        JPanel grid = new JPanel(new GridLayout(2, 2, 18, 18));
        grid.setOpaque(false);
        grid.setAlignmentX(LEFT_ALIGNMENT);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 360));

        lblEmpleadosActivos = new JLabel("0");
        lblBruto = new JLabel("CRC 0");
        lblPatronal = new JLabel("CRC 0");
        lblNeto = new JLabel("CRC 0");

        grid.add(crearCardIndicador("Empleados activos", lblEmpleadosActivos));
        grid.add(crearCardIndicador("Nomina bruta estimada", lblBruto));
        grid.add(crearCardIndicador("Cargas patronales", lblPatronal));
        grid.add(crearCardIndicador("Nomina neta estimada", lblNeto));

        seccion.add(titulo);
        seccion.add(Box.createVerticalStrut(6));
        seccion.add(subtitulo);
        seccion.add(Box.createVerticalStrut(16));
        seccion.add(grid);
        return seccion;
    }

    /**
     * Crea la seccion intermedia de resumen ejecutivo.
     *
     * @return panel de resumen
     */
    private JPanel crearSeccionResumen() {
        PanelTarjetaPersonalizado panelInfo = new PanelTarjetaPersonalizado();
        panelInfo.setLayout(new BorderLayout(0, 18));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        panelInfo.setAlignmentX(LEFT_ALIGNMENT);
        panelInfo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 240));

        JPanel cabecera = new JPanel(new BorderLayout(0, 8));
        cabecera.setOpaque(false);

        JLabel tituloInfo = new JLabel("Indicadores del periodo");
        tituloInfo.setFont(TemaVisual.FUENTE_SUBTITULO);
        tituloInfo.setForeground(TemaVisual.TEXTO_PRINCIPAL);

        JLabel descripcion = new JLabel("<html>El sistema estima automaticamente salario bruto, deducciones CCSS, cargas patronales y salario neto con base en la normativa costarricense vigente desde enero de 2026.</html>");
        descripcion.setFont(TemaVisual.FUENTE_NORMAL);
        descripcion.setForeground(TemaVisual.TEXTO_SECUNDARIO);

        cabecera.add(tituloInfo, BorderLayout.NORTH);
        cabecera.add(descripcion, BorderLayout.CENTER);

        JPanel contenido = new JPanel(new GridLayout(1, 2, 18, 0));
        contenido.setOpaque(false);

        PanelTarjetaPersonalizado promedioCard = new PanelTarjetaPersonalizado(new java.awt.Color(248, 250, 252));
        promedioCard.setLayout(new BorderLayout(0, 8));
        promedioCard.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JLabel etiquetaPromedio = new JLabel("Salario promedio");
        etiquetaPromedio.setFont(TemaVisual.FUENTE_NORMAL);
        etiquetaPromedio.setForeground(TemaVisual.TEXTO_SECUNDARIO);

        lblPromedio = new JLabel("CRC 0");
        lblPromedio.setFont(TemaVisual.FUENTE_TITULO);
        lblPromedio.setForeground(TemaVisual.AZUL_PRINCIPAL);

        promedioCard.add(etiquetaPromedio, BorderLayout.NORTH);
        promedioCard.add(lblPromedio, BorderLayout.CENTER);

        PanelTarjetaPersonalizado estadoCard = new PanelTarjetaPersonalizado(new java.awt.Color(248, 250, 252));
        estadoCard.setLayout(new BorderLayout(0, 8));
        estadoCard.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JLabel etiquetaEstado = new JLabel("Estado de actualizacion");
        etiquetaEstado.setFont(TemaVisual.FUENTE_NORMAL);
        etiquetaEstado.setForeground(TemaVisual.TEXTO_SECUNDARIO);

        lblEstado = new EtiquetaEstado();
        lblEstado.setVerticalAlignment(SwingConstants.TOP);

        estadoCard.add(etiquetaEstado, BorderLayout.NORTH);
        estadoCard.add(lblEstado, BorderLayout.CENTER);

        contenido.add(promedioCard);
        contenido.add(estadoCard);

        panelInfo.add(cabecera, BorderLayout.NORTH);
        panelInfo.add(contenido, BorderLayout.CENTER);
        return panelInfo;
    }

    /**
     * Crea la seccion inferior con la tabla de ingresos recientes.
     *
     * @return panel con tabla
     */
    private JPanel crearSeccionTabla() {
        PanelTarjetaPersonalizado panelTabla = new PanelTarjetaPersonalizado();
        panelTabla.setLayout(new BorderLayout(0, 14));
        panelTabla.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        panelTabla.setAlignmentX(LEFT_ALIGNMENT);
        panelTabla.setMaximumSize(new Dimension(Integer.MAX_VALUE, 420));

        JLabel tituloTabla = new JLabel("Ultimos ingresos registrados");
        tituloTabla.setFont(TemaVisual.FUENTE_SUBTITULO);
        tituloTabla.setForeground(TemaVisual.TEXTO_PRINCIPAL);
        panelTabla.add(tituloTabla, BorderLayout.NORTH);

        tablaModel = new DefaultTableModel(new String[]{"ID", "Empleado", "Departamento", "Ingreso", "Salario base"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        JTable tabla = new JTable(tablaModel);
        tabla.setRowHeight(30);
        tabla.setFont(TemaVisual.FUENTE_NORMAL);
        tabla.getTableHeader().setFont(TemaVisual.FUENTE_DESTACADA);
        tabla.setFillsViewportHeight(true);

        JScrollPane tablaScroll = new JScrollPane(tabla);
        tablaScroll.setBorder(BorderFactory.createEmptyBorder());
        tablaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tablaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelTabla.add(tablaScroll, BorderLayout.CENTER);
        return panelTabla;
    }

    /**
     * Crea una tarjeta de indicador simple.
     *
     * @param titulo etiqueta del indicador
     * @param valorLabel etiqueta donde se coloca el valor
     * @return tarjeta lista para uso
     */
    private JPanel crearCardIndicador(String titulo, JLabel valorLabel) {
        PanelTarjetaPersonalizado panel = new PanelTarjetaPersonalizado();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(TemaVisual.FUENTE_NORMAL);
        tituloLabel.setForeground(TemaVisual.TEXTO_SECUNDARIO);

        valorLabel.setFont(TemaVisual.FUENTE_TITULO);
        valorLabel.setForeground(TemaVisual.AZUL_PRINCIPAL);

        panel.add(tituloLabel, BorderLayout.NORTH);
        panel.add(valorLabel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Contenedor vertical que sigue el ancho del viewport para evitar scroll
     * horizontal.
     */
    private static class DashboardScrollContainer extends JPanel implements Scrollable {

        /**
         * Crea el contenedor vertical del dashboard.
         */
        DashboardScrollContainer() {
            setOpaque(false);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        }

        /**
         * Obtiene el tamano preferido del viewport.
         *
         * @return dimension preferida
         */
        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        /**
         * Define el incremento por unidad del scroll.
         *
         * @param visibleRect area visible
         * @param orientation orientacion del scroll
         * @param direction direccion del desplazamiento
         * @return incremento recomendado
         */
        @Override
        public int getScrollableUnitIncrement(java.awt.Rectangle visibleRect, int orientation, int direction) {
            return 16;
        }

        /**
         * Define el incremento por bloque del scroll.
         *
         * @param visibleRect area visible
         * @param orientation orientacion del scroll
         * @param direction direccion del desplazamiento
         * @return incremento recomendado
         */
        @Override
        public int getScrollableBlockIncrement(java.awt.Rectangle visibleRect, int orientation, int direction) {
            return 64;
        }

        /**
         * Indica que el contenedor debe ajustarse al ancho del viewport.
         *
         * @return {@code true} para evitar scroll horizontal
         */
        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        /**
         * Indica que el contenedor puede crecer verticalmente segun contenido.
         *
         * @return {@code false} para habilitar scroll vertical
         */
        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }
}
