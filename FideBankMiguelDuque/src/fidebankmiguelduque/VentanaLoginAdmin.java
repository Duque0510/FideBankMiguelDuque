
package fidebankmiguelduque;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaLoginAdmin extends VentanaBase {

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JLabel lblMensaje;

    public VentanaLoginAdmin() {
        super("FideBank - Acceso Administrador", 720, 480);

        JPanel tarjeta = crearTarjeta("Autenticación del administrador");
        JPanel cuerpo = crearPanelTransparente();
        cuerpo.add(crearEtiqueta("Ingrese sus credenciales de administración.", 12f, TEXTO_SECUNDARIO),
                BorderLayout.NORTH);

        JPanel formulario = crearPanelFormulario(2);
        txtUsuario = new JTextField();
        txtContrasena = new JPasswordField();
        formulario.add(new JLabel("Usuario:"));
        formulario.add(txtUsuario);
        formulario.add(new JLabel("Contraseña:"));
        formulario.add(txtContrasena);
        cuerpo.add(formulario, BorderLayout.CENTER);

        lblMensaje = crearMensaje();
        JButton btnIniciarSesion = crearBotonPrimario("Iniciar Sesión");
        JButton btnVolver = crearBotonSecundario("Volver");
        JPanel inferior = crearPanelTransparente();
        inferior.add(lblMensaje, BorderLayout.NORTH);
        inferior.add(crearPanelBotones(btnIniciarSesion, btnVolver), BorderLayout.CENTER);
        cuerpo.add(inferior, BorderLayout.SOUTH);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        agregarTarjeta(tarjeta, 100);

        btnIniciarSesion.addActionListener(e -> iniciarSesion());
        btnVolver.addActionListener(e -> abrirVentana(new VentanaBienvenida()));
        getRootPane().setDefaultButton(btnIniciarSesion);
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarError(lblMensaje, "Debe ingresar usuario y contraseña.");
            return;
        }

        Usuario administrador = Banco.getAdministrador();
        if (administrador.iniciarSesion(usuario, contrasena)) {
            abrirVentana(new VentanaPanelAdmin(administrador));
        } else {
            mostrarError(lblMensaje, "Usuario o contraseña incorrectos.");
            txtContrasena.setText("");
        }
    }
}
