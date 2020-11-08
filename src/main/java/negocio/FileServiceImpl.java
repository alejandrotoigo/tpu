package negocio;

import excepciones.ServiceException;
import persistencia.AgrupacionDAO;

import java.io.File;
import java.util.Scanner;

public class FileServiceImpl implements FileService {

    public static final String CODIGO_CATEGORIA_PRESIDENTE_VICEPRESIDENTE = "000100000000000";

    @Override
    public void readFiles(String path) throws ServiceException {
        File archivo = new File(path);

        // Validamos que se haya seleccionada
        // un directorio y no un archivo
        if(!archivo.isDirectory()) throw new ServiceException("El archivo seleccionado no es un directorio");

        leerAgrupaciones(archivo);
    }

    // Metodo encargado de leer todas las agrupaciones y persistir en la base de datos
    public void leerAgrupaciones(File archivo) throws ServiceException {
        File file = new File(archivo.getPath() + "/" + "descripcion_postulaciones.dsv");

        // Validamos que exista el archivo
        if(!file.exists()) throw new ServiceException("El archivo descripcion_postulaciones.dsv no existe en el directorio seleccionado");

        AgrupacionDAO agrupacionDAO = new AgrupacionDAO();
        Scanner scanner = null;
        try {
            String linea = "";
            scanner = new Scanner(file);
            // Arrancamos la transaccion para persistir cada agrupacion encontrada
            agrupacionDAO.beginTransaction();
            while (scanner.hasNext()) {
                linea = scanner.nextLine();
                String[] campos = linea.split("\\|");
                if (campos[0].compareTo(CODIGO_CATEGORIA_PRESIDENTE_VICEPRESIDENTE) == 0){
                    Agrupacion agrupacion = new Agrupacion((campos[2]),campos[3]);
                    // Persistimos en la bbdd
                    agrupacionDAO.persistSinTransaccion(agrupacion);
                }
            }
            // Confirmamos la transaccion
            agrupacionDAO.commit();

        } catch (Exception e) {
            agrupacionDAO.rollback();
            throw new ServiceException("No se pudo leer el Archivo");
        } finally {
            if(scanner != null) scanner.close();
        }
    }
}
