
package fidebankmiguelduque;

public class VentanaRetiro extends VentanaOperacion {

    public VentanaRetiro(Cuenta cuenta) {
        super("FideBank - Retiro de Fondos", "Solicitud de retiro", "Saldo disponible",
                "Monto a retirar (₡):", "Confirmar Retiro", cuenta);
    }

    @Override
    protected Transaccion crearTransaccion(double monto) {
        return new Retiro(monto, cuenta.getNumeroCuenta());
    }

    @Override
    protected String getMensajeExito() {
        return "Retiro exitoso";
    }
}
