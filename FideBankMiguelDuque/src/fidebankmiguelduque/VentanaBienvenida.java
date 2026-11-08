
package fidebankmiguelduque;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaBienvenida extends VentanaBase {

    public VentanaBienvenida() {
        super("FideBank", 720, 480);
        agregarTextoEncabezado("Cajero Automático");

        JPanel centro = new JPanel(new GridLayout(5, 1, 0, 14));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(30, 150, 30, 150));

        JLabel lblTitulo = crearEtiqueta("Bienvenido a FideBank", 26f, AZUL_OSCURO);
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        JLabel lblSubtitulo = crearEtiqueta("Seleccione una opción para comenzar.", 13f, TEXTO_SECUNDARIO);
        lblSubtitulo.setHorizontalAlignment(JLabel.CENTER);
        JButton btnIniciarOperacion = crearBotonPrimario("Iniciar Operación");
        JButton btnAccesoAdministrador = crearBotonSecundario("Acceso Administrador");
        JLabel lblNota = crearEtiqueta("Por su seguridad, nunca comparta su PIN.", 12f, TEXTO_SECUNDARIO);
        lblNota.setHorizontalAlignment(JLabel.CENTER);

        centro.add(lblTitulo);
        centro.add(lblSubtitulo);
        centro.add(btnIniciarOperacion);
        centro.add(btnAccesoAdministrador);
        centro.add(lblNota);

        btnIniciarOperacion.addActionListener(e -> abrirVentana(new VentanaLoginCliente()));
        btnAccesoAdministrador.addActionListener(e -> abrirVentana(new VentanaLoginAdmin()));

        panelContenido.add(centro, BorderLayout.CENTER);
    }
}
