
package fidebankmiguelduque;

public class CuentaNoExisteException extends BancoException {

    public CuentaNoExisteException(String numeroCuenta) {
        super("La cuenta " + numeroCuenta + " no existe.");
    }
}
