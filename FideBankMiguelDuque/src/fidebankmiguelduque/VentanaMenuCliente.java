
package fidebankmiguelduque;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaMenuCliente extends VentanaBase {

    private Cuenta cuenta;

    public VentanaMenuCliente(Cuenta cuenta) {
        super("FideBank - Menú Principal", 760, 520);
        this.cuenta = cuenta;

        Cliente titular = Banco.buscarClientePorId(cuenta.getIdCliente());
        String nombre = titular != null ? titular.getNombreCompleto() : "";

        JPanel superior = new JPanel(new GridLayout(2, 1, 0, 4));
        superior.setOpaque(false);
        superior.add(crearEtiqueta("Bienvenido, " + nombre, 22f, AZUL_OSCURO));
        superior.add(crearEtiqueta("Cuenta: " + cuenta.getNumeroCuenta(), 12f, TEXTO_SECUNDARIO));

        JPanel botones = new JPanel(new GridLayout(3, 2, 15, 15));
        botones.setOpaque(false);
        JButton btnSaldo = crearBotonPrimario("Consultar Saldo");
        JButton btnRetiro = crearBotonPrimario("Retiro de Fondos");
        JButton btnDeposito = crearBotonPrimario("Depósito");
        JButton btnTransferencia = crearBotonPrimario("Transferencia");
        JButton btnHistorial = crearBotonPrimario("Historial de Transacciones");
        JButton btnCerrarSesion = crearBotonPeligro("Cerrar Sesión");
        botones.add(btnSaldo);
        botones.add(btnRetiro);
        botones.add(btnDeposito);
        botones.add(btnTransferencia);
        botones.add(btnHistorial);
        botones.add(btnCerrarSesion);

        JLabel lblInstruccion = crearEtiqueta("Seleccione la operación que desea realizar.", 12f, TEXTO_SECUNDARIO);
        lblInstruccion.setHorizontalAlignment(JLabel.CENTER);

        btnSaldo.addActionListener(e -> abrirVentana(new VentanaConsultaSaldo(cuenta)));
        btnRetiro.addActionListener(e -> abrirVentana(new VentanaRetiro(cuenta)));
        btnDeposito.addActionListener(e -> abrirVentana(new VentanaDeposito(cuenta)));
        btnTransferencia.addActionListener(e -> abrirVentana(new VentanaTransferencia(cuenta)));
        btnHistorial.addActionListener(e -> abrirVentana(new VentanaHistorial(cuenta)));
        btnCerrarSesion.addActionListener(e -> abrirVentana(new VentanaBienvenida()));

        panelContenido.add(superior, BorderLayout.NORTH);
        panelContenido.add(botones, BorderLayout.CENTER);
        panelContenido.add(lblInstruccion, BorderLayout.SOUTH);
    }

    public Cuenta getCuenta() {
        return cuenta;
    }
}
