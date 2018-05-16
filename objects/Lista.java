package objects;

/**
 *
 * @author Root
 */
public class Lista {
    private int ID;
    private String STRING;

    public Lista(int ID, String STRING) {
        this.ID = ID;
        this.STRING = STRING;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSTRING() {
        return STRING;
    }

    public void setSTRING(String STRING) {
        this.STRING = STRING;
    }

    @Override
    public String toString() {
        return STRING;
    }
    
}
