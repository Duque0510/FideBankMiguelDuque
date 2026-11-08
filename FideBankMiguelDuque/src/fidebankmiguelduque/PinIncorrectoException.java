
package fidebankmiguelduque;

public class PinIncorrectoException extends BancoException {

    private int intentosRestantes;

    public PinIncorrectoException(int intentosRestantes) {
        super("PIN incorrecto. Intentos restantes: " + intentosRestantes);
        this.intentosRestantes = intentosRestantes;
    }

    public int getIntentosRestantes() {
        return intentosRestantes;
    }
}
