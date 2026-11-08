
package fidebankmiguelduque;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaAperturaCuenta extends VentanaBase {

    private JTextField txtCedula;
    private JLabel lblEstadoCliente;
    private JLabel lblNombreCliente;
    private JLabel lblDatosCliente;
    private JComboBox<String> cmbTipoCuenta;
    private JPasswordField txtPin;
    private JPasswordField txtConfirmarPin;
    private JLabel lblMensaje;
    private Cliente clienteSeleccionado;

    public VentanaAperturaCuenta(Usuario administrador) {
        super("FideBank - Apertura de Cuenta", 760, 640);

        JPanel tarjeta = crearTarjeta("Nueva cuenta bancaria");
        JPanel cuerpo = crearPanelTransparente();

        JPanel busqueda = new JPanel(new BorderLayout(10, 0));
        busqueda.setOpaque(false);
        txtCedula = new JTextField();
        JButton btnBuscar = crearBotonPrimario("Buscar");
        busqueda.add(new JLabel("Cédula del cliente:   "), BorderLayout.WEST);
        busqueda.add(txtCedula, BorderLayout.CENTER);
        busqueda.add(btnBuscar, BorderLayout.EAST);

        JPanel panelCliente = new JPanel(new GridLayout(3, 1, 0, 4));
        panelCliente.setBackground(FONDO);
        panelCliente.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        lblEstadoCliente = crearEtiqueta("Busque un cliente por su número de cédula.", 12f, TEXTO_SECUNDARIO);
        lblNombreCliente = crearEtiqueta(" ", 14f, Color.BLACK);
        lblDatosCliente = crearEtiqueta(" ", 12f, TEXTO_SECUNDARIO);
        panelCliente.add(lblEstadoCliente);
        panelCliente.add(lblNombreCliente);
        panelCliente.add(lblDatosCliente);

        JPanel superior = crearPanelTransparente();
        superior.add(busqueda, BorderLayout.NORTH);
        superior.add(panelCliente, BorderLayout.CENTER);
        cuerpo.add(superior, BorderLayout.NORTH);

        JPanel formulario = crearPanelFormulario(3);
        cmbTipoCuenta = new JComboBox<>(new String[]{"Ahorro", "Corriente"});
        txtPin = new JPasswordField();
        txtConfirmarPin = new JPasswordField();
        formulario.add(new JLabel("Tipo de cuenta:"));
        formulario.add(cmbTipoCuenta);
        formulario.add(new JLabel("PIN (4 dígitos):"));
        formulario.add(txtPin);
        formulario.add(new JLabel("Confirmar PIN:"));
        formulario.add(txtConfirmarPin);
        cuerpo.add(formulario, BorderLayout.CENTER);

        lblMensaje = crearMensaje();
        JButton btnAbrirCuenta = crearBotonPrimario("Abrir Cuenta");
        JButton btnCancelar = crearBotonSecundario("Cancelar");
        JPanel inferior = crearPanelTransparente();
        inferior.add(crearPanelBotones(btnAbrirCuenta, btnCancelar), BorderLayout.NORTH);
        inferior.add(lblMensaje, BorderLayout.CENTER);
        cuerpo.add(inferior, BorderLayout.SOUTH);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        agregarTarjeta(tarjeta, 70);

        btnBuscar.addActionListener(e -> buscarCliente());
        txtCedula.addActionListener(e -> buscarCliente());
        btnAbrirCuenta.addActionListener(e -> abrirCuenta());
        btnCancelar.addActionListener(e -> abrirVentana(new VentanaPanelAdmin(administrador)));
    }

    private void buscarCliente() {
        String cedula = txtCedula.getText().trim();
        if (cedula.isEmpty()) {
            mostrarError(lblMensaje, "Debe ingresar la cédula del cliente.");
            return;
        }
        try {
            clienteSeleccionado = Banco.buscarClientePorCedula(cedula);
            lblEstadoCliente.setText("Cliente encontrado");
            lblEstadoCliente.setForeground(VERDE);
            lblNombreCliente.setText(clienteSeleccionado.getNombreCompleto());
            lblDatosCliente.setText("Cédula: " + clienteSeleccionado.getCedula()
                    + " · Teléfono: " + clienteSeleccionado.getTelefono());
            limpiarMensaje(lblMensaje);
        } catch (ClienteNoExisteException ex) {
            clienteSeleccionado = null;
            lblEstadoCliente.setText("Cliente no encontrado");
            lblEstadoCliente.setForeground(ROJO);
            lblNombreCliente.setText(" ");
            lblDatosCliente.setText(" ");
            mostrarError(lblMensaje, ex.getMessage());
        }
    }

    private void abrirCuenta() {
        if (clienteSeleccionado == null) {
            mostrarError(lblMensaje, "Primero debe buscar un cliente por su cédula.");
            return;
        }

        String pin = new String(txtPin.getPassword());
        String confirmacion = new String(txtConfirmarPin.getPassword());
        if (!pin.matches("\\d{4}")) {
            mostrarError(lblMensaje, "El PIN debe tener exactamente 4 dígitos.");
            return;
        }
        if (!pin.equals(confirmacion)) {
            mostrarError(lblMensaje, "Los PIN ingresados no coinciden.");
            return;
        }

        Cuenta cuenta;
        if (cmbTipoCuenta.getSelectedItem().equals("Ahorro")) {
            cuenta = new CuentaAhorro(clienteSeleccionado.getIdCliente(), pin);
        } else {
            cuenta = new CuentaCorriente(clienteSeleccionado.getIdCliente(), pin);
        }

        try {
            cuenta.abrirCuenta();
            mostrarExito(lblMensaje, "Cuenta abierta exitosamente. Número de cuenta: " + cuenta.getNumeroCuenta());
            txtPin.setText("");
            txtConfirmarPin.setText("");
        } catch (IOException ex) {
            mostrarError(lblMensaje, "Error al guardar los datos: " + ex.getMessage());
        }
    }
}
