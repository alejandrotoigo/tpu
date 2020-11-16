package negocio;

import util.TextFile;

import java.util.Collection;

public class Regiones {
    //Archivo cargado con la info completa
    private TextFile fileRegiones;

    private Region pais;

    public Regiones(String path) {
        fileRegiones = new TextFile(path + "\\descripcion_regiones.dsv");
        pais = fileRegiones.identificarRegiones();
    }

    public Collection getDistritos() {
        return pais.getSubregiones();
    }




}
