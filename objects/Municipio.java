package objects;

/**
 *
 * @author JMalagon
 * Esta clase estpa incluida para trabajar con la información obtenida directamente
 * de la base de datos referente a los muncipios.
 * Contiene los métodos get y set necesarios para manipular los datos de los municipios en la base de datos.
 */
public class Municipio {

    private String nombreMunicipio;
    private int idMunicipio;
    private int idEstado;
    
    public Municipio(int idMunicipio, String nombreMunicipio, int idEstado) {
        this.idMunicipio = idMunicipio;
        this.nombreMunicipio = nombreMunicipio;
        this.idEstado = idEstado;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }
    
    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public String toString() {
        return nombreMunicipio;
    }
}
