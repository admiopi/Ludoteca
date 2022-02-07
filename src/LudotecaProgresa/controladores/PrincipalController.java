package LudotecaProgresa.controladores;

import LudotecaProgresa.modelos.Alumno;
import LudotecaProgresa.modelos.Juego;
import LudotecaProgresa.modelos.Prestamo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.InputMethodTextRun;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.text.html.ImageView;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

import static LudotecaProgresa.controladores.BDController.getConnection;

public class PrincipalController {
    @FXML
    private ImageView imNuevoContacto;

    @FXML
    private ImageView imNuevoJuego;

    @FXML
    private ImageView imNuevoPrestamo;

    @FXML
    private ImageView imRevisarPrestamos;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtIdAlumno;

    @FXML
    private TextField txtNombreJuego;

    @FXML
    private TextField txtNumeroJugadores;

    @FXML
    private TextField txtTematica;

    @FXML
    private TextField txtTipoJuego;

    @FXML
    private TextField txtIdJuego;

    @FXML
    private Label lbFechaEntrega;

    @FXML
    private Label lbFechaDevolucion;

    @FXML
    private TextField txtCodAlumno;

    @FXML
    private TextField txtCodJuego;

    @FXML
    private Label lbAlumno;

    @FXML
    private Label lbJuego;


    @FXML
    private TextField txtCodigoAlumno;

    @FXML
    private Label lbNombreControlPrestamos;

    @FXML
    private Label lbApellidosControlPrestamos;

    @FXML
    private Label lbJuego1;

    @FXML
    private Label lbjuego2;

    @FXML
    private Label lbJuego3;

    public static int idPrestamo1= 0;
    public static int idPrestamo2= 0;
    public static int idPrestamo3= 0;


