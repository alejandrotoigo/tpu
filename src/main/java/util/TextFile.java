package util;

import modelos.TSB_OAHashtable;
import negocio.Agrupacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextFile {
    private File archivo;


    public TextFile(String path) {
        this.archivo = new File(path);



    }

    public String leerEncabezado() {

        String linea = "";
        try {
            Scanner scanner = new Scanner(archivo);
            while (scanner.hasNext()) {
                linea = scanner.nextLine();
                break;
            }

        } catch (FileNotFoundException e) {
            System.out.println("No se pudo leer el Archivo");
        }
        return linea;


    }

    public TSB_OAHashtable identificarAgrupacione() {
        String linea = "";
        TSB_OAHashtable table = new TSB_OAHashtable(10);

        try {
            Scanner scanner = new Scanner(archivo);
            while (scanner.hasNext()) {
                linea = scanner.nextLine();
                String[] campos = linea.split("\\|");
                if (campos[0].compareTo("000100000000000") == 0){
                    Agrupacion agrupacion = new Agrupacion(campos[2],campos[3]);
                    table.put(agrupacion.getCodigo(),agrupacion);

                }


                
            }

        } catch (FileNotFoundException e) {
            System.out.println("No se pudo leer el Archivo");
        }
        return table;

    }
}
