
package fidebankmiguelduque;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

public class ArchivoDatos {

    private static final String ARCHIVO_CLIENTES = "clientes.txt";
    private static final String ARCHIVO_CUENTAS = "cuentas.txt";
    private static final String ARCHIVO_TRANSACCIONES = "transacciones.txt";
    private static final String SEPARADOR = ";";

    public static void guardarClientes(ArrayList<Cliente> clientes) throws IOException {
        try (PrintWriter salida = new PrintWriter(new FileWriter(ARCHIVO_CLIENTES))) {
            for (Cliente c : clientes) {
                salida.println(c.getIdCliente() + SEPARADOR
                        + limpiar(c.getNombre()) + SEPARADOR
                        + limpiar(c.getApellidos()) + SEPARADOR
                        + limpiar(c.getCedula()) + SEPARADOR
                        + limpiar(c.getTelefono()) + SEPARADOR
                        + limpiar(c.getCorreo()) + SEPARADOR
                        + limpiar(c.getDireccion()) + SEPARADOR
                        + c.getFechaRegistro());
            }
        }
    }

    public static ArrayList<Cliente> leerClientes() throws IOException {
        ArrayList<Cliente> clientes = new ArrayList<>();
        for (String[] datos : leerLineas(ARCHIVO_CLIENTES)) {
            clientes.add(new Cliente(Integer.parseInt(datos[0]), datos[1], datos[2], datos[3], datos[4],
                    datos[5], datos[6], LocalDate.parse(datos[7])));
        }
        return clientes;
    }

    public static void guardarCuentas(ArrayList<Cuenta> cuentas) throws IOException {
        try (PrintWriter salida = new PrintWriter(new FileWriter(ARCHIVO_CUENTAS))) {
            for (Cuenta c : cuentas) {
                salida.println(c.getNumeroCuenta() + SEPARADOR
                        + c.getTipoCuenta() + SEPARADOR
                        + c.consultarSaldo() + SEPARADOR
                        + c.getPin() + SEPARADOR
                        + c.getEstado() + SEPARADOR
                        + c.getFechaApertura() + SEPARADOR
                        + c.getIdCliente() + SEPARADOR
                        + c.getIntentosFallidos());
            }
        }
    }

    public static ArrayList<Cuenta> leerCuentas() throws IOException {
        ArrayList<Cuenta> cuentas = new ArrayList<>();
        for (String[] datos : leerLineas(ARCHIVO_CUENTAS)) {
            String numero = datos[0];
            double saldo = Double.parseDouble(datos[2]);
            LocalDate fecha = LocalDate.parse(datos[5]);
            int idCliente = Integer.parseInt(datos[6]);
            int intentos = Integer.parseInt(datos[7]);
            if (datos[1].equals("Corriente")) {
                cuentas.add(new CuentaCorriente(numero, saldo, datos[3], datos[4], fecha, idCliente, intentos));
            } else {
                cuentas.add(new CuentaAhorro(numero, saldo, datos[3], datos[4], fecha, idCliente, intentos));
            }
        }
        return cuentas;
    }

    public static void guardarTransacciones(ArrayList<Transaccion> transacciones) throws IOException {
        try (PrintWriter salida = new PrintWriter(new FileWriter(ARCHIVO_TRANSACCIONES))) {
            for (Transaccion t : transacciones) {
                double saldoAnteriorDestino = 0.0;
                double saldoNuevoDestino = 0.0;
                if (t instanceof Transferencia) {
                    Transferencia transferencia = (Transferencia) t;
                    saldoAnteriorDestino = transferencia.getSaldoAnteriorDestino();
                    saldoNuevoDestino = transferencia.getSaldoNuevoDestino();
                }
                salida.println(t.getIdTransaccion() + SEPARADOR
                        + t.getTipo() + SEPARADOR
                        + t.getMonto() + SEPARADOR
                        + t.getFecha() + SEPARADOR
                        + t.getHora() + SEPARADOR
                        + t.getNumeroCuentaOrigen() + SEPARADOR
                        + t.getNumeroCuentaDestino() + SEPARADOR
                        + t.getSaldoAnterior() + SEPARADOR
                        + t.getSaldoNuevo() + SEPARADOR
                        + saldoAnteriorDestino + SEPARADOR
                        + saldoNuevoDestino);
            }
        }
    }

    public static ArrayList<Transaccion> leerTransacciones() throws IOException {
        ArrayList<Transaccion> transacciones = new ArrayList<>();
        for (String[] datos : leerLineas(ARCHIVO_TRANSACCIONES)) {
            int id = Integer.parseInt(datos[0]);
            double monto = Double.parseDouble(datos[2]);
            LocalDate fecha = LocalDate.parse(datos[3]);
            double saldoAnterior = Double.parseDouble(datos[7]);
            double saldoNuevo = Double.parseDouble(datos[8]);
            if (datos[1].equals("Retiro")) {
                transacciones.add(new Retiro(id, monto, fecha, datos[4], datos[5], saldoAnterior, saldoNuevo));
            } else if (datos[1].equals("Depósito")) {
                transacciones.add(new Deposito(id, monto, fecha, datos[4], datos[5], saldoAnterior, saldoNuevo));
            } else {
                transacciones.add(new Transferencia(id, monto, fecha, datos[4], datos[5], datos[6], saldoAnterior,
                        saldoNuevo, Double.parseDouble(datos[9]), Double.parseDouble(datos[10])));
            }
        }
        return transacciones;
    }

    public static File guardarComprobante(Comprobante comprobante) throws IOException {
        File carpeta = new File(Banco.getCarpetaComprobantes());
        carpeta.mkdirs();
        File archivo = new File(carpeta, "comprobante_" + Formato.formatearId(comprobante.getIdComprobante()) + ".txt");
        try (PrintWriter salida = new PrintWriter(new FileWriter(archivo))) {
            salida.print(comprobante.getContenido());
        }
        return archivo;
    }

    private static ArrayList<String[]> leerLineas(String nombreArchivo) throws IOException {
        ArrayList<String[]> lineas = new ArrayList<>();
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            return lineas;
        }
        try (BufferedReader entrada = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = entrada.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    lineas.add(linea.split(SEPARADOR, -1));
                }
            }
        }
        return lineas;
    }

    private static String limpiar(String texto) {
        return texto.replace(SEPARADOR, ",");
    }
}
