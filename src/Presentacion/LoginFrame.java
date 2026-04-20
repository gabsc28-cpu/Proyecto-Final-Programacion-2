package Presentacion;

import Entidades.Usuario;
import Excepciones.ArchivoInvalidoException;
import Excepciones.AutenticacionFallidaException;
import Logica.AutenticacionService;
import Utilidades.TemaVisual;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Ventana de acceso seguro al sistema.
 */
public class LoginFrame extends JFrame {

    private final AutenticacionService autenticacionService;
    private CampoTextoPersonalizado txtUsuario;
    private CampoPasswordPersonalizado txtPassword;
    private BotonPersonalizado btnIngresar;
    private EtiquetaEstado lblEstado;

    /**
     * Crea la ventana de login.
     */
    public LoginFrame() {
        this.autenticacionService = new AutenticacionService();
        initComponents();
        initStyles();
        initEvents();
    }

    /**
     * Construye la interfaz principal.
     */
    private void initComponents() {
        setTitle("PAY SPHERE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBorder(BorderFactory.createEmptyBorder(32, 32, 32, 32));
        root.setBackground(TemaVisual.FONDO_APP);
        setContentPane(root);

        JPanel contenedor = new JPanel(new BorderLayout(24, 0));
        contenedor.setOpaque(false);

        contenedor.add(crearPanelInformativo(), BorderLayout.WEST);
        contenedor.add(crearPanelLogin(), BorderLayout.CENTER);

        root.add(contenedor);
        pack();
        setMinimumSize(new Dimension(1080, 680));
        setLocationRelativeTo(null);
    }

    /**
     * Aplica estilos adicionales a la ventana.
     */
    private void initStyles() {
        getRootPane().setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * Registra eventos de la ventana.
     */
    private void initEvents() {
        btnIngresar.addActionListener(event -> autenticar());
        txtPassword.addActionListener(event -> autenticar());
    }

    /**
     * Crea el hero visual del costado izquierdo.
     *
     * @return panel listo para mostrar
     */
    private JPanel crearPanelInformativo() {
        return new PanelHeroLogin();
    }

    /**
     * Crea el formulario de login.
     *
     * @return panel listo para mostrar
     */
    private JPanel crearPanelLogin() {
        PanelTarjetaPersonalizado panel = new PanelTarjetaPersonalizado();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(34, 34, 34, 34));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.insets = new Insets(0, 0, 14, 0);

        JLabel titulo = new JLabel("Bienvenido");
        titulo.setFont(TemaVisual.FUENTE_TITULO);
        titulo.setForeground(TemaVisual.TEXTO_PRINCIPAL);
        panel.add(titulo, constraints);

        constraints.gridy++;
        JLabel subtitulo = new JLabel("Ingrese sus credenciales para continuar");
        subtitulo.setFont(TemaVisual.FUENTE_NORMAL);
        subtitulo.setForeground(TemaVisual.TEXTO_SECUNDARIO);
        panel.add(subtitulo, constraints);

        constraints.gridy++;
        constraints.insets = new Insets(18, 0, 8, 0);
        panel.add(crearEtiquetaCampo("Usuario"), constraints);

        txtUsuario = new CampoTextoPersonalizado();
        constraints.gridy++;
        constraints.insets = new Insets(0, 0, 14, 0);
        panel.add(txtUsuario, constraints);

        constraints.gridy++;
        constraints.insets = new Insets(8, 0, 8, 0);
        panel.add(crearEtiquetaCampo("Contrasena"), constraints);

        txtPassword = new CampoPasswordPersonalizado();
        constraints.gridy++;
        constraints.insets = new Insets(0, 0, 16, 0);
        panel.add(txtPassword, constraints);

        lblEstado = new EtiquetaEstado();
        constraints.gridy++;
        constraints.insets = new Insets(0, 0, 16, 0);
        panel.add(lblEstado, constraints);

        btnIngresar = new BotonPersonalizado("Ingresar al sistema", BotonPersonalizado.Estilo.PRIMARIO);
        constraints.gridy++;
        constraints.insets = new Insets(4, 0, 0, 0);
        panel.add(btnIngresar, constraints);

        constraints.gridy++;
        constraints.weighty = 1;
        JLabel nota = new JLabel("<html><br>Credenciales administradas desde archivo externo y validadas con hash SHA-256.</html>");
        nota.setFont(TemaVisual.FUENTE_PEQUENA);
        nota.setForeground(TemaVisual.TEXTO_SECUNDARIO);
        panel.add(nota, constraints);

        return panel;
    }

    /**
     * Crea una etiqueta de formulario.
     *
     * @param texto descripcion visible
     * @return etiqueta configurada
     */
    private JLabel crearEtiquetaCampo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(TemaVisual.FUENTE_DESTACADA);
        label.setForeground(TemaVisual.TEXTO_PRINCIPAL);
        return label;
    }

    /**
     * Ejecuta el proceso de autenticacion.
     */
    private void autenticar() {
        limpiarEstadoCampos();
        char[] password = txtPassword.getPassword();
        try {
            Usuario usuario = autenticacionService.autenticar(txtUsuario.getText(), password);
            lblEstado.mostrarExito("Acceso correcto. Cargando dashboard...");
            dispose();
            new DashboardFrame(usuario).setVisible(true);
        } catch (AutenticacionFallidaException | ArchivoInvalidoException exception) {
            marcarErroresLogin();
            lblEstado.mostrarError(exception.getMessage());
        } finally {
            Arrays.fill(password, '\0');
        }
    }

    /**
     * Limpia estados visuales del formulario.
     */
    private void limpiarEstadoCampos() {
        txtUsuario.limpiarEstado();
        txtPassword.limpiarEstado();
        lblEstado.limpiar();
    }

    /**
     * Marca visualmente los campos del login ante un error.
     */
    private void marcarErroresLogin() {
        txtUsuario.mostrarError();
        txtPassword.mostrarError();
    }
}
