
package fidebankmiguelduque;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VentanaListaClientes extends VentanaBase {

    private JTextField txtCedula;

    public VentanaListaClientes(Usuario administrador) {
        super("FideBank - Clientes Registrados", 820, 560);

        ArrayList<Cliente> clientes = Banco.obtenerClientes();

        JPanel titulo = new JPanel(new BorderLayout());
        titulo.setOpaque(false);
        titulo.add(crearEtiqueta("Directorio de clientes", 16f, AZUL_OSCURO), BorderLayout.WEST);
        titulo.add(crearEtiqueta(clientes.size() + " clientes registrados", 12f, TEXTO_SECUNDARIO), BorderLayout.EAST);

        JPanel busqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        busqueda.setOpaque(false);
        txtCedula = new JTextField(15);
        JButton btnConsultar = crearBotonPrimario("Consultar");
        busqueda.add(new JLabel("Consultar por cédula:"));
        busqueda.add(txtCedula);
        busqueda.add(btnConsultar);

        JPanel superior = crearPanelTransparente();
        superior.add(titulo, BorderLayout.NORTH);
        superior.add(busqueda, BorderLayout.CENTER);

        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Apellidos", "Cédula", "Teléfono", "Cuentas"}, 0);
        for (Cliente c : clientes) {
            modelo.addRow(new Object[]{
                Formato.formatearId(c.getIdCliente()),
                c.getNombre(),
                c.getApellidos(),
                c.getCedula(),
                c.getTelefono(),
                c.obtenerCuentas().size()
            });
        }
        JTable tabla = new JTable(modelo);
        tabla.setDefaultEditor(Object.class, null);
        tabla.setRowHeight(26);
        tabla.setFillsViewportHeight(true);
        tabla.getTableHeader().setReorderingAllowed(false);

        JButton btnVolver = crearBotonSecundario("Volver al Panel");
        btnVolver.addActionListener(e -> abrirVentana(new VentanaPanelAdmin(administrador)));
        btnConsultar.addActionListener(e -> consultarCliente());
        txtCedula.addActionListener(e -> consultarCliente());

        panelContenido.add(superior, BorderLayout.NORTH);
        panelContenido.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panelContenido.add(crearPanelBotones(btnVolver), BorderLayout.SOUTH);
    }

    private void consultarCliente() {
        String cedula = txtCedula.getText().trim();
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una cédula.", "Consulta de cliente",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Cliente cliente = Banco.buscarClientePorCedula(cedula);
            StringBuilder texto = new StringBuilder(cliente.toString());
            ArrayList<Cuenta> cuentas = cliente.obtenerCuentas();
            texto.append("\n\nCuentas asociadas: ").append(cuentas.size());
            for (Cuenta c : cuentas) {
                texto.append("\n  ").append(c.getNumeroCuenta()).append(" - ").append(c.getDescripcionTipo())
                        .append(" (").append(c.getEstado()).append(")");
            }
            JOptionPane.showMessageDialog(this, texto.toString(), "Consulta de cliente",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (ClienteNoExisteException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Consulta de cliente", JOptionPane.ERROR_MESSAGE);
        }
    }
}
