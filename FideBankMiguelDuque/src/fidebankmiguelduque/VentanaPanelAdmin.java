
package fidebankmiguelduque;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class VentanaPanelAdmin extends VentanaBase {

    public VentanaPanelAdmin(Usuario administrador) {
        super("FideBank - Panel de Administración", 760, 520);

        JPanel superior = new JPanel(new GridLayout(2, 1, 0, 4));
        superior.setOpaque(false);
        superior.add(crearEtiqueta("Bienvenido, " + administrador.getNombreCompleto(), 22f, AZUL_OSCURO));
        superior.add(crearEtiqueta("Seleccione una gestión de clientes o cuentas.", 12f, TEXTO_SECUNDARIO));

        JPanel botones = new JPanel(new GridLayout(4, 1, 0, 14));
        botones.setOpaque(false);
        botones.setBorder(BorderFactory.createEmptyBorder(10, 120, 30, 120));
        JButton btnRegistrar = crearBotonPrimario("Registrar Nuevo Cliente");
        JButton btnAbrirCuenta = crearBotonPrimario("Abrir Cuenta Bancaria");
        JButton btnConsultar = crearBotonPrimario("Consultar Clientes");
        JButton btnCerrarSesion = crearBotonPeligro("Cerrar Sesión");
        botones.add(btnRegistrar);
        botones.add(btnAbrirCuenta);
        botones.add(btnConsultar);
        botones.add(btnCerrarSesion);

        btnRegistrar.addActionListener(e -> abrirVentana(new VentanaRegistroCliente(administrador)));
        btnAbrirCuenta.addActionListener(e -> abrirVentana(new VentanaAperturaCuenta(administrador)));
        btnConsultar.addActionListener(e -> abrirVentana(new VentanaListaClientes(administrador)));
        btnCerrarSesion.addActionListener(e -> {
            administrador.cerrarSesion();
            abrirVentana(new VentanaBienvenida());
        });

        panelContenido.add(superior, BorderLayout.NORTH);
        panelContenido.add(botones, BorderLayout.CENTER);
    }
}
