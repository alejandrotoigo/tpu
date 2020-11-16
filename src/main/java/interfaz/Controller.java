package interfaz;

import excepciones.ServiceException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import negocio.Agrupaciones;
import negocio.Region;
import negocio.Regiones;
import negocio.Resultados;

import java.io.File;

public class Controller {
    public Label lblUbicacion;
    public ListView lvwResultados;
    public ComboBox cboDistritos;
    public ComboBox cboSecciones;
    public ComboBox cboCircuitos;
    public Resultados resultados;
    public Label lblCargaDatosInfo;
    public Button btnCambiar;
    public Button btnCargar;

    public void cambiarUbicacion(ActionEvent actionEvent) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Seleccione ubicación de los datos");
        dc.setInitialDirectory(new File("C:\\Users"));
        //dc.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = dc.showDialog(null);
        if (file != null)
            lblUbicacion.setText(file.getPath());
    }

// *********hasta acá controlado*********

    public void cargarDatos(ActionEvent actionEvent) {
        lblUbicacion.getScene().setCursor(Cursor.WAIT);
        final ObservableList[] ol = new ObservableList[2];

        btnCambiar.setDisable(true);
        btnCargar.setDisable(true);

        lblCargaDatosInfo.setText("Cargando Datos al sistema. Por favor espere.");
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() {
                File directorio = new File(lblUbicacion.getText());

                try {
                    // Validamos que se haya seleccionada
                    // un directorio y no un archivo
                    if (!directorio.isDirectory()) {
                        throw new ServiceException("El directorio seleccionado no es válido");
                    }


                    //Generamos lista de agrupaciones
                    Agrupaciones.leerAgrupaciones(lblUbicacion.getText());

                    //Generamos lista de distritos del país
                    Regiones regiones = new Regiones(lblUbicacion.getText());
                    ol[0] = FXCollections.observableArrayList(regiones.getDistritos());

                    //Procesamos los totales por región
                    resultados = new Resultados(lblUbicacion.getText());

                    ol[1] = FXCollections.observableArrayList(resultados.getResultadosRegion("00"));

                } catch (ServiceException se) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText(se.getMessage());
                        alert.showAndWait();
                    });
                    this.cancel(true);
                }

                return null;
            }
        };
        task.setOnSucceeded(e -> {
            cboCircuitos.setDisable(false);
            cboSecciones.setDisable(false);
            cboDistritos.setDisable(false);
            lvwResultados.setDisable(false);
            cboDistritos.setItems(ol[0]);
            lvwResultados.setItems(ol[1]);
            lblCargaDatosInfo.setText("Datos Cargados en el sistema.");
            lblUbicacion.getScene().setCursor(Cursor.DEFAULT);
        });
        task.setOnCancelled(e -> {
            lblCargaDatosInfo.setText("");
            btnCambiar.setDisable(false);
            btnCargar.setDisable(false);
            lblUbicacion.getScene().setCursor(Cursor.DEFAULT);
        });
        new Thread(task).start();


    }

    public void elegirDistrito(ActionEvent actionEvent) {
        //Generamos lista de secciones del distrito elegido
        ObservableList ol;

        Region distrito = (Region) cboDistritos.getValue();
        ol = FXCollections.observableArrayList(distrito.getSubregiones());
        cboSecciones.setItems(ol);
        //Mostramos resultados del Distrito
        ol = FXCollections.observableArrayList(resultados.getResultadosRegion(distrito.getCodigo()));
        lvwResultados.setItems(ol);

    }

    public void elegirSeccion(ActionEvent actionEvent) {
        ObservableList ol;
        //Genera una lista de circuitos de la sección elegida
        if (cboSecciones.getValue() != null) {
            Region seccion = (Region) cboSecciones.getValue();
            ol = FXCollections.observableArrayList(seccion.getSubregiones());
            cboCircuitos.setItems(ol);
            //intento que vuelva a cargar
            if (cboCircuitos.getItems() == null)
                cboCircuitos.setItems(ol);
            //Mostramos resultados de la sección
            ol = FXCollections.observableArrayList(resultados.getResultadosRegion(seccion.getCodigo()));
            lvwResultados.setVisible(true);
            lvwResultados.setItems(ol);
        } else {
            cboCircuitos.setItems(null);
        }
    }

    public void elegirCircuito(ActionEvent actionEvent) {
        ObservableList ol;
        //Genera una lista de circuitos de la sección elegida
        if (cboCircuitos.getValue() != null) {
            Region circuito = (Region) cboCircuitos.getValue();
            //Mostramos resultados del circuito
            ol = FXCollections.observableArrayList(resultados.getResultadosRegion(circuito.getCodigo()));
            if (ol.size() == 0)
                lvwResultados.setVisible(false);
            else
                lvwResultados.setVisible(true);
            lvwResultados.setItems(ol);

        } else {
            cboCircuitos.setItems(null);
        }
    }


}
