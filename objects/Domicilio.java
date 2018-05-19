package objects;

/**
 *
 * @author JMalagon
 */
public class Domicilio {
    private int ID, TIPO;    
    private String DIRECCION, LATITUD, LONGITUD, PROPIETARIO, VIGENCIA, TIEMPO_RESIDENCIA;

    public Domicilio(int ID, int TIPO, String DIRECCION, String LATITUD, String LONGITUD, String PROPIETARIO, String VIGENCIA, String TIEMPO_RESIDENCIA) {
        this.ID = ID;
        this.TIPO = TIPO;
        this.DIRECCION = DIRECCION;
        this.LATITUD = LATITUD;
        this.LONGITUD = LONGITUD;
        this.PROPIETARIO = PROPIETARIO;
        this.VIGENCIA = VIGENCIA;
        this.TIEMPO_RESIDENCIA = TIEMPO_RESIDENCIA;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getTIPO() {
        return TIPO;
    }

    public void setTIPO(int TIPO) {
        this.TIPO = TIPO;
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

    public String getPROPIETARIO() {
        return PROPIETARIO;
    }

    public void setPROPIETARIO(String PROPIETARIO) {
        this.PROPIETARIO = PROPIETARIO;
    }

    public String getVIGENCIA() {
        return VIGENCIA;
    }

    public void setVIGENCIA(String VIGENCIA) {
        this.VIGENCIA = VIGENCIA;
    }

    public String getTIEMPO_RESIDENCIA() {
        return TIEMPO_RESIDENCIA;
    }

    public void setTIEMPO_RESIDENCIA(String TIEMPO_RESIDENCIA) {
        this.TIEMPO_RESIDENCIA = TIEMPO_RESIDENCIA;
    }

    @Override
    public String toString() {
        return "Domicilio{" + "ID=" + ID + ", TIPO=" + TIPO + ", DIRECCION=" + DIRECCION + ", LATITUD=" + LATITUD + ", LONGITUD=" + LONGITUD + ", PROPIETARIO=" + PROPIETARIO + ", VIGENCIA=" + VIGENCIA + ", TIEMPO_RESIDENCIA=" + TIEMPO_RESIDENCIA + '}';
    }
    
    
}
