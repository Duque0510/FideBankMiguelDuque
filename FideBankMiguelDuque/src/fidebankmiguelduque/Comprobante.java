
package fidebankmiguelduque;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class Comprobante {

    private int idComprobante;
    private int idTransaccion;
    private LocalDate fechaImpresion;
    private String contenido;

    private Comprobante(int idComprobante, int idTransaccion, String contenido) {
        this.idComprobante = idComprobante;
        this.idTransaccion = idTransaccion;
        this.contenido = contenido;
        this.fechaImpresion = LocalDate.now();
    }

    public static Comprobante generarComprobante(Transaccion t) {
        String linea = "==============================\n";
        StringBuilder texto = new StringBuilder();
        texto.append(linea);
        texto.append("     FIDEBANK - COMPROBANTE\n");
        texto.append(linea);
        texto.append("Fecha: ").append(t.getFecha()).append("\n");
        texto.append("Hora: ").append(t.getHora()).append("\n");
        texto.append("Operación: ").append(t.getNombreOperacion()).append("\n");
        texto.append("Cuenta: ").append(t.getNumeroCuentaOrigen()).append("\n");
        texto.append(t.getDetalleComprobante());
        texto.append("Monto: ").append(Formato.moneda(t.getMonto())).append("\n");
        texto.append("Saldo anterior: ").append(Formato.moneda(t.getSaldoAnterior())).append("\n");
        texto.append("Saldo nuevo: ").append(Formato.moneda(t.getSaldoNuevo())).append("\n");
        texto.append(linea);
        texto.append("    Gracias por usar FideBank\n");
        return new Comprobante(Banco.siguienteIdComprobante(), t.getIdTransaccion(), texto.toString());
    }

    public File imprimir() throws IOException {
        return ArchivoDatos.guardarComprobante(this);
    }

    public int getIdComprobante() {
        return idComprobante;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public LocalDate getFechaImpresion() {
        return fechaImpresion;
    }

    public String getContenido() {
        return contenido;
    }
}
