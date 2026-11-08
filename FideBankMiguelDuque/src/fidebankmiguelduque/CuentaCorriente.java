
package fidebankmiguelduque;

import java.time.LocalDate;

public class CuentaCorriente extends Cuenta {

    public CuentaCorriente(String numeroCuenta, double saldo, String pin, String estado, LocalDate fechaApertura,
            int idCliente, int intentosFallidos) {
        super(numeroCuenta, saldo, pin, estado, fechaApertura, idCliente, intentosFallidos);
    }

    public CuentaCorriente(int idCliente, String pin) {
        super(idCliente, pin);
    }

    @Override
    public String getTipoCuenta() {
        return "Corriente";
    }

    @Override
    public String getDescripcionTipo() {
        return "Cuenta Corriente";
    }
}
