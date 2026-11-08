//Avance 2 Miguel Duque
//Usuario y clave de administrador es admin
//cuentas de prueba 1000000001, 1000000002, 1000000003 pin 1234
package fidebankmiguelduque;

import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        try {
            Banco.cargarDatos();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        SwingUtilities.invokeLater(() -> new VentanaBienvenida().setVisible(true));
    }
}
