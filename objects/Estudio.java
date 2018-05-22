package objects;

/**
 *
 * @author JMalagon
 */
public class Estudio {
    private final int ID;
    private final String NIVEL;

    public Estudio(int id, String nivel) {
        this.ID = id;
        this.NIVEL = nivel;
    }

    public int getID() {
        return ID;
    }

    public String getNivel() {
        return NIVEL;
    }        
    
    @Override
    public String toString(){
        return NIVEL;
    }
}
