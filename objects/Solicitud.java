package objects;

/**
 *
 * @author Root
 */
public class Solicitud {

    private int ID, MONTO, PLAZO, CLIENTE, USUARIO, SUCURSAL, ESTADO;
    private double TASA;
    private String FECHA, HORA;

    public Solicitud(int ID, int MONTO, int PLAZO, int CLIENTE, int USUARIO, int SUCURSAL, double TASA, String FECHA, String HORA, int ESTADO) {
        this.ID = ID;
        this.MONTO = MONTO;
        this.PLAZO = PLAZO;
        this.CLIENTE = CLIENTE;
        this.USUARIO = USUARIO;
        this.SUCURSAL = SUCURSAL;
        this.TASA = TASA;
        this.FECHA = FECHA;
        this.HORA = HORA;
        this.ESTADO = ESTADO;
    }

    public Solicitud() {

    }

    public void setID(String var) {
        try {
            this.ID = Integer.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        }
    }

    public void setMONTO(String var) {
        try {
            this.MONTO = Integer.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        }
    }

    public void setPLAZO(String var) {
        try {
            this.PLAZO = Integer.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        }
    }

    public void setCLIENTE(String var) {
        try {
            this.CLIENTE = Integer.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        }
    }

    public void setUSUARIO(String var) {
        try {
            this.USUARIO = Integer.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        }
    }

    public void setSUCURSAL(String var) {
        try {
            this.SUCURSAL = Integer.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        }
    }

    public void setESTADO(String var) {
        try {
            this.ESTADO = Integer.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        }
    }

    public void setTASA(String var) {
        try {
            this.TASA = Double.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        }
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

    public int getESTADO() {
        return ESTADO;
    }

    public void setESTADO(int ESTADO) {
        this.ESTADO = ESTADO;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    @Override
    public String toString() {
        return "" + ID;
    }
}
