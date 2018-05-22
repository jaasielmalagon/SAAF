package objects;

/**
 *
 * @author JMalagon
 */
public class Calle {

    private String nombreCalle;
    private int idCalle = 0;
    private int idColonia = 0;
    
    public Calle(int idCalle, String nombreCalle, int idColonia) {
        this.idCalle = idCalle;
        this.nombreCalle = nombreCalle;
        this.idColonia = idColonia;
    }

    public String getNombreDeCalle() {
        return nombreCalle;
    }

    public void setNombreDeCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

    public int getIdCalle() {
        return idCalle;
    }

    public void setIdCalle(int idCalle) {
        this.idCalle = idCalle;
    }
    
    public int getIdColonia() {
        return idColonia;
    }

    public void setIdColonia(int idColonia) {
        this.idColonia = idColonia;
    }

    @Override
    public String toString() {
        return nombreCalle;
    }
}
