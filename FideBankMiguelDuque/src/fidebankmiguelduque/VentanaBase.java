
package fidebankmiguelduque;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaBase extends JFrame {

    protected static final Color AZUL_OSCURO = new Color(27, 58, 92);
    protected static final Color AZUL = new Color(46, 134, 193);
    protected static final Color ROJO = new Color(231, 76, 60);
    protected static final Color VERDE = new Color(30, 132, 73);
    protected static final Color FONDO = new Color(244, 245, 247);
    protected static final Color GRIS = new Color(229, 231, 235);
    protected static final Color BORDE = new Color(200, 205, 212);
    protected static final Color TEXTO_SECUNDARIO = new Color(90, 90, 90);

    private JPanel encabezado;
    protected JPanel panelContenido;

    public VentanaBase(String titulo, int ancho, int alto) {
        setTitle(titulo);
        setSize(ancho, alto);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(AZUL_OSCURO);
        encabezado.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(20f));
        encabezado.add(lblTitulo, BorderLayout.WEST);

        panelContenido = new JPanel(new BorderLayout(0, 15));
        panelContenido.setBackground(FONDO);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel pie = new JPanel(new BorderLayout());
        pie.setBackground(GRIS);
        pie.setBorder(BorderFactory.createEmptyBorder(6, 25, 6, 25));
        pie.add(crearEtiqueta("FideBank © 2026 - Su dinero seguro", 11f, TEXTO_SECUNDARIO), BorderLayout.WEST);
        pie.add(crearEtiqueta("Servicio disponible", 11f, TEXTO_SECUNDARIO), BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(encabezado, BorderLayout.NORTH);
        add(panelContenido, BorderLayout.CENTER);
        add(pie, BorderLayout.SOUTH);
    }

    protected void agregarTextoEncabezado(String texto) {
        JLabel lblTexto = new JLabel(texto);
        lblTexto.setForeground(Color.WHITE);
        encabezado.add(lblTexto, BorderLayout.EAST);
    }

    protected JLabel crearEtiqueta(String texto, float tamano, Color color) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(etiqueta.getFont().deriveFont(tamano));
        etiqueta.setForeground(color);
        return etiqueta;
    }

    protected JButton crearBoton(String texto, Color fondo, Color colorTexto) {
        JButton boton = new JButton(texto);
        boton.setBackground(fondo);
        boton.setForeground(colorTexto);
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        return boton;
    }

    protected JButton crearBotonPrimario(String texto) {
        return crearBoton(texto, AZUL, Color.WHITE);
    }

    protected JButton crearBotonSecundario(String texto) {
        return crearBoton(texto, GRIS, Color.BLACK);
    }

    protected JButton crearBotonPeligro(String texto) {
        return crearBoton(texto, ROJO, Color.WHITE);
    }

    protected JPanel crearPanelBotones(JButton... botones) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        panel.setOpaque(false);
        for (JButton boton : botones) {
            panel.add(boton);
        }
        return panel;
    }

    protected JPanel crearPanelFormulario(int filas) {
        JPanel panel = new JPanel(new GridLayout(filas, 2, 10, 10));
        panel.setOpaque(false);
        return panel;
    }

    protected JPanel crearPanelTransparente() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);
        return panel;
    }

    protected JPanel crearTarjeta(String titulo) {
        JPanel tarjeta = new JPanel(new BorderLayout(0, 12));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(18, 22, 18, 22)));
        if (titulo != null) {
            tarjeta.add(crearEtiqueta(titulo, 17f, Color.BLACK), BorderLayout.NORTH);
        }
        return tarjeta;
    }

    protected void agregarTarjeta(JPanel tarjeta, int margenLateral) {
        panelContenido.setBorder(BorderFactory.createEmptyBorder(30, margenLateral, 20, margenLateral));
        panelContenido.add(tarjeta, BorderLayout.NORTH);
    }

    protected JLabel crearMensaje() {
        JLabel mensaje = new JLabel(" ");
        limpiarMensaje(mensaje);
        return mensaje;
    }

    protected void mostrarError(JLabel mensaje, String texto) {
        configurarMensaje(mensaje, texto, ROJO, new Color(253, 236, 234));
    }

    protected void mostrarExito(JLabel mensaje, String texto) {
        configurarMensaje(mensaje, texto, VERDE, new Color(234, 247, 238));
    }

    protected void limpiarMensaje(JLabel mensaje) {
        mensaje.setText(" ");
        mensaje.setOpaque(false);
        mensaje.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 12));
    }

    private void configurarMensaje(JLabel mensaje, String texto, Color color, Color fondo) {
        mensaje.setText(texto);
        mensaje.setForeground(color);
        mensaje.setBackground(fondo);
        mensaje.setOpaque(true);
        mensaje.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, color),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
    }

    protected void abrirVentana(JFrame ventana) {
        ventana.setVisible(true);
        dispose();
    }
}
