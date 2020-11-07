package negocio;

import excepciones.ServiceException;

public interface FileService {

    /**
     * Metodo encargado de leer los archivos del directorio seleccionado por el usuario
     * @param path: ruta del directorio donde se encuentran los archivos
     */
    public void readFiles(String path) throws ServiceException;
}
