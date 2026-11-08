
package fidebankmiguelduque;

import java.time.LocalDate;

public class Deposito extends Transaccion {

    public Deposito(int idTransaccion, double monto, LocalDate fecha, String hora, String numeroCuentaOrigen,
            double saldoAnterior, double saldoNuevo) {
        super(idTransaccion, monto, fecha, hora, numeroCuentaOrigen, "", saldoAnterior, saldoNuevo);
    }

    public Deposito(double monto, String numeroCuentaOrigen) {
        super(monto, numeroCuentaOrigen, "");
    }

    @Override
    public String getTipo() {
        return "Depósito";
    }

    @Override
    public String getNombreOperacion() {
        return "Depósito de Dinero";
    }

    @Override
    public void ejecutar(Cuenta origen) throws BancoException {
        validarMonto();
        setSaldoAnterior(origen.consultarSaldo());
        origen.actualizarSaldo(getMonto());
        setSaldoNuevo(origen.consultarSaldo());
    }
}
