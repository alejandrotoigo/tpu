package interfaz;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import negocio.Agrupaciones;
import negocio.Region;
import negocio.Regiones;
import negocio.Resultados;

import java.awt.event.MouseListener;
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
        ObservableList ol;
        //Generamos lista de agrupaciones
        
        lblCargaDatosInfo.setText("Cargando Datos al sistema. Por favor espere.");
        Agrupaciones.leerAgrupaciones(lblUbicacion.getText());
//HASTA ACA BIEN - PROBLEMAS EN LA CARGA DE DISTRITOS Y POR ENDE EN LA CARGA DE REGIONES, PERO NO DE CIRCUITOS
        //Generamos lista de distritos del país
        Regiones regiones = new Regiones(lblUbicacion.getText());
        ol = FXCollections.observableArrayList(regiones.getDistritos());
        cboCircuitos.setDisable(false);
        cboSecciones.setDisable(false);
        cboDistritos.setDisable(false);
        lvwResultados.setDisable(false);
        cboDistritos.setItems(ol);
        //Procesamos los totales por región
        resultados = new Resultados(lblUbicacion.getText());
        //System.out.println(resultados);
        ol = FXCollections.observableArrayList(resultados.getResultadosRegion("00"));
        lvwResultados.setItems(ol);
        lblCargaDatosInfo.setText("Datos Cargados en el sistema.");
        btnCambiar.setVisible(false);


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

    // ***********controlado desde acá hasta el final***********
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
            if (ol.size()==0)
                lvwResultados.setVisible(false);
            else
                lvwResultados.setVisible(true);
            lvwResultados.setItems(ol);

        } else {
            cboCircuitos.setItems(null);
        }
    }



}
