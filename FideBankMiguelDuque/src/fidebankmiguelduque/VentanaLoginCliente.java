
package fidebankmiguelduque;

import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaLoginCliente extends VentanaBase {

    private JTextField txtCuenta;
    private JPasswordField txtPin;
    private JLabel lblMensaje;

    public VentanaLoginCliente() {
        super("FideBank - Autenticación", 720, 480);

        JPanel tarjeta = crearTarjeta("Acceso a su cuenta");
        JPanel cuerpo = crearPanelTransparente();
        cuerpo.add(crearEtiqueta("Ingrese su número de cuenta y PIN de 4 dígitos", 12f, TEXTO_SECUNDARIO),
                BorderLayout.NORTH);

        JPanel formulario = crearPanelFormulario(2);
        txtCuenta = new JTextField();
        txtPin = new JPasswordField();
        formulario.add(new JLabel("Número de Cuenta:"));
        formulario.add(txtCuenta);
        formulario.add(new JLabel("PIN:"));
        formulario.add(txtPin);
        cuerpo.add(formulario, BorderLayout.CENTER);

        lblMensaje = crearMensaje();
        JButton btnIngresar = crearBotonPrimario("Ingresar");
        JButton btnCancelar = crearBotonSecundario("Cancelar");
        JPanel inferior = crearPanelTransparente();
        inferior.add(lblMensaje, BorderLayout.NORTH);
        inferior.add(crearPanelBotones(btnIngresar, btnCancelar), BorderLayout.CENTER);
        cuerpo.add(inferior, BorderLayout.SOUTH);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        agregarTarjeta(tarjeta, 100);

        btnIngresar.addActionListener(e -> ingresar());
        btnCancelar.addActionListener(e -> abrirVentana(new VentanaBienvenida()));
        getRootPane().setDefaultButton(btnIngresar);
    }

    private void ingresar() {
        String numeroCuenta = txtCuenta.getText().trim();
        String pin = new String(txtPin.getPassword());

        if (numeroCuenta.isEmpty() || pin.isEmpty()) {
            mostrarError(lblMensaje, "Debe ingresar el número de cuenta y el PIN.");
            return;
        }
        if (!pin.matches("\\d{4}")) {
            mostrarError(lblMensaje, "El PIN debe tener exactamente 4 dígitos.");
            return;
        }

        try {
            Cuenta cuenta = Banco.autenticar(numeroCuenta, pin);
            abrirVentana(new VentanaMenuCliente(cuenta));
        } catch (BancoException ex) {
            mostrarError(lblMensaje, ex.getMessage());
            txtPin.setText("");
        } catch (IOException ex) {
            mostrarError(lblMensaje, "Error al guardar los datos: " + ex.getMessage());
        }
    }
}
