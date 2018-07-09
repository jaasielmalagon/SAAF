package objects;

/**
 *
 * @author Root
 */
public class Lista {
    private int ID;
    private String STRING, STRING2;

    public Lista(int ID, String STRING) {
        this.ID = ID;
        this.STRING = STRING;
    }
    
    public Lista(int ID, String STRING, String STRING2) {
        this.ID = ID;
        this.STRING = STRING;
        this.STRING2 = STRING2;
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
    
    public String getSTRING2() {
        return STRING2;
    }

    public void setSTRING2(String STRING2) {
        this.STRING2 = STRING2;
    }

    @Override
    public String toString() {
        return STRING;
    }
    
}
