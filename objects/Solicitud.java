package objects;

/**
 *
 * @author Root
 */
public class Solicitud {
    private int ID, MONTO, PLAZO, CLIENTE, USUARIO, SUCURSAL;
    private double TASA;
    private String FECHA, HORA;

    public Solicitud(int ID, int MONTO, int PLAZO, int CLIENTE, int USUARIO, int SUCURSAL, double TASA, String FECHA, String HORA) {
        this.ID = ID;
        this.MONTO = MONTO;
        this.PLAZO = PLAZO;
        this.CLIENTE = CLIENTE;
        this.USUARIO = USUARIO;
        this.SUCURSAL = SUCURSAL;
        this.TASA = TASA;
        this.FECHA = FECHA;
        this.HORA = HORA;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMONTO() {
        return MONTO;
    }

    public void setMONTO(int MONTO) {
        this.MONTO = MONTO;
    }

    public int getPLAZO() {
        return PLAZO;
    }

    public void setPLAZO(int PLAZO) {
        this.PLAZO = PLAZO;
    }

    public int getCLIENTE() {
        return CLIENTE;
    }

    public void setCLIENTE(int CLIENTE) {
        this.CLIENTE = CLIENTE;
    }

    public int getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(int USUARIO) {
        this.USUARIO = USUARIO;
    }

    public int getSUCURSAL() {
        return SUCURSAL;
    }

    public void setSUCURSAL(int SUCURSAL) {
        this.SUCURSAL = SUCURSAL;
    }

    public double getTASA() {
        return TASA;
    }

    public void setTASA(double TASA) {
        this.TASA = TASA;
    }

    public String getFecha() {
        return FECHA;
    }

    public void setFecha(String fecha) {
        this.FECHA = fecha;
    }
    
    public String getHORA() {
        return HORA;
    }

    public void setHORA(String HORA) {
        this.HORA = HORA;
    }
    
    @Override
    public String toString(){
        return ""+ID;
    }
}
