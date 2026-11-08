
package fidebankmiguelduque;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class VentanaConsultaSaldo extends VentanaBase {

    public VentanaConsultaSaldo(Cuenta cuenta) {
        super("FideBank - Consulta de Saldo", 720, 480);

        Cliente titular = Banco.buscarClientePorId(cuenta.getIdCliente());
        String nombre = titular != null ? titular.getNombreCompleto() : "";

        JPanel tarjeta = crearTarjeta("Información de la cuenta");

        JPanel datos = new JPanel(new GridLayout(3, 1, 0, 6));
        datos.setOpaque(false);
        datos.add(crearEtiqueta("Titular: " + nombre, 13f, TEXTO_SECUNDARIO));
        datos.add(crearEtiqueta("Cuenta: " + cuenta.getNumeroCuenta(), 13f, TEXTO_SECUNDARIO));
        datos.add(crearEtiqueta("Tipo: " + cuenta.getDescripcionTipo(), 13f, TEXTO_SECUNDARIO));

        JPanel saldo = new JPanel(new GridLayout(2, 1, 0, 4));
        saldo.setOpaque(false);
        saldo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDE),
                BorderFactory.createEmptyBorder(12, 0, 0, 0)));
        saldo.add(crearEtiqueta("Saldo Disponible:", 13f, Color.BLACK));
        saldo.add(crearEtiqueta(Formato.moneda(cuenta.consultarSaldo()), 30f, VERDE));

        JPanel cuerpo = crearPanelTransparente();
        cuerpo.add(datos, BorderLayout.NORTH);
        cuerpo.add(saldo, BorderLayout.CENTER);
        tarjeta.add(cuerpo, BorderLayout.CENTER);
        agregarTarjeta(tarjeta, 110);

        JButton btnVolver = crearBotonSecundario("Volver al Menú");
        btnVolver.addActionListener(e -> abrirVentana(new VentanaMenuCliente(cuenta)));
        panelContenido.add(crearPanelBotones(btnVolver), BorderLayout.CENTER);
    }
}
