package Presentacion;

import Entidades.Usuario;
import Logica.CalculadoraNominaCostaRica;
import Logica.CorreoNominaService;
import Logica.CorreoService;
import Logica.DashboardService;
import Logica.EmpleadoService;
import Logica.ReportePdfService;
import Logica.ReporteService;
import Logica.ServicioNomina;
import Utilidades.TemaVisual;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Ventana principal del sistema con dashboard y navegacion lateral.
 */
public class DashboardFrame extends JFrame {

    private final Usuario usuario;
    private final EmpleadoService empleadoService;
    private final ServicioNomina servicioNomina;
    private final DashboardService dashboardService;
    private final ReporteService reporteService;
    private final CorreoService correoService;

    private CardLayout cardLayout;
    private JPanel pnlContenido;
    private JLabel lblTituloVista;
    private JLabel lblSubtituloVista;
    private BotonPersonalizado btnDashboard;
    private BotonPersonalizado btnEmpleados;
    private BotonPersonalizado btnNomina;
    private BotonPersonalizado btnReportes;

    private PanelDashboard panelDashboard;
    private PanelEmpleados panelEmpleados;
    private PanelNomina panelNomina;
    private PanelReportes panelReportes;

    /**
     * Crea la ventana principal para el usuario autenticado.
     *
     * @param usuario usuario autenticado
     */
    public DashboardFrame(Usuario usuario) {
        this.usuario = usuario;
        this.empleadoService = new EmpleadoService();
        this.servicioNomina = new ServicioNomina(empleadoService, new CalculadoraNominaCostaRica());
        this.dashboardService = new DashboardService(empleadoService, servicioNomina);
        this.reporteService = new ReportePdfService();
        this.correoService = new CorreoNominaService();
        initComponents();
        initStyles();
        initEvents();
        mostrarVista("dashboard", "Dashboard ejecutivo", "Vision general de la planilla empresarial");
    }

