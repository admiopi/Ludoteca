package LudotecaProgresa.modelos;
import java.util.Date;

public class Alumno {
    private int idAlumno;
    private String nombre;
    private  String apellido;
    private Date fechaPenalizacion;
    private String email;
    private String telefono;

    public Alumno(int idAlumno, String nombre, String apellido, String email, String telefono) {
        this.idAlumno= idAlumno;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setFechaPenalizacion(Date fechaPenalizacion) {
        this.fechaPenalizacion = fechaPenalizacion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Date getFechaPenalizacion() {
        return fechaPenalizacion;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }
}
