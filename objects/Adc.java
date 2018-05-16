package objects;

/**
 *
 * @author Root
 */
public class Adc {
    private int ID, SUCURSAL, ID_STAFF, AGENCIA, VACANTE, NIVEL;

    public Adc(int ID, int SUCURSAL, int ID_STAFF, int AGENCIA, int VACANTE, int NIVEL) {
        this.ID = ID;
        this.SUCURSAL = SUCURSAL;
        this.ID_STAFF = ID_STAFF;
        this.AGENCIA = AGENCIA;
        this.VACANTE = VACANTE;
        this.NIVEL = NIVEL;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSUCURSAL() {
        return SUCURSAL;
    }

    public void setSUCURSAL(int SUCURSAL) {
        this.SUCURSAL = SUCURSAL;
    }

    public int getID_STAFF() {
        return ID_STAFF;
    }

    public void setID_STAFF(int ID_STAFF) {
        this.ID_STAFF = ID_STAFF;
    }

    public int getAGENCIA() {
        return AGENCIA;
    }

    public void setAGENCIA(int AGENCIA) {
        this.AGENCIA = AGENCIA;
    }

    public int getVACANTE() {
        return VACANTE;
    }

    public void setVACANTE(int VACANTE) {
        this.VACANTE = VACANTE;
    }

    public int getNIVEL() {
        return NIVEL;
    }

    public void setNIVEL(int NIVEL) {
        this.NIVEL = NIVEL;
    }

    @Override
    public String toString() {
        return "Adc{" + "ID=" + ID + ", SUCURSAL=" + SUCURSAL + ", ID_STAFF=" + ID_STAFF + ", AGENCIA=" + AGENCIA + ", VACANTE=" + VACANTE + ", NIVEL=" + NIVEL + '}';
    }

            
}
