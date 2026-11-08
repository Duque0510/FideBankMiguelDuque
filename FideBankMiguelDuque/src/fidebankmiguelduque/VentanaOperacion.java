
package fidebankmiguelduque;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class VentanaOperacion extends VentanaBase {

    protected Cuenta cuenta;
    protected JPanel formulario;
    protected JPanel panelAdicional;
    protected JTextField txtMonto;
    private JLabel lblSaldo;
    private JCheckBox chkComprobante;
    private JLabel lblMensaje;
    private String textoSaldo;

    public VentanaOperacion(String titulo, String tituloTarjeta, String textoSaldo, String textoMonto,
            String textoConfirmar, Cuenta cuenta) {
        super(titulo, 720, 580);
        this.cuenta = cuenta;
        this.textoSaldo = textoSaldo;

        JPanel tarjeta = crearTarjeta(tituloTarjeta);
        JPanel cuerpo = crearPanelTransparente();

        lblSaldo = crearEtiqueta("", 13f, AZUL_OSCURO);
        actualizarSaldo();
        cuerpo.add(lblSaldo, BorderLayout.NORTH);

        formulario = new JPanel(new GridLayout(0, 2, 10, 10));
        formulario.setOpaque(false);
        txtMonto = new JTextField();
        formulario.add(new JLabel(textoMonto));
        formulario.add(txtMonto);
        panelAdicional = crearPanelTransparente();
        JPanel centro = crearPanelTransparente();
        centro.add(formulario, BorderLayout.NORTH);
        centro.add(panelAdicional, BorderLayout.CENTER);
        cuerpo.add(centro, BorderLayout.CENTER);

        chkComprobante = new JCheckBox("Imprimir comprobante", true);
        chkComprobante.setOpaque(false);
        lblMensaje = crearMensaje();
        JButton btnConfirmar = crearBotonPrimario(textoConfirmar);
        JButton btnCancelar = crearBotonSecundario("Cancelar");
        JPanel inferior = crearPanelTransparente();
        inferior.add(chkComprobante, BorderLayout.NORTH);
        inferior.add(crearPanelBotones(btnConfirmar, btnCancelar), BorderLayout.CENTER);
        inferior.add(lblMensaje, BorderLayout.SOUTH);
        cuerpo.add(inferior, BorderLayout.SOUTH);

        tarjeta.add(cuerpo, BorderLayout.CENTER);
        agregarTarjeta(tarjeta, 90);

        btnConfirmar.addActionListener(e -> confirmar());
        btnCancelar.addActionListener(e -> abrirVentana(new VentanaMenuCliente(cuenta)));
        getRootPane().setDefaultButton(btnConfirmar);
    }

    protected abstract Transaccion crearTransaccion(double monto);

    protected abstract String getMensajeExito();

    protected boolean validarDatos(double monto) {
        return true;
    }

    protected void limpiarCampos() {
        txtMonto.setText("");
    }

    protected void mostrarErrorOperacion(String texto) {
        mostrarError(lblMensaje, texto);
    }

    private void confirmar() {
        try {
            double monto = Formato.leerMonto(txtMonto.getText());
            if (!validarDatos(monto)) {
                return;
            }
            Transaccion transaccion = crearTransaccion(monto);
            Banco.realizarTransaccion(transaccion, cuenta);
            if (chkComprobante.isSelected()) {
                abrirVentana(new VentanaComprobante(Comprobante.generarComprobante(transaccion), cuenta));
            } else {
                mostrarExito(lblMensaje, getMensajeExito() + ". Nuevo saldo: " + Formato.moneda(cuenta.consultarSaldo()));
                actualizarSaldo();
                limpiarCampos();
            }
        } catch (BancoException ex) {
            mostrarError(lblMensaje, ex.getMessage());
        } catch (IOException ex) {
            mostrarError(lblMensaje, "Error al guardar los datos: " + ex.getMessage());
        }
    }

    private void actualizarSaldo() {
        lblSaldo.setText(textoSaldo + ": " + Formato.moneda(cuenta.consultarSaldo()));
    }
}
