
package fidebankmiguelduque;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VentanaHistorial extends VentanaBase {

    public VentanaHistorial(Cuenta cuenta) {
        super("FideBank - Historial de Transacciones", 780, 540);

        String numeroCuenta = cuenta.getNumeroCuenta();
        ArrayList<Transaccion> historial = Banco.obtenerHistorial(numeroCuenta);

        JPanel superior = new JPanel(new BorderLayout());
        superior.setOpaque(false);
        superior.add(crearEtiqueta("Cuenta: " + numeroCuenta, 14f, Color.BLACK), BorderLayout.WEST);
        superior.add(crearEtiqueta(historial.size() + " transacciones", 12f, TEXTO_SECUNDARIO), BorderLayout.EAST);

        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"Fecha", "Hora", "Tipo", "Monto", "Saldo Resultante"}, 0);
        for (Transaccion t : historial) {
            modelo.addRow(new Object[]{
                t.getFecha().toString(),
                t.getHora(),
                t.getDescripcionPara(numeroCuenta),
                Formato.montoConSigno(t.getMontoPara(numeroCuenta)),
                Formato.moneda(t.getSaldoResultantePara(numeroCuenta))
            });
        }
        JTable tabla = new JTable(modelo);
        tabla.setDefaultEditor(Object.class, null);
        tabla.setRowHeight(26);
        tabla.setFillsViewportHeight(true);
        tabla.getTableHeader().setReorderingAllowed(false);

        String nota = historial.isEmpty()
                ? "No hay transacciones registradas en esta cuenta."
                : "Movimientos ordenados del más reciente al más antiguo.";
        JButton btnVolver = crearBotonSecundario("Volver al Menú");
        btnVolver.addActionListener(e -> abrirVentana(new VentanaMenuCliente(cuenta)));
        JPanel inferior = crearPanelTransparente();
        inferior.add(crearEtiqueta(nota, 12f, TEXTO_SECUNDARIO), BorderLayout.NORTH);
        inferior.add(crearPanelBotones(btnVolver), BorderLayout.CENTER);

        panelContenido.add(superior, BorderLayout.NORTH);
        panelContenido.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panelContenido.add(inferior, BorderLayout.SOUTH);
    }
}
