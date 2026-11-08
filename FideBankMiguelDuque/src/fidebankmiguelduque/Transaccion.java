
package fidebankmiguelduque;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class Transaccion {

    private int idTransaccion;
    private double monto;
    private LocalDate fecha;
    private String hora;
    private String numeroCuentaOrigen;
    private String numeroCuentaDestino;
    private double saldoAnterior;
    private double saldoNuevo;

    public Transaccion(int idTransaccion, double monto, LocalDate fecha, String hora, String numeroCuentaOrigen,
            String numeroCuentaDestino, double saldoAnterior, double saldoNuevo) {
        this.idTransaccion = idTransaccion;
        this.monto = monto;
        this.fecha = fecha;
        this.hora = hora;
        this.numeroCuentaOrigen = numeroCuentaOrigen;
        this.numeroCuentaDestino = numeroCuentaDestino;
        this.saldoAnterior = saldoAnterior;
        this.saldoNuevo = saldoNuevo;
    }

    public Transaccion(double monto, String numeroCuentaOrigen, String numeroCuentaDestino) {
        this(0, monto, LocalDate.now(), LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                numeroCuentaOrigen, numeroCuentaDestino, 0.0, 0.0);
    }

    public abstract String getTipo();

    public abstract String getNombreOperacion();

    public abstract void ejecutar(Cuenta origen) throws BancoException;

    protected void validarMonto() throws MontoInvalidoException {
        if (monto <= 0) {
            throw new MontoInvalidoException();
        }
    }

    public boolean involucraCuenta(String numeroCuenta) {
        return numeroCuentaOrigen.equals(numeroCuenta);
    }

    public double getMontoPara(String numeroCuenta) {
        return monto;
    }

    public double getSaldoResultantePara(String numeroCuenta) {
        return saldoNuevo;
    }

    public String getDescripcionPara(String numeroCuenta) {
        return getTipo();
    }

    public String getDetalleComprobante() {
        return "";
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getNumeroCuentaOrigen() {
        return numeroCuentaOrigen;
    }

    public String getNumeroCuentaDestino() {
        return numeroCuentaDestino;
    }

    public double getSaldoAnterior() {
        return saldoAnterior;
    }

    protected void setSaldoAnterior(double saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public double getSaldoNuevo() {
        return saldoNuevo;
    }

    protected void setSaldoNuevo(double saldoNuevo) {
        this.saldoNuevo = saldoNuevo;
    }
}