    /**
     * Construye la interfaz principal.
     */
    private void initComponents() {
        setTitle("Sistema de nomina empresarial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(24, 24));
        root.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        root.setBackground(TemaVisual.FONDO_APP);
        setContentPane(root);

        root.add(crearSidebar(), BorderLayout.WEST);
        root.add(crearContenedorCentral(), BorderLayout.CENTER);

        pack();
        setMinimumSize(new Dimension(1280, 780));
        setLocationRelativeTo(null);
    }

    /**
     * Aplica estilos base a la ventana.
     */
    private void initStyles() {
        getRootPane().setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * Registra eventos de la ventana.
     */
    private void initEvents() {
        btnDashboard.addActionListener(event -> mostrarVista("dashboard", "Dashboard principal", "Vision general de la planilla empresarial"));
        btnEmpleados.addActionListener(event -> mostrarVista("empleados", "Gestion de empleados", "Administracion integral del personal"));
        btnNomina.addActionListener(event -> mostrarVista("nomina", "Calculo de nomina", "Liquidacion individual con detalle financiero"));
        btnReportes.addActionListener(event -> mostrarVista("reportes", "Reportes PDF", "Resumen patronal y reportes individuales"));
    }

    /**
     * Crea el menu lateral de navegacion.
     *
     * @return panel sidebar configurado
     */
    private JPanel crearSidebar() {
        PanelTarjetaPersonalizado panel = new PanelTarjetaPersonalizado(TemaVisual.AZUL_PRINCIPAL);
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(240, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(24, 18, 24, 18));

        JPanel branding = new JPanel();
        branding.setOpaque(false);
        branding.setLayout(new BoxLayout(branding, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("PAY SPHERE");
        logo.setForeground(Color.WHITE);
        logo.setFont(TemaVisual.FUENTE_SUBTITULO);

        JLabel slogan = new JLabel("<html>Control empresarial<br>de planillas</html>");
        slogan.setForeground(new Color(191, 219, 254));
        slogan.setFont(TemaVisual.FUENTE_PEQUENA);
        slogan.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        branding.add(logo);
        branding.add(slogan);

        JPanel menu = new JPanel(new GridLayout(0, 1, 0, 12));
        menu.setOpaque(false);
        menu.setBorder(BorderFactory.createEmptyBorder(26, 0, 26, 0));

        btnDashboard = new BotonPersonalizado("DASHBOARD", BotonPersonalizado.Estilo.MENU);
        btnEmpleados = new BotonPersonalizado("EMPLEADOS", BotonPersonalizado.Estilo.MENU);
        btnNomina = new BotonPersonalizado("NÓMINA", BotonPersonalizado.Estilo.MENU);
        btnReportes = new BotonPersonalizado("REPORTES", BotonPersonalizado.Estilo.MENU);
        BotonPersonalizado btnCerrarSesion = new BotonPersonalizado("Cerrar sesion", BotonPersonalizado.Estilo.SECUNDARIO);
        btnCerrarSesion.addActionListener(event -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        menu.add(btnDashboard);
        menu.add(btnEmpleados);
        menu.add(btnNomina);
        menu.add(btnReportes);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.add(btnCerrarSesion, BorderLayout.SOUTH);

        panel.add(branding, BorderLayout.NORTH);
        panel.add(menu, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Crea el contenedor central con header y vistas dinamicas.
     *
     * @return panel principal del contenido
     */
    private JPanel crearContenedorCentral() {
        JPanel contenedor = new JPanel(new BorderLayout(0, 18));
        contenedor.setOpaque(false);

        contenedor.add(crearHeader(), BorderLayout.NORTH);

        cardLayout = new CardLayout();
        pnlContenido = new JPanel(cardLayout);
        pnlContenido.setOpaque(false);

        panelDashboard = new PanelDashboard(dashboardService);
        panelEmpleados = new PanelEmpleados(empleadoService, this::refrescarModulos);
        panelNomina = new PanelNomina(empleadoService, servicioNomina, reporteService, correoService);
        panelReportes = new PanelReportes(servicioNomina, reporteService, correoService);

        pnlContenido.add(panelDashboard, "dashboard");
        pnlContenido.add(panelEmpleados, "empleados");
        pnlContenido.add(panelNomina, "nomina");
        pnlContenido.add(panelReportes, "reportes");

        contenedor.add(pnlContenido, BorderLayout.CENTER);
        return contenedor;
    }

    /**
     * Crea el header superior con informacion contextual.
     *
     * @return header principal
     */
    private JPanel crearHeader() {
        PanelTarjetaPersonalizado panel = new PanelTarjetaPersonalizado();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        JPanel infoVista = new JPanel();
        infoVista.setOpaque(false);
        infoVista.setLayout(new BoxLayout(infoVista, BoxLayout.Y_AXIS));

        lblTituloVista = new JLabel("Dashboard ejecutivo");
        lblTituloVista.setFont(TemaVisual.FUENTE_TITULO);
        lblTituloVista.setForeground(TemaVisual.TEXTO_PRINCIPAL);

        lblSubtituloVista = new JLabel("Vision general de la planilla empresarial");
        lblSubtituloVista.setFont(TemaVisual.FUENTE_NORMAL);
        lblSubtituloVista.setForeground(TemaVisual.TEXTO_SECUNDARIO);

        infoVista.add(lblTituloVista);
        infoVista.add(Box.createVerticalStrut(4));
        infoVista.add(lblSubtituloVista);

        JPanel infoUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        infoUsuario.setOpaque(false);

        PanelTarjetaPersonalizado chip = new PanelTarjetaPersonalizado(new Color(239, 246, 255));
        chip.setLayout(new BoxLayout(chip, BoxLayout.Y_AXIS));
        chip.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        JLabel lblNombre = new JLabel(usuario.getNombreMostrar());
        lblNombre.setFont(TemaVisual.FUENTE_DESTACADA);
        lblNombre.setForeground(TemaVisual.AZUL_PRINCIPAL);

        JLabel lblMeta = new JLabel(usuario.getRol() + " | " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lblMeta.setFont(TemaVisual.FUENTE_PEQUENA);
        lblMeta.setForeground(TemaVisual.TEXTO_SECUNDARIO);

        chip.add(lblNombre);
        chip.add(lblMeta);
        infoUsuario.add(chip);

        panel.add(infoVista, BorderLayout.WEST);
        panel.add(infoUsuario, BorderLayout.EAST);
        return panel;
    }

    /**
     * Cambia la vista central mostrada.
     *
     * @param clave identificador de la vista
     * @param titulo titulo del header
     * @param subtitulo subtitulo del header
     */
    private void mostrarVista(String clave, String titulo, String subtitulo) {
        lblTituloVista.setText(titulo);
        lblSubtituloVista.setText(subtitulo);
        cardLayout.show(pnlContenido, clave);
        actualizarMenu(clave);
        refrescarVistaActual(clave);
    }

    /**
     * Actualiza el estado visual de los botones del menu.
     *
     * @param clave vista activa
     */
    private void actualizarMenu(String clave) {
        btnDashboard.establecerSeleccionado("dashboard".equals(clave));
        btnEmpleados.establecerSeleccionado("empleados".equals(clave));
        btnNomina.establecerSeleccionado("nomina".equals(clave));
        btnReportes.establecerSeleccionado("reportes".equals(clave));
    }

    /**
     * Refresca los paneles dependientes de datos.
     */
    private void refrescarModulos() {
        panelDashboard.refrescar();
        panelNomina.refrescar();
        panelReportes.refrescar();
    }

    /**
     * Refresca la vista que acaba de mostrarse.
     *
     * @param clave vista activa
     */
    private void refrescarVistaActual(String clave) {
        switch (clave) {
            case "dashboard" -> panelDashboard.refrescar();
            case "empleados" -> panelEmpleados.refrescar();
            case "nomina" -> panelNomina.refrescar();
            case "reportes" -> panelReportes.refrescar();
            default -> {
            }
        }
    }
}
