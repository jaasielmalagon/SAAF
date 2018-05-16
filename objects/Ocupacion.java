package objects;

/**
 *
 * @author JMalagon
 */
public class Ocupacion {
    private final int ID;
    private final String OCUPACION;
    
    public Ocupacion(int id, String ocupacion){
        this.ID = id;
        this.OCUPACION = ocupacion;
    }
    
    public int getId(){
        return ID;
    }
    
    public String getOcupacion(){
        return OCUPACION;
    }
    
    @Override
    public String toString(){
        return OCUPACION;
    }
}
