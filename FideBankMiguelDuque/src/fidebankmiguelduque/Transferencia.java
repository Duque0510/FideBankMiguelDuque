
package fidebankmiguelduque;

import java.time.LocalDate;

public class Transferencia extends Transaccion {

    private double saldoAnteriorDestino;
    private double saldoNuevoDestino;

    public Transferencia(int idTransaccion, double monto, LocalDate fecha, String hora, String numeroCuentaOrigen,
            String numeroCuentaDestino, double saldoAnterior, double saldoNuevo,
            double saldoAnteriorDestino, double saldoNuevoDestino) {
        super(idTransaccion, monto, fecha, hora, numeroCuentaOrigen, numeroCuentaDestino, saldoAnterior, saldoNuevo);
        this.saldoAnteriorDestino = saldoAnteriorDestino;
        this.saldoNuevoDestino = saldoNuevoDestino;
    }

    public Transferencia(double monto, String numeroCuentaOrigen, String numeroCuentaDestino) {
        super(monto, numeroCuentaOrigen, numeroCuentaDestino);
    }

    @Override
    public String getTipo() {
        return "Transferencia";
    }

    @Override
    public String getNombreOperacion() {
        return "Transferencia entre Cuentas";
    }

    @Override
    public void ejecutar(Cuenta origen) throws BancoException {
        validarMonto();
        if (getNumeroCuentaDestino().equals(origen.getNumeroCuenta())) {
            throw new DatosInvalidosException("No puede transferir a su propia cuenta.");
        }
        Cuenta destino = Banco.buscarCuenta(getNumeroCuentaDestino());
        if (!destino.estaActiva()) {
            throw new CuentaBloqueadaException("La cuenta destino no está activa.");
        }
        if (getMonto() > origen.consultarSaldo()) {
            throw new SaldoInsuficienteException();
        }
        setSaldoAnterior(origen.consultarSaldo());
        saldoAnteriorDestino = destino.consultarSaldo();
        origen.actualizarSaldo(-getMonto());
        destino.actualizarSaldo(getMonto());
        setSaldoNuevo(origen.consultarSaldo());
        saldoNuevoDestino = destino.consultarSaldo();
    }

    @Override
    public boolean involucraCuenta(String numeroCuenta) {
        return super.involucraCuenta(numeroCuenta) || getNumeroCuentaDestino().equals(numeroCuenta);
    }

    @Override
    public double getMontoPara(String numeroCuenta) {
        if (getNumeroCuentaDestino().equals(numeroCuenta)) {
            return getMonto();
        }
        return -getMonto();
    }

    @Override
    public double getSaldoResultantePara(String numeroCuenta) {
        if (getNumeroCuentaDestino().equals(numeroCuenta)) {
            return saldoNuevoDestino;
        }
        return getSaldoNuevo();
    }

    @Override
    public String getDescripcionPara(String numeroCuenta) {
        if (getNumeroCuentaDestino().equals(numeroCuenta)) {
            return "Transferencia recibida";
        }
        return getTipo();
    }

    @Override
    public String getDetalleComprobante() {
        return "Cuenta destino: " + getNumeroCuentaDestino() + "\n";
    }

    public double getSaldoAnteriorDestino() {
        return saldoAnteriorDestino;
    }

    public double getSaldoNuevoDestino() {
        return saldoNuevoDestino;
    }
}