    public void grabarFicheroTextoAlumnos(Connection connection){
        try {
            connection = getConnection();
            FileController.escribirFichero(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public void onBtnGuardarButtonClicked(MouseEvent mouseEvent){
        if(txtIdAlumno.getText().length()!=0){
            Alumno alumno = rellenaAlumno();
            Connection connection = null;

            //--------BASE DE DATOS-----------------------

            if (!BDController.existeAlumno(Integer.parseInt(txtIdAlumno.getText()))) {
                try {
                    connection = getConnection();
                    if (BDController.insertarAlumno(alumno, connection)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("TODO OK");
                        alert.setContentText("SE HA GUARDADO EL ALUMNO");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                        grabarFicheroTextoAlumnos(connection);
                        limpiar();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("ALUMNO YA EXISTENTE");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR AL CONECTAR A LA BASE DE DATOS");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ALUMNO YA EXISTENTE");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DEBES SELECCIONAR UN ALUMNO");
            alert.setHeaderText(null);
            alert.showAndWait();
            limpiar();
        }
    }

    public void onBtnBuscarAlumnoClicked(MouseEvent mouseEvent){
        if(txtIdAlumno.getText().length()!=0){
            Alumno alumno=BDController.obtenerAlumno(Integer.parseInt(txtIdAlumno.getText()));
            if (alumno == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ESTE ALUMNO NO EXISTE");
                alert.setHeaderText(null);
                alert.showAndWait();
            }else{
                txtIdAlumno.setText(String.valueOf(alumno.getIdAlumno()));
                txtNombre.setText(alumno.getNombre());
                txtApellido.setText(alumno.getApellido());
                txtEmail.setText(alumno.getEmail());
                txtTelefono.setText(alumno.getTelefono());
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DEBES SELECCIONAR UN ALUMNO");
            alert.setHeaderText(null);
            alert.showAndWait();
            limpiar();
        }

    }


    public void onBtnModificarAlumnoClicked(MouseEvent mouseEvent){
       if (txtIdAlumno.getText().length()!=0){
           if(BDController.existeAlumno(Integer.parseInt(txtIdAlumno.getText()))) {
               Connection connection = null;
               try {
                   connection = getConnection();
                   Alumno alumno = rellenaAlumno();
                   if (BDController.modificarAlumno(alumno, connection)) {
                       Alert alert = new Alert(Alert.AlertType.ERROR);
                       alert.setTitle("MODIFICACIÓN REALIZADA");
                       alert.setHeaderText(null);
                       alert.showAndWait();
                       grabarFicheroTextoAlumnos(connection);
                       limpiar();
                   }else{
                       Alert alert = new Alert(Alert.AlertType.ERROR);
                       alert.setTitle("MODIFICACIÓN NO REALIZADA");
                       alert.setHeaderText(null);
                       alert.showAndWait();
                   }
               } catch (SQLException throwables) {
                   throwables.printStackTrace();
               }finally {
                   if (connection!=null);
                   try{
                       connection.close();
                   }catch (SQLException throwables){
                       throwables.printStackTrace();
                   }
               }
           }else{
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("ALUMNO NO ENCONTRADO");
               alert.setHeaderText(null);
               alert.showAndWait();
           }
       }else{
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("DEBES SELECCIONAR UN ALUMNO");
           alert.setHeaderText(null);
           alert.showAndWait();
       }
    }



    public void onBtnEliminarAlumnoClicked(MouseEvent mouseEvent){
        if (txtIdAlumno.getText().length()!=0) {
            if (BDController.existeAlumno(Integer.parseInt(txtIdAlumno.getText())) /*&& txtIdAlumno.getText()!="" && txtNombre.getText()!=""
                && txtApellido.getText()!=""&& txtEmail.getText()!=""&& txtTelefono.getText()!=""*/) {
                Connection connection = null;
                try {
                    connection = getConnection();
                    Alumno alumno = rellenaAlumno();
                    if (BDController.eliminarAlumno(alumno, connection)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ELIMINACIÓN REALIZADA");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                        grabarFicheroTextoAlumnos(connection);
                        limpiar();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ELIMINACIÓN NO REALIZADA");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    if (connection != null) ;
                    try {
                        connection.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ALUMNO NO ENCONTRADO");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DEBES SELECCIONAR UN ALUMNO");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    //RELLENAR ALUMNO
    private Alumno rellenaAlumno() {
        int idAlumno=Integer.parseInt(txtIdAlumno.getText());
        String nombre=this.txtNombre.getText();
        String apellido=this.txtApellido.getText();
        String email = this.txtEmail.getText();
        String telefono = this.txtTelefono.getText();

        Alumno alumno = new Alumno(idAlumno, nombre, apellido, email, telefono);

        return alumno;
    }

    //LIMPIAR
    private void limpiar(){
        txtIdAlumno.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtEmail.clear();
        txtTelefono.clear();
    }


    //------------------------------------JUEGOS------------------------------------------------------------------------

    public void onBtnGuardarJuegosButtonClicked(MouseEvent mouseEvent){
        if(txtIdJuego.getText().length()!=0){
            Juego juego = rellenaJuego();
            Connection connection = null;

            //--------BASE DE DATOS-----------------------

            if (!BDController.existeJuego(Integer.parseInt(txtIdJuego.getText()))) {
                try {
                    connection = getConnection();
                    if (BDController.insertarJuego(juego, connection)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("TODO OK");
                        alert.setContentText("SE HA GUARDADO EL JUEGO");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                        grabarFicheroTextoJuegos(connection);
                        limpiarJuego();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("JUEGO YA EXISTENTE");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR AL CONECTAR A LA BASE DE DATOS");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
                limpiar();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("JUEGO YA EXISTENTE");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DEBES SELECCIONAR UN JUEGO");
            alert.setHeaderText(null);
            alert.showAndWait();
            limpiar();
        }
    }

    public void onBtnBuscarJuegoClicked(MouseEvent mouseEvent){
        if(txtIdJuego.getText().length()!=0){
            Juego juego=BDController.obtenerJuego(Integer.parseInt(txtIdJuego.getText()));
            if (juego == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ESTE JUEGO NO EXISTE");
                alert.setHeaderText(null);
                alert.showAndWait();
            }else{
                txtIdJuego.setText(String.valueOf(juego.getIdJuego()));
                txtNombreJuego.setText(juego.getNombre());
                txtNumeroJugadores.setText(String.valueOf(juego.getNumJugadores()));
                txtTematica.setText(juego.getTematica());
                txtTipoJuego.setText(juego.getTipoJuego());
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DEBES SELECCIONAR UN JUEGO");
            alert.setHeaderText(null);
            alert.showAndWait();
            limpiarJuego();
        }

    }


    public void onBtnModificarJuegoClicked(MouseEvent mouseEvent){
        if (txtIdJuego.getText().length()!=0){
            if(BDController.existeJuego(Integer.parseInt(txtIdJuego.getText()))) {
                Connection connection = null;
                try {
                    connection = getConnection();
                    Juego juego = rellenaJuego();
                    if (BDController.modificarJuego(juego, connection)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("MODIFICACIÓN REALIZADA");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                        grabarFicheroTextoJuegos(connection);
                        limpiarJuego();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("MODIFICACIÓN NO REALIZADA");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }finally {
                    if (connection!=null);
                    try{
                        connection.close();
                    }catch (SQLException throwables){
                        throwables.printStackTrace();
                    }
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("JUEGO NO ENCONTRADO");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DEBES SELECCIONAR UN JUEGO");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }


    public void onBtnEliminarJuegoClicked(MouseEvent mouseEvent){
        if (txtIdJuego.getText().length()!=0) {
            if (BDController.existeJuego(Integer.parseInt(txtIdJuego.getText()))) {
                Connection connection = null;
                try {
                    connection = getConnection();
                    Juego juego = rellenaJuego();
                    if (BDController.eliminarJuego(juego, connection)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ELIMINACIÓN REALIZADA");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                        grabarFicheroTextoJuegos(connection);
                        limpiarJuego();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ELIMINACIÓN NO REALIZADA");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    if (connection != null) ;
                    try {
                        connection.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("JUEGO NO ENCONTRADO");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DEBES SELECCIONAR UN JUEGO");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }


    //RELLENAR JUEGO
    private Juego rellenaJuego() {
        int idJuego=Integer.parseInt(txtIdJuego.getText());
        String nombre=this.txtNombreJuego.getText();
        int numJugadores=Integer.parseInt(txtNumeroJugadores.getText());
        String tematica = this.txtTematica.getText();
        String tipoJuego = this.txtTipoJuego.getText();

        Juego juego = new Juego(idJuego, nombre, numJugadores, tematica, tipoJuego);

        return juego;
    }

    //LIMPIAR JUEGO
    private void limpiarJuego(){
        txtIdJuego.clear();
        txtNombreJuego.clear();
        txtNumeroJugadores.clear();
        txtTematica.clear();
        txtTipoJuego.clear();
    }

    public void grabarFicheroTextoJuegos(Connection connection){
        try {
            connection = getConnection();
            FileController.escribirFicheroJuegos(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //-----------------CREAR PRESTAMOS------------------------------------------------------------------------------------------
    public void buscarAlumno(){
        if(txtCodAlumno.getText().length()!=0){
            Alumno alumno=BDController.obtenerAlumno(Integer.parseInt(txtCodAlumno.getText()));
            if (alumno == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ESTE ALUMNO NO EXISTE");
                alert.setHeaderText(null);
                alert.showAndWait();
                limpiarPrestamo();
            }else{
                lbAlumno.setText(alumno.getNombre());
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DEBES SELECCIONAR UN ALUMNO");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    public void buscarJuego(){
        if(txtCodJuego.getText().length()!=0){
            Juego juego=BDController.obtenerJuego(Integer.parseInt(txtCodJuego.getText()));
            if (juego == null){
                limpiarPrestamo();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ESTE JUEGO NO EXISTE");
                alert.setHeaderText(null);
                alert.showAndWait();

            }else{
                lbJuego.setText(juego.getNombre());
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DEBES SELECCIONAR UN JUEGO");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    public void generarFechas(){
        LocalDateTime fechaEntrega = LocalDateTime.now();

        DateTimeFormatter esDateFormat= DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String textoFecha =fechaEntrega.format(esDateFormat);
        lbFechaEntrega.setText(textoFecha);

        LocalDateTime fechaDevolucion = fechaEntrega.plusDays(3);
        String textoFechaDevolucion = fechaDevolucion.format(esDateFormat);
        lbFechaDevolucion.setText(textoFechaDevolucion);
    }

    public void botonAceptarPrestamoClick(){
        if(txtCodAlumno.getText().length()!=0 && txtCodJuego.getText().length()!=0 && lbAlumno.getText().length()!=0 && lbJuego.getText().length()!=0){
            Prestamo prestamo = rellenaPrestamo();

            Connection connection = null;

            //--------BASE DE DATOS-----------------------


            try {
                connection = getConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (BDController.insertarPrestamo(prestamo, connection)) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("TODO OK");
                            alert.setContentText("SE HA GUARDADO EL PRÉSTAMO");
                            alert.setHeaderText(null);
                            alert.showAndWait();
                            grabarFicheroTextoPrestamos(connection);
                            limpiarPrestamo();
                        }else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("NO SE HA PODIDO INSERTAR");
                            alert.setHeaderText(null);
                            alert.showAndWait();
                        }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FALTAN CAMPOS POR RELLENAR O CAMPO INCORRECTO");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    //RELLENAR PRÉSTAMO
    private Prestamo rellenaPrestamo() {
            int codAlumno=Integer.parseInt(txtCodAlumno.getText());
            int codJuego=Integer.parseInt(txtCodJuego.getText());
            LocalDateTime fechaEntrega = LocalDateTime.now();
            LocalDateTime fechaDevolucion = fechaEntrega.plusDays(3);

            Prestamo prestamo = new Prestamo(codAlumno, codJuego, fechaEntrega, fechaDevolucion);

            return prestamo;
        }

    //LIMPIAR PRÉSTAMO
    private void limpiarPrestamo(){
        txtCodJuego.clear();
        txtCodAlumno.clear();
        lbAlumno.setText("");
        lbJuego.setText("");
        lbFechaEntrega.setText("");
        lbFechaDevolucion.setText("");
    }

    public void grabarFicheroTextoPrestamos(Connection connection){
        try {
            connection = getConnection();
            FileController.escribirFicheroPrestamos(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void ValidarAlumnoJuego (){
        generarFechas();
        buscarAlumno();
        int filas=buscarJuegosPrestados(Integer.parseInt(txtCodAlumno.getText()));
        if (filas>=3){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("EXCESO DE PRÉSTAMOS");
            alert.setHeaderText(null);
            alert.showAndWait();
            limpiarPrestamo();
        }else{
            buscarJuego();
        }

    }

    public static  int buscarJuegosPrestados(int idAlumno){
        Connection connection = null;
        int total=0;
        try {
            connection = getConnection();
            String query = "select * from prestamos where idAlumno = ?;";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, idAlumno);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                total++;
            }
            return total;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
    }

    //------------------------CONTROLAR PRÉSTAMOS-----------------------------------------------------------------------


    public void validarAlumnoPrestamo(){
        if(txtCodigoAlumno.getText().length()!=0){
            Alumno alumno=BDController.obtenerAlumno(Integer.parseInt(txtCodigoAlumno.getText()));
            if (alumno == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ESTE ALUMNO NO EXISTE");
                alert.setHeaderText(null);
                alert.showAndWait();
            }else{
                lbNombreControlPrestamos.setText(alumno.getNombre());
                lbApellidosControlPrestamos.setText(alumno.getApellido());
                buscarJuegosPrestadosControl(Integer.parseInt(txtCodigoAlumno.getText()));
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DEBES SELECCIONAR UN ALUMNO");
            alert.setHeaderText(null);
            alert.showAndWait();
            limpiar();
        }

    }

    public void buscarJuegosPrestadosControl(int idAlumno){
        Connection connection = null;
        int total=0;

        try {
            connection = getConnection();
            String query = "SELECT nombre, fechaDevolucion, idPrestamo FROM prestamos p inner join juegos j on p.idJuego = j.idJuego where idAlumno=?;";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, idAlumno);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                total++;
                switch (total){
                    case 1:
                        lbJuego1.setText(rs.getString(1)+" F.Devolución: "+rs.getString(2));
                        idPrestamo1 = rs.getInt(3);
                        break;
                    case 2:
                        lbjuego2.setText(rs.getString(1)+" F.Devolución: "+rs.getString(2));
                        idPrestamo2 = rs.getInt(3);
                        break;
                    case 3:
                        lbJuego3.setText(rs.getString(1)+" F.Devolución: "+rs.getString(2));
                        idPrestamo3 = rs.getInt(3);
                    break;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void prorrogarJuego(int id, Connection connection){
        try {
            connection = getConnection();
            String query = "SELECT fechaEntrega, fechaDevolucion FROM prestamos where idPrestamo=?;";
            PreparedStatement pstm = connection.prepareStatement(query);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()){
                LocalDateTime fechaEntrega = rs.getObject(1, LocalDateTime.class);
                LocalDateTime fechaDevolucion = rs.getObject(2, LocalDateTime.class);
                long dias = ChronoUnit.DAYS.between(fechaEntrega, fechaDevolucion);
                System.out.println(dias);
                //if ()

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }




}

