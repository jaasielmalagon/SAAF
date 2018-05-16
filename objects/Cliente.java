package objects;

/**
 *
 * @author JMalagon
 */
public class Cliente {

    private final int ID, SUCURSAL, USUARIO, ID_PERSONA, DEPENDIENTES, OCUPACION, ESTUDIOS, SCORE, STATUS, ACTIVIDAD;
    private final String F_REGISTRO, EMPRESA, DOMICILIO_EMPRESA, TEL_EMPRESA, HORA_ENTRADA, HORA_SALIDA, ADC;
    private final double INGRESOS, EGRESOS;
    private final Persona PERSONA;
    
    public Cliente(
            int ID, int SUCURSAL, int USUARIO,
            String F_REGISTRO, String ADC, int ID_PERSONA,
            double INGRESOS, double EGRESOS,
            int DEPENDIENTES, int OCUPACION, int ESTUDIOS,
            String EMPRESA, String DOMICILIO_EMPRESA,
            String TEL_EMPRESA, String HORA_ENTRADA, String HORA_SALIDA,
            int SCORE, int STATUS, int ACTIVIDAD, Persona persona) {
        this.ID = ID;
        this.SUCURSAL = SUCURSAL;
        this.USUARIO = USUARIO;
        this.ADC = ADC;
        this.ID_PERSONA = ID_PERSONA;
        this.DEPENDIENTES = DEPENDIENTES;
        this.OCUPACION = OCUPACION;
        this.ESTUDIOS = ESTUDIOS;
        this.SCORE = SCORE;
        this.STATUS = STATUS;
        this.ACTIVIDAD = ACTIVIDAD;
        this.F_REGISTRO = F_REGISTRO;
        this.EMPRESA = EMPRESA;
        this.DOMICILIO_EMPRESA = DOMICILIO_EMPRESA;
        this.TEL_EMPRESA = TEL_EMPRESA;
        this.HORA_ENTRADA = HORA_ENTRADA;
        this.HORA_SALIDA = HORA_SALIDA;
        this.INGRESOS = INGRESOS;
        this.EGRESOS = EGRESOS;
        this.PERSONA = persona;
    }

    public int getIdCliente() {
        return ID;
    }

    public int getSUCURSAL() {
        return SUCURSAL;
    }

    public int getUSUARIO() {
        return USUARIO;
    }

    public String getADC() {
        return ADC;
    }

    public int getID_PERSONA() {
        return ID_PERSONA;
    }

    public int getDEPENDIENTES() {
        return DEPENDIENTES;
    }

    public int getOCUPACION() {
        return OCUPACION;
    }

    public int getESTUDIOS() {
        return ESTUDIOS;
    }

    public int getSCORE() {
        return SCORE;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public int getACTIVIDAD() {
        return ACTIVIDAD;
    }

    public String getF_REGISTRO() {
        return F_REGISTRO;
    }

    public String getEMPRESA() {
        return EMPRESA;
    }

    public String getDOMICILIO_EMPRESA() {
        return DOMICILIO_EMPRESA;
    }

    public String getTEL_EMPRESA() {
        return TEL_EMPRESA;
    }

    public String getHORA_ENTRADA() {
        return HORA_ENTRADA;
    }

    public String getHORA_SALIDA() {
        return HORA_SALIDA;
    }

    public double getINGRESOS() {
        return INGRESOS;
    }

    public double getEGRESOS() {
        return EGRESOS;
    }
    
    public Persona getPersona(){
        return PERSONA;
    }
    
    @Override
    public String toString(){
        return PERSONA.getNombre() + " " + PERSONA.getApaterno() + " " + PERSONA.getAmaterno();
    }

}
