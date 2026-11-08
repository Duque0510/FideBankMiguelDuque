
package fidebankmiguelduque;

public class VentanaDeposito extends VentanaOperacion {

    public VentanaDeposito(Cuenta cuenta) {
        super("FideBank - Depósito", "Depósito a su cuenta", "Saldo actual",
                "Monto a depositar (₡):", "Confirmar Depósito", cuenta);
    }

    @Override
    protected Transaccion crearTransaccion(double monto) {
        return new Deposito(monto, cuenta.getNumeroCuenta());
    }

    @Override
    protected String getMensajeExito() {
        return "Depósito exitoso";
    }
}
