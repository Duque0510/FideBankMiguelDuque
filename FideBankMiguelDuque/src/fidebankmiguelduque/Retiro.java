
package fidebankmiguelduque;

import java.time.LocalDate;

public class Retiro extends Transaccion {

    public Retiro(int idTransaccion, double monto, LocalDate fecha, String hora, String numeroCuentaOrigen,
            double saldoAnterior, double saldoNuevo) {
        super(idTransaccion, monto, fecha, hora, numeroCuentaOrigen, "", saldoAnterior, saldoNuevo);
    }

    public Retiro(double monto, String numeroCuentaOrigen) {
        super(monto, numeroCuentaOrigen, "");
    }

    @Override
    public String getTipo() {
        return "Retiro";
    }

    @Override
    public String getNombreOperacion() {
        return "Retiro de Fondos";
    }

    @Override
    public void ejecutar(Cuenta origen) throws BancoException {
        validarMonto();
        if (getMonto() > origen.consultarSaldo()) {
            throw new SaldoInsuficienteException();
        }
        setSaldoAnterior(origen.consultarSaldo());
        origen.actualizarSaldo(-getMonto());
        setSaldoNuevo(origen.consultarSaldo());
    }

    @Override
    public double getMontoPara(String numeroCuenta) {
        return -getMonto();
    }
}
