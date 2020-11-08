package interfaz;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
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
        cboDistritos.setItems(ol);
        //Procesamos los totales por región
        resultados = new Resultados(lblUbicacion.getText());
        //System.out.println(resultados);
        ol = FXCollections.observableArrayList(resultados.getResultadosRegion("00"));
        lvwResultados.setItems(ol);
        lblCargaDatosInfo.setText("Datos Cargados en el sistema.");

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
            //Mostramos resultados de la sección
            ol = FXCollections.observableArrayList(resultados.getResultadosRegion(seccion.getCodigo()));
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
            lvwResultados.setItems(ol);
        } else {
            cboCircuitos.setItems(null);
        }
    }
}
