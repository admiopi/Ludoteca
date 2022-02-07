package LudotecaProgresa.controladores;

import LudotecaProgresa.modelos.Alumno;
import LudotecaProgresa.modelos.Juego;
import LudotecaProgresa.modelos.Prestamo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.TimeZone;

public class BDController {
    private static  final String URL = "jdbc:mysql://localhost:3306/bdludoteca?TimeZone=";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL+TimeZone.getDefault(), "root", "");
    }

    public static  boolean insertarAlumno(Alumno alumno, Connection connection) throws SQLException {
        String query = "insert into alumnos(idAlumno, nombre, apellido, email, telefono) values(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, String.valueOf(alumno.getIdAlumno()));
        preparedStatement.setString(2,alumno.getNombre());
        preparedStatement.setString(3,alumno.getApellido());
        preparedStatement.setString(4,alumno.getEmail());
        preparedStatement.setString(5,alumno.getTelefono());

        int affectedRows = preparedStatement.executeUpdate();

        return affectedRows >0;
    }

    public static  boolean modificarAlumno(Alumno alumno, Connection connection) throws SQLException {
        String queryUpdateAlumnos = "update alumnos set idAlumno=?, nombre=?, apellido=?, email=?, telefono=? where idAlumno=?";
        PreparedStatement preparedStatement = connection.prepareStatement(queryUpdateAlumnos);
        preparedStatement.setString(1, String.valueOf(alumno.getIdAlumno()));
        preparedStatement.setString(2,alumno.getNombre());
        preparedStatement.setString(3,alumno.getApellido());
        preparedStatement.setString(4,alumno.getEmail());
        preparedStatement.setString(5,alumno.getTelefono());
        preparedStatement.setString(6,String.valueOf(alumno.getIdAlumno()));


        int affectedRows = preparedStatement.executeUpdate();
        return affectedRows==1;

    }


    public static  boolean eliminarAlumno(Alumno alumno, Connection connection) throws SQLException {
        String deleteQuery = "delete from alumnos where idAlumno=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setString(1, String.valueOf(alumno.getIdAlumno()));
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println(affectedRows);
        return affectedRows>0;
    }


    //OBTENER ALUMNO--------------------

    public static  Alumno obtenerAlumno(int idAlumno){
        Connection connection = null;

        try {
            connection = getConnection();
            String query = "select * from alumnos where idAlumno = ?;";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, idAlumno);
            ResultSet rs = pstm.executeQuery();
             if (rs.next()){
                Alumno a = new Alumno(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(5), rs.getString(6));
                            return a;
            }else{
                 return null;
             }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }

    //EXISTE PERSONA
    public static  boolean existeAlumno(int idAlumno){
        Connection connection = null;
        try {
            connection = getConnection();
            String query = "select * from alumnos where idAlumno = ?";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, idAlumno);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    //-----------------------------------------JUEGOS-------------------------------------------------------------------
    public static  boolean insertarJuego(Juego juego, Connection connection) throws SQLException {
        String query = "insert into juegos(idJuego, nombre, numJugadores, tematica, tipoJuego) values(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, String.valueOf(juego.getIdJuego()));
        preparedStatement.setString(2,juego.getNombre());
        preparedStatement.setString(3,String.valueOf(juego.getNumJugadores()));
        preparedStatement.setString(4,juego.getTematica());
        preparedStatement.setString(5,juego.getTipoJuego());

        int affectedRows = preparedStatement.executeUpdate();

        return affectedRows >0;
    }

    public static  boolean modificarJuego(Juego juego, Connection connection) throws SQLException {
        String queryUpdateJuegos = "update juegos set idJuego=?, nombre=?, numJugadores=?, tematica=?, tipoJuego=? where idJuego=?";
        PreparedStatement preparedStatement = connection.prepareStatement(queryUpdateJuegos);
        preparedStatement.setString(1, String.valueOf(juego.getIdJuego()));
        preparedStatement.setString(2,juego.getNombre());
        preparedStatement.setString(3,String.valueOf(juego.getNumJugadores()));
        preparedStatement.setString(4,juego.getTematica());
        preparedStatement.setString(5,juego.getTipoJuego());
        preparedStatement.setString(6,String.valueOf(juego.getIdJuego()));


        int affectedRows = preparedStatement.executeUpdate();
        return affectedRows==1;
    }

    public static  boolean eliminarJuego(Juego juego, Connection connection) throws SQLException {
        String deleteQuery = "delete from juegos where idJuego=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setString(1, String.valueOf(juego.getIdJuego()));
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println(affectedRows);
        return affectedRows>0;
    }

    public static  Juego obtenerJuego(int idJuego){
        Connection connection = null;

        try {
            connection = getConnection();
            String query = "select * from juegos where idJuego = ?;";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, idJuego);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()){
                Juego j = new Juego(rs.getInt(1),rs.getString(2),rs.getInt(3), rs.getString(4), rs.getString(5));
                return j;
            }else{
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }

    public static  boolean existeJuego(int idJuego){
        Connection connection = null;
        try {
            connection = getConnection();
            String query = "select * from juegos where idJuego = ?;";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, idJuego);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    //-----------------------------------------------------PRESTAMOS------------------------------------------------------
    public static  boolean insertarPrestamo(Prestamo prestamo, Connection connection) throws SQLException {
        String query = "insert into prestamos(idAlumno, idJuego, fechaEntrega, fechaDevolucion) values(?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, String.valueOf(prestamo.getIdAlumno()));
        preparedStatement.setString(2,String.valueOf(prestamo.getIdJuego()));
        preparedStatement.setString(3,String.valueOf(prestamo.getFechaEntrega()));
        preparedStatement.setString(4,String.valueOf(prestamo.getFechaDevolucion()));

        int affectedRows = preparedStatement.executeUpdate();

        return affectedRows >0;
    }

}
