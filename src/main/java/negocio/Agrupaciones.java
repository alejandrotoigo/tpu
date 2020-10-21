package negocio;

import modelos.TSB_OAHashtable;
import util.TextFile;

public class Agrupaciones {
    private TextFile textfileagrupaciones;
    private TSB_OAHashtable table;

    public Agrupaciones (String path){
        this.textfileagrupaciones = new TextFile(path + "/descripcion_postulaciones.dsv");
        table = textfileagrupaciones.identificarAgrupacione();

    };

}
