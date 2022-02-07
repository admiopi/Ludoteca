package LudotecaProgresa.controladores;

import LudotecaProgresa.modelos.Alumno;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TimeZone;

public class FileController {
    //ESCRIBIR FICHERO
    public static void escribirFichero(Connection connection) {
        File archivo = new File("Nuevos_Alumnos.txt");
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        Statement stm = null;
        try {
            stm = connection.createStatement();
            String query = "select * from alumnos";
            ResultSet rs = stm.executeQuery(query);
            fileWriter = new FileWriter(archivo);
            printWriter = new PrintWriter(fileWriter);
            while (rs.next()) {
                int idAlumno = rs.getInt(1);
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                String email = rs.getString(5);
                String telefono = rs.getString(6);
                printWriter.println(idAlumno + ":" + nombre + ":" + apellido + ":" + email + ":" + telefono);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                if (fileWriter!=null)
                    fileWriter.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }

    public static void escribirFicheroJuegos(Connection connection) {
        File archivo = new File("Nuevos_Juegos.txt");
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        Statement stm = null;
        try {
            stm = connection.createStatement();
            String query = "select * from juegos";
            ResultSet rs = stm.executeQuery(query);
            fileWriter = new FileWriter(archivo);
            printWriter = new PrintWriter(fileWriter);
            while (rs.next()) {
                int idJuego = rs.getInt(1);
                String nombre = rs.getString(2);
                int numJugadores = rs.getInt(3);
                String tematica = rs.getString(4);
                String tipoJuego = rs.getString(5);
                printWriter.println(idJuego + ":" + nombre + ":" + numJugadores + ":" + tematica + ":" + tipoJuego);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                if (fileWriter!=null)
                    fileWriter.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }



    //ESCRIBIR FICHERO
    public static void escribirFicheroPrestamos(Connection connection) {
        File archivo = new File("Nuevos_Prestamos.txt");
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        Statement stm = null;
        try {
            stm = connection.createStatement();
            String query = "select * from prestamos";
            ResultSet rs = stm.executeQuery(query);
            fileWriter = new FileWriter(archivo);
            printWriter = new PrintWriter(fileWriter);
            while (rs.next()) {
                int idPrestamo = rs.getInt(1);
                int idAlumno = rs.getInt(2);
                int idJuego = rs.getInt(3);
                LocalDateTime fechaEntrega = rs.getObject(4, LocalDateTime.class);
                LocalDateTime fechaDevolucion = rs.getObject(5, LocalDateTime.class);

                printWriter.println(idPrestamo + ":" + idAlumno + ":" + idJuego + ":" + fechaEntrega + ":" + fechaDevolucion);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                if (fileWriter!=null)
                    fileWriter.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }
}






