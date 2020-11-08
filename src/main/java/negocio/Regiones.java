package negocio;

import util.TextFile;

import java.util.Collection;

public class Regiones {
    //Archivo cargado con la info completa
    private TextFile fileRegiones;

    private Region pais;

    public Regiones(String path) {
        fileRegiones = new TextFile(path + "\\descripcion_regiones.dsv");

        //fileMesas = new TextFile(path + "\\mesas_totales_agrp_politica.dsv");
        pais = fileRegiones.identificarRegiones();
        //fileMesas.sumarVotosPorAgrupacion(table);
    }

    public Collection getDistritos() {
        return pais.getSubregiones();
    }


}
