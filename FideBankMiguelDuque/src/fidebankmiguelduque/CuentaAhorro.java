
package fidebankmiguelduque;

import java.time.LocalDate;

public class CuentaAhorro extends Cuenta {

    public CuentaAhorro(String numeroCuenta, double saldo, String pin, String estado, LocalDate fechaApertura,
            int idCliente, int intentosFallidos) {
        super(numeroCuenta, saldo, pin, estado, fechaApertura, idCliente, intentosFallidos);
    }

    public CuentaAhorro(int idCliente, String pin) {
        super(idCliente, pin);
    }

    @Override
    public String getTipoCuenta() {
        return "Ahorro";
    }

    @Override
    public String getDescripcionTipo() {
        return "Cuenta de Ahorro";
    }
}
