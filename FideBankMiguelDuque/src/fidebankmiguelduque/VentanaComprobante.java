
package fidebankmiguelduque;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class VentanaComprobante extends VentanaBase {

    private Comprobante comprobante;
    private JLabel lblMensaje;

    public VentanaComprobante(Comprobante comprobante, Cuenta cuenta) {
        super("FideBank - Comprobante de Transacción", 640, 600);
        this.comprobante = comprobante;

        lblMensaje = crearMensaje();
        mostrarExito(lblMensaje, "Operación realizada exitosamente.");

        JTextArea txtComprobante = new JTextArea(comprobante.getContenido());
        txtComprobante.setEditable(false);
        txtComprobante.setFocusable(false);
        txtComprobante.setFont(txtComprobante.getFont().deriveFont(13f));
        txtComprobante.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        JPanel tarjeta = crearTarjeta(null);
        tarjeta.add(txtComprobante, BorderLayout.CENTER);

        JButton btnImprimir = crearBotonPrimario("Imprimir Comprobante");
        JButton btnVolver = crearBotonSecundario("Volver al Menú");
        btnImprimir.addActionListener(e -> imprimir(btnImprimir));
        btnVolver.addActionListener(e -> abrirVentana(new VentanaMenuCliente(cuenta)));

        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 90, 20, 90));
        panelContenido.add(lblMensaje, BorderLayout.NORTH);
        panelContenido.add(tarjeta, BorderLayout.CENTER);
        panelContenido.add(crearPanelBotones(btnImprimir, btnVolver), BorderLayout.SOUTH);
    }

    private void imprimir(JButton btnImprimir) {
        try {
            File archivo = comprobante.imprimir();
            JOptionPane.showMessageDialog(this, "Comprobante generado exitosamente.\nArchivo: " + archivo.getPath(),
                    "Comprobante", JOptionPane.INFORMATION_MESSAGE);
            mostrarExito(lblMensaje, "Comprobante guardado en " + archivo.getPath());
            btnImprimir.setEnabled(false);
        } catch (IOException ex) {
            mostrarError(lblMensaje, "Error al guardar el comprobante: " + ex.getMessage());
        }
    }
}
