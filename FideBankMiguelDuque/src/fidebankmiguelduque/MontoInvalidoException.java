
package fidebankmiguelduque;

public class MontoInvalidoException extends BancoException {

    public MontoInvalidoException() {
        super("El monto debe ser un número mayor a ₡0.");
    }
}
