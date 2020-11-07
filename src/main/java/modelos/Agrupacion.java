package modelos;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Agrupacion {
    @Id
    int codAgrupacion;
    String nombreAgrupacion;

    public Agrupacion() {
    }

    public Agrupacion(int codAgrupacion, String nombreAgrupacion) {
        this.codAgrupacion = codAgrupacion;
        this.nombreAgrupacion = nombreAgrupacion;
    }

    public int getCodAgrupacion() {
        return codAgrupacion;
    }

    public void setCodAgrupacion(int codAgrupacion) {
        this.codAgrupacion = codAgrupacion;
    }

    public String getNombreAgrupacion() {
        return nombreAgrupacion;
    }

    public void setNombreAgrupacion(String nombreAgrupacion) {
        this.nombreAgrupacion = nombreAgrupacion;
    }
}
