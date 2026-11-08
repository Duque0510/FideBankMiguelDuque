
package fidebankmiguelduque;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Banco {

    private static final String CARPETA_COMPROBANTES = "comprobantes";

    private static ArrayList<Cliente> clientes = new ArrayList<>();
    private static ArrayList<Cuenta> cuentas = new ArrayList<>();
    private static ArrayList<Transaccion> transacciones = new ArrayList<>();
    private static Usuario administrador = new Usuario(1, "Administrador", "admin", "admin", "Administrador", "Activo");

    public static void cargarDatos() throws IOException {
        clientes = ArchivoDatos.leerClientes();
        cuentas = ArchivoDatos.leerCuentas();
        transacciones = ArchivoDatos.leerTransacciones();
    }

    public static void guardarDatos() throws IOException {
        ArchivoDatos.guardarClientes(clientes);
        ArchivoDatos.guardarCuentas(cuentas);
        ArchivoDatos.guardarTransacciones(transacciones);
    }

    public static Usuario getAdministrador() {
        return administrador;
    }

    public static void registrarCliente(Cliente cliente) throws DatosInvalidosException, IOException {
        for (Cliente c : clientes) {
            if (c.getCedula().equalsIgnoreCase(cliente.getCedula())) {
                throw new DatosInvalidosException("Ya existe un cliente con la cédula " + cliente.getCedula() + ".");
            }
        }
        int maximo = 0;
        for (Cliente c : clientes) {
            if (c.getIdCliente() > maximo) {
                maximo = c.getIdCliente();
            }
        }
        cliente.setId(maximo + 1);
        clientes.add(cliente);
        guardarDatos();
    }

    public static Cliente buscarClientePorCedula(String cedula) throws ClienteNoExisteException {
        for (Cliente c : clientes) {
            if (c.getCedula().equalsIgnoreCase(cedula)) {
                return c;
            }
        }
        throw new ClienteNoExisteException(cedula);
    }

    public static Cliente buscarClientePorId(int idCliente) {
        for (Cliente c : clientes) {
            if (c.getIdCliente() == idCliente) {
                return c;
            }
        }
        return null;
    }

    public static ArrayList<Cliente> obtenerClientes() {
        return new ArrayList<>(clientes);
    }

    public static ArrayList<Cuenta> obtenerCuentasCliente(int idCliente) {
        ArrayList<Cuenta> resultado = new ArrayList<>();
        for (Cuenta c : cuentas) {
            if (c.getIdCliente() == idCliente) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public static void abrirCuenta(Cuenta cuenta) throws IOException {
        long maximo = 1000000000L;
        for (Cuenta c : cuentas) {
            long numero = Long.parseLong(c.getNumeroCuenta());
            if (numero > maximo) {
                maximo = numero;
            }
        }
        cuenta.setNumeroCuenta(String.valueOf(maximo + 1));
        cuentas.add(cuenta);
        guardarDatos();
    }

    public static Cuenta buscarCuenta(String numeroCuenta) throws CuentaNoExisteException {
        for (Cuenta c : cuentas) {
            if (c.getNumeroCuenta().equals(numeroCuenta)) {
                return c;
            }
        }
        throw new CuentaNoExisteException(numeroCuenta);
    }

    public static Cuenta autenticar(String numeroCuenta, String pin) throws BancoException, IOException {
        Cuenta cuenta = buscarCuenta(numeroCuenta);
        if (!cuenta.estaActiva()) {
            throw new CuentaBloqueadaException("La cuenta está bloqueada. Contacte a FideBank.");
        }
        if (!cuenta.validarPin(pin)) {
            int intentosRestantes = cuenta.registrarIntentoFallido();
            guardarDatos();
            if (intentosRestantes <= 0) {
                throw new CuentaBloqueadaException("PIN incorrecto. La cuenta fue bloqueada tras "
                        + Cuenta.MAXIMO_INTENTOS + " intentos fallidos.");
            }
            throw new PinIncorrectoException(intentosRestantes);
        }
        cuenta.reiniciarIntentos();
        guardarDatos();
        return cuenta;
    }

    public static void realizarTransaccion(Transaccion transaccion, Cuenta origen) throws BancoException, IOException {
        transaccion.ejecutar(origen);
        int maximo = 0;
        for (Transaccion t : transacciones) {
            if (t.getIdTransaccion() > maximo) {
                maximo = t.getIdTransaccion();
            }
        }
        transaccion.setIdTransaccion(maximo + 1);
        transacciones.add(transaccion);
        guardarDatos();
    }

    public static ArrayList<Transaccion> obtenerHistorial(String numeroCuenta) {
        ArrayList<Transaccion> historial = new ArrayList<>();
        for (int i = transacciones.size() - 1; i >= 0; i--) {
            if (transacciones.get(i).involucraCuenta(numeroCuenta)) {
                historial.add(transacciones.get(i));
            }
        }
        return historial;
    }

    public static int siguienteIdComprobante() {
        String[] archivos = new File(CARPETA_COMPROBANTES).list();
        if (archivos == null) {
            return 1;
        }
        return archivos.length + 1;
    }

    public static String getCarpetaComprobantes() {
        return CARPETA_COMPROBANTES;
    }
}
