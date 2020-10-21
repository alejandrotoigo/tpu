package negocio;

public class Agrupacion {
    private String codigo;
    private String nombre;
    private int votos;


    public Agrupacion(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.votos =0;
    }

    public String getCodigo() {
        return codigo;
    }
}
