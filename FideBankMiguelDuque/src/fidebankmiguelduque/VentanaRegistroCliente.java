
package fidebankmiguelduque;

import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaRegistroCliente extends VentanaBase {

    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtCedula;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JTextField txtDireccion;
    private JLabel lblMensaje;

    public VentanaRegistroCliente(Usuario administrador) {
        super("FideBank - Registro de Cliente", 720, 620);

        JPanel tarjeta = crearTarjeta("Datos del nuevo cliente");
        JPanel cuerpo = crearPanelTransparente();
        cuerpo.add(crearEtiqueta("Complete todos los campos para registrar al cliente.", 12f, TEXTO_SECUNDARIO),
                BorderLayout.NORTH);

        JPanel formulario = crearPanelFormulario(6);
        txtNombre = new JTextField();
        txtApellidos = new JTextField();
        txtCedula = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();
        txtDireccion = new JTextField();
        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Apellidos:"));
        formulario.add(txtApellidos);
        formulario.add(new JLabel("Cédula:"));
        formulario.add(txtCedula);
        formulario.add(new JLabel("Teléfono:"));
        formulario.add(txtTelefono);
        formulario.add(new JLabel("Correo:"));
        formulario.add(txtCorreo);
        formulario.add(new JLabel("Dirección:"));
        formulario.add(txtDireccion);
        cuerpo.add(formulario, BorderLayout.CENTER);

        lblMensaje = crearMensaje();
        JButton btnRegistrar = crearBotonPrimario("Registrar Cliente");
        JButton btnCancelar = crearBotonSecundario("Cancelar");
        JPanel inferior = crearPanelTransparente();
        inferior.add(crearPanelBotones(btnRegistrar, btnCancelar), BorderLayout.NORTH);
        inferior.add(lblMensaje, BorderLayout.CENTER);
        cuerpo.add(inferior, BorderLayout.SOUTH);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        agregarTarjeta(tarjeta, 90);

        btnRegistrar.addActionListener(e -> registrar());
        btnCancelar.addActionListener(e -> abrirVentana(new VentanaPanelAdmin(administrador)));
    }

    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String cedula = txtCedula.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (nombre.isEmpty() || apellidos.isEmpty() || cedula.isEmpty() || telefono.isEmpty()
                || correo.isEmpty() || direccion.isEmpty()) {
            mostrarError(lblMensaje, "Debe completar todos los campos.");
            return;
        }
        if (!correo.contains("@")) {
            mostrarError(lblMensaje, "El correo electrónico no es válido.");
            return;
        }

        try {
            Cliente cliente = new Cliente(nombre, apellidos, cedula, telefono, correo, direccion);
            cliente.registrarCliente();
            mostrarExito(lblMensaje, "Cliente registrado exitosamente. ID: " + Formato.formatearId(cliente.getIdCliente()));
            limpiarCampos();
        } catch (DatosInvalidosException ex) {
            mostrarError(lblMensaje, ex.getMessage());
        } catch (IOException ex) {
            mostrarError(lblMensaje, "Error al guardar los datos: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtCedula.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtNombre.requestFocus();
    }
}
