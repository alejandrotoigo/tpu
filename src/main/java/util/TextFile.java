package util;

import negocio.Agrupacion;
import negocio.Region;
import negocio.Resultados;
import modelos.TSBHashtableDA;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextFile {
    private File file;

    public TextFile(String path) {
        file = new File(path);
    }

    public String leerEncabezado() {
        String linea = "";
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                linea = scanner.nextLine();
                break;
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo leer el archivo");
        }
        return linea;
    }

    public TSBHashtableDA identificarAgrupaciones() {
        String linea = "", campos[];
        TSBHashtableDA table = new TSBHashtableDA(10);
        Agrupacion agrupacion;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                linea = scanner.nextLine();
                campos = linea.split("\\|");
                //Filtramos votación para Presidente
                if (campos[0].compareTo("000100000000000") == 0) {
                    agrupacion = new Agrupacion((campos[2]), campos[3]);
                    table.put(agrupacion.getCodigo(), agrupacion);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo leer el archivo");
        }
        return table;
    }
    // METODO DE ABAJO CONTROLADO
    public void sumarVotosPorRegion(Resultados resultados) {
        String linea = "", campos[], codAgrupacion;

        int votos;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                linea = scanner.nextLine();
                campos = linea.split("\\|");
                codAgrupacion = campos[5];
                //Filtramos votación para Presidente
                    if (campos[4].compareTo("000100000000000") == 0) {
                    votos = Integer.parseInt(campos[6]);
                    //Acumulamos los votos del pais
                    resultados.sumarVotos("00",codAgrupacion,votos);
                    //Acumulamos los votos del distrito, seccion y circuito de la mesa
                    //resultados.sumarVotos(campos[0],campos[5],votos);
                    for(int i = 0; i<3; i++){
                        resultados.sumarVotos(campos[i],codAgrupacion,votos);

                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo leer el archivo");
        }
    }

    public void sumarVotosPorAgrupacion(TSBHashtableDA table) {
        String linea = "", campos[];
        Agrupacion agrupacion;
        int votos;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                linea = scanner.nextLine();
                campos = linea.split("\\|");
                //Filtramos votación para Presidente
                if (campos[4].compareTo("000100000000000") == 0) {
                    agrupacion = (Agrupacion) table.get(campos[5]);
                    votos = Integer.parseInt(campos[6]);
                    agrupacion.sumarVotos(votos);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo leer el archivo");
        }
    }
    //METODO DE ABAJO CONTROLADO
    public Region identificarRegiones() {
        String linea = "", campos[], codigo, nombre;
        Region pais = new Region("00", "Argentina");
        Region distrito, seccion;

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                linea = scanner.nextLine();
                campos = linea.split("\\|");

                codigo = campos[0];
    //CONTROLADO HASTA ACA

                nombre = campos[1];
                switch(codigo.length()){
                    case 2:
                        //Distrito
                            distrito = pais.getOrPutSubregion(codigo);
                            distrito.setNombre(nombre);
                            break;
                    case 5:
                        //Seccion
                            distrito = pais.getOrPutSubregion(codigo.substring(0,2));
                            seccion = distrito.getOrPutSubregion(codigo);
                            seccion.setNombre(nombre);
                            break;
                    case 11:
                        //Circuito
                        distrito = pais.getOrPutSubregion(codigo.substring(0,2));
                        seccion = distrito.getOrPutSubregion(codigo.substring(0,5));
                        seccion.agregarSubregion(new Region(codigo,nombre));

                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo leer el archivo");
        }
        return pais;
    }


}

