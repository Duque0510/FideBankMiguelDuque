
package fidebankmiguelduque;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente extends Persona {

    private String apellidos;
    private String cedula;
    private String telefono;
    private String correo;
    private String direccion;
    private LocalDate fechaRegistro;

    public Cliente(int idCliente, String nombre, String apellidos, String cedula, String telefono,
            String correo, String direccion, LocalDate fechaRegistro) {
        super(idCliente, nombre);
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
    }

    public Cliente(String nombre, String apellidos, String cedula, String telefono, String correo, String direccion) {
        this(0, nombre, apellidos, cedula, telefono, correo, direccion, LocalDate.now());
    }

    public boolean registrarCliente() throws DatosInvalidosException, IOException {
        Banco.registrarCliente(this);
        return true;
    }

    public boolean actualizarDatos(String telefono, String correo, String direccion) throws IOException {
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        Banco.guardarDatos();
        return true;
    }

    public ArrayList<Cuenta> obtenerCuentas() {
        return Banco.obtenerCuentasCliente(getIdCliente());
    }

    @Override
    public String getNombreCompleto() {
        return getNombre() + " " + apellidos;
    }

    public int getIdCliente() {
        return getId();
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCedula() {
        return cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    @Override
    public String toString() {
        return "ID: " + Formato.formatearId(getIdCliente())
                + "\nNombre: " + getNombreCompleto()
                + "\nCédula: " + cedula
                + "\nTeléfono: " + telefono
                + "\nCorreo: " + correo
                + "\nDirección: " + direccion
                + "\nFecha de registro: " + fechaRegistro;
    }
}
