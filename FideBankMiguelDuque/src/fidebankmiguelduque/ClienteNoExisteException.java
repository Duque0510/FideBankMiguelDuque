
package fidebankmiguelduque;

public class ClienteNoExisteException extends BancoException {

    public ClienteNoExisteException(String cedula) {
        super("No existe un cliente con la cédula " + cedula + ".");
    }
}
