
package fidebankmiguelduque;

import java.io.IOException;
import java.time.LocalDate;

public abstract class Cuenta {

    public static final int MAXIMO_INTENTOS = 3;

    private String numeroCuenta;
    private double saldo;
    private String pin;
    private String estado;
    private LocalDate fechaApertura;
    private int idCliente;
    private int intentosFallidos;

    public Cuenta(String numeroCuenta, double saldo, String pin, String estado, LocalDate fechaApertura,
            int idCliente, int intentosFallidos) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.pin = pin;
        this.estado = estado;
        this.fechaApertura = fechaApertura;
        this.idCliente = idCliente;
        this.intentosFallidos = intentosFallidos;
    }

    public Cuenta(int idCliente, String pin) {
        this("", 0.0, pin, "Activa", LocalDate.now(), idCliente, 0);
    }

    public abstract String getTipoCuenta();

    public abstract String getDescripcionTipo();

    public boolean abrirCuenta() throws IOException {
        Banco.abrirCuenta(this);
        return true;
    }

    public boolean validarPin(String pin) {
        return this.pin.equals(pin);
    }

    public double consultarSaldo() {
        return saldo;
    }

    public boolean actualizarSaldo(double monto) {
        if (saldo + monto < 0) {
            return false;
        }
        saldo += monto;
        return true;
    }

    public void bloquearCuenta() {
        estado = "Bloqueada";
    }

    public boolean estaActiva() {
        return estado.equals("Activa");
    }

    public int registrarIntentoFallido() {
        intentosFallidos++;
        if (intentosFallidos >= MAXIMO_INTENTOS) {
            bloquearCuenta();
        }
        return MAXIMO_INTENTOS - intentosFallidos;
    }

    public void reiniciarIntentos() {
        intentosFallidos = 0;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getPin() {
        return pin;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }
}
