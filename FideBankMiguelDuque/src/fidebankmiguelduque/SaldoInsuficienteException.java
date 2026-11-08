
package fidebankmiguelduque;

public class SaldoInsuficienteException extends BancoException {

    public SaldoInsuficienteException() {
        super("Saldo insuficiente");
    }
}
