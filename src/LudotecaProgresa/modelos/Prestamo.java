package LudotecaProgresa.modelos;

import java.time.LocalDateTime;
import java.util.Date;

public class Prestamo {
    private int idPrestamo;
    private int idAlumno;
    private int idJuego;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaDevolucion;

    public Prestamo(int idAlumno, int idJuego, LocalDateTime fechaEntrega,  LocalDateTime fechaDevolucion) {
        this.idAlumno = idAlumno;
        this.idJuego = idJuego;
        this.fechaEntrega = fechaEntrega;
        this.fechaDevolucion = fechaDevolucion;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(int idJuego) {
        this.idJuego = idJuego;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public LocalDateTime getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDateTime fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}
