
package fidebankmiguelduque;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaTransferencia extends VentanaOperacion {

    private JTextField txtDestino;
    private JLabel lblRuta;
    private JLabel lblMontoResumen;

    public VentanaTransferencia(Cuenta cuenta) {
        super("FideBank - Transferencia", "Datos de la transferencia", "Saldo disponible",
                "Monto a transferir (₡):", "Confirmar Transferencia", cuenta);

        txtDestino = new JTextField();
        formulario.add(new JLabel("Cuenta destino:"), 0);
        formulario.add(txtDestino, 1);

        JPanel resumen = new JPanel(new GridLayout(3, 1, 0, 4));
        resumen.setBackground(FONDO);
        resumen.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        resumen.add(crearEtiqueta("Resumen de la operación", 12f, TEXTO_SECUNDARIO));
        lblRuta = crearEtiqueta("", 13f, Color.BLACK);
        lblMontoResumen = crearEtiqueta("", 13f, Color.BLACK);
        resumen.add(lblRuta);
        resumen.add(lblMontoResumen);
        panelAdicional.add(resumen, BorderLayout.CENTER);
        actualizarResumen();

        txtDestino.addActionListener(e -> actualizarResumen());
        txtMonto.addActionListener(e -> actualizarResumen());
    }

    private void actualizarResumen() {
        String destino = txtDestino.getText().trim();
        lblRuta.setText("De: " + cuenta.getNumeroCuenta() + " → A: " + (destino.isEmpty() ? "-" : destino));
        try {
            lblMontoResumen.setText("Monto: " + Formato.moneda(Formato.leerMonto(txtMonto.getText())));
        } catch (MontoInvalidoException ex) {
            lblMontoResumen.setText("Monto: -");
        }
    }

    @Override
    protected boolean validarDatos(double monto) {
        String destino = txtDestino.getText().trim();
        actualizarResumen();
        if (destino.isEmpty()) {
            mostrarErrorOperacion("Debe ingresar la cuenta destino.");
            return false;
        }
        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea transferir " + Formato.moneda(monto) + " a la cuenta " + destino + "?",
                "Confirmar transferencia", JOptionPane.YES_NO_OPTION);
        return respuesta == JOptionPane.YES_OPTION;
    }

    @Override
    protected Transaccion crearTransaccion(double monto) {
        return new Transferencia(monto, cuenta.getNumeroCuenta(), txtDestino.getText().trim());
    }

    @Override
    protected String getMensajeExito() {
        return "Transferencia exitosa";
    }

    @Override
    protected void limpiarCampos() {
        super.limpiarCampos();
        txtDestino.setText("");
        actualizarResumen();
    }
}
