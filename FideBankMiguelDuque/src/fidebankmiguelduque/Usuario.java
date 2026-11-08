
package fidebankmiguelduque;

public class Usuario extends Persona {

    private String nombreUsuario;
    private String contrasena;
    private String rol;
    private String estado;
    private boolean sesionActiva;

    public Usuario(int idUsuario, String nombre, String nombreUsuario, String contrasena, String rol, String estado) {
        super(idUsuario, nombre);
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.estado = estado;
    }

    public boolean iniciarSesion(String user, String pass) {
        sesionActiva = estado.equals("Activo") && nombreUsuario.equals(user) && contrasena.equals(pass);
        return sesionActiva;
    }

    public void cerrarSesion() {
        sesionActiva = false;
    }

    @Override
    public String getNombreCompleto() {
        return getNombre();
    }

    public int getIdUsuario() {
        return getId();
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    public String getEstado() {
        return estado;
    }

    public boolean isSesionActiva() {
        return sesionActiva;
    }
}
