package LudotecaProgresa.modelos;

public class Juego {
    private int idJuego;
    private String nombre;
    private int numJugadores;
    private String tematica;
    private String tipoJuego;


    public Juego(int idJuego, String nombre, int numJugadores, String tematica, String tipoJuego) {
        this.idJuego = idJuego;
        this.nombre = nombre;
        this.numJugadores = numJugadores;
        this.tematica = tematica;
        this.tipoJuego = tipoJuego;
    }

    public int getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(int idJuego) {
        this.idJuego = idJuego;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumJugadores() {
        return numJugadores;
    }

    public void setNumJugadores(int numJugadores) {
        this.numJugadores = numJugadores;
    }

    public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public String getTipoJuego() {
        return tipoJuego;
    }

    public void setTipoJuego(String tipoJuego) {
        this.tipoJuego = tipoJuego;
    }
}
