package objects;

/**
 *
 * @author Root
 */
public class Adc {
    private int ID = 0, SUCURSAL = 0, ID_EMPLEADO = 0, AGENCIA = 0, VACANTE = 0, NIVEL = 0;

    public Adc(int ID, int SUCURSAL, int ID_EMPLEADO, int AGENCIA, int VACANTE, int NIVEL) {
        this.ID = ID;
        this.SUCURSAL = SUCURSAL;
        this.ID_EMPLEADO = ID_EMPLEADO;
        this.AGENCIA = AGENCIA;
        this.VACANTE = VACANTE;
        this.NIVEL = NIVEL;
    }
    
    public Adc(){}

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

    public int getID_EMPLEADO() {
        return ID_EMPLEADO;
    }

    public void setID_EMPLEADO(int ID_EMPLEADO) {
        this.ID_EMPLEADO = ID_EMPLEADO;
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
        return "Adc{" + "ID=" + ID + ", SUCURSAL=" + SUCURSAL + ", ID_EMPLEADO=" + ID_EMPLEADO + ", AGENCIA=" + AGENCIA + ", VACANTE=" + VACANTE + ", NIVEL=" + NIVEL + '}';
    }

            
}
