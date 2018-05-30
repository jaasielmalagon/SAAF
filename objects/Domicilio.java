package objects;

/**
 *
 * @author JMalagon
 */
public class Domicilio {
    private int ID = 0;   
    private String DIRECCION, LATITUD, LONGITUD;

    public Domicilio(int ID, String DIRECCION, String LATITUD, String LONGITUD) {
        this.ID = ID;        
        this.DIRECCION = DIRECCION;
        this.LATITUD = LATITUD;
        this.LONGITUD = LONGITUD;        
    }
    
    public Domicilio(){
        
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDIRECCION() {
        return DIRECCION;
    }

    public void setDIRECCION(String DIRECCION) {
        this.DIRECCION = DIRECCION;
    }

    public String getLATITUD() {
        return LATITUD;
    }

    public void setLATITUD(String LATITUD) {
        this.LATITUD = LATITUD;
    }

    public String getLONGITUD() {
        return LONGITUD;
    }

    public void setLONGITUD(String LONGITUD) {
        this.LONGITUD = LONGITUD;
    }    

    @Override
    public String toString() {
        return "Domicilio{" + "ID=" + ID + ", DIRECCION=" + DIRECCION + ", LATITUD=" + LATITUD + ", LONGITUD=" + LONGITUD + '}';
    }        
    
}
