package objects;

/**
 *
 * @author JMalagon
 */
public class Cliente {

    private int ID = 0, SUCURSAL = 0, USUARIO = 0, ID_PERSONA = 0, DEPENDIENTES = 0, OCUPACION = 0, ESTUDIOS = 0, SCORE = 0, STATUS = 0, ACTIVIDAD = 0, TIPO_VIVIENDA = 0;
    //private String ID, SUCURSAL, USUARIO, ID_PERSONA, DEPENDIENTES, OCUPACION, ESTUDIOS, SCORE, STATUS, ACTIVIDAD, TIPO_VIVIENDA;
    private String F_REGISTRO, EMPRESA, DOMICILIO_EMPRESA, TEL_EMPRESA, HORA_ENTRADA, HORA_SALIDA, ADC, PROPIETARIO, VIGENCIA, TIEMPO_RESIDENCIA;
    private double INGRESOS, EGRESOS;
    private Persona PERSONA;

    public Cliente(
            int ID, int SUCURSAL, int USUARIO,
            String F_REGISTRO, String ADC, int ID_PERSONA,
            double INGRESOS, double EGRESOS,
            int DEPENDIENTES, int OCUPACION, int ESTUDIOS,
            String EMPRESA, String DOMICILIO_EMPRESA,
            String TEL_EMPRESA, String HORA_ENTRADA, String HORA_SALIDA,
            int SCORE, int STATUS, int ACTIVIDAD, Persona persona,
            int TIPO_VIVIENDA, String PROPIETARIO, String VIGENCIA, String TIEMPO_RESIDENCIA) {

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
        this.TIPO_VIVIENDA = TIPO_VIVIENDA;
        this.PROPIETARIO = PROPIETARIO;
        this.VIGENCIA = VIGENCIA;
        this.TIEMPO_RESIDENCIA = TIEMPO_RESIDENCIA;
    }
    
    public Cliente(){
        
    }

    public int getID() {
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

    public Persona getPersona() {
        return PERSONA;
    }

    public int getTIPO_VIVIENDA() {
        return TIPO_VIVIENDA;
    }
    
    public String getVIGENCIA() {
        return VIGENCIA;
    }
    
    public String getPROPIETARIO() {
        return PROPIETARIO;
    }
    
    public int getTIEMPO_RESIDENCIA() {
        int t = 0;
        try {
            t = Integer.valueOf(TIEMPO_RESIDENCIA);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        }
        return t;
    }

    @Override
    public String toString() {
        return "Cliente{" + "ID=" + ID + ", SUCURSAL=" + SUCURSAL + ", USUARIO=" + USUARIO + ", ID_PERSONA=" + ID_PERSONA + ", DEPENDIENTES=" + DEPENDIENTES + ", OCUPACION=" + OCUPACION + ", ESTUDIOS=" + ESTUDIOS + ", SCORE=" + SCORE + ", STATUS=" + STATUS + ", ACTIVIDAD=" + ACTIVIDAD + ", TIPO_VIVIENDA=" + TIPO_VIVIENDA + ", F_REGISTRO=" + F_REGISTRO + ", EMPRESA=" + EMPRESA + ", DOMICILIO_EMPRESA=" + DOMICILIO_EMPRESA + ", TEL_EMPRESA=" + TEL_EMPRESA + ", HORA_ENTRADA=" + HORA_ENTRADA + ", HORA_SALIDA=" + HORA_SALIDA + ", ADC=" + ADC + ", PROPIETARIO=" + PROPIETARIO + ", VIGENCIA=" + VIGENCIA + ", TIEMPO_RESIDENCIA=" + TIEMPO_RESIDENCIA + ", INGRESOS=" + INGRESOS + ", EGRESOS=" + EGRESOS + ", PERSONA=" + PERSONA.toString() + '}';
    }
    
    //STRINGS TO INTEGER
    public void setID(int ID) {
        this.ID = ID;
    }        

    public void setSUCURSAL(int SUCURSAL) {
        this.SUCURSAL = SUCURSAL;
    }

    public void setUSUARIO(int USUARIO) {
        this.USUARIO = USUARIO;
    }

    public void setID_PERSONA(int ID_PERSONA) {
        this.ID_PERSONA = ID_PERSONA;
    }

    public void setDEPENDIENTES(int DEPENDIENTES) {
        this.DEPENDIENTES = DEPENDIENTES;
    }

    public void setOCUPACION(int OCUPACION) {
        this.OCUPACION = OCUPACION;
    }

    public void setESTUDIOS(int ESTUDIOS) {
        this.ESTUDIOS = ESTUDIOS;
    }

    public void setSCORE(int SCORE) {
        this.SCORE = SCORE;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public void setACTIVIDAD(int ACTIVIDAD) {
        this.ACTIVIDAD = ACTIVIDAD;
    }

    public void setTIPO_VIVIENDA(int TIPO_VIVIENDA) {
        this.TIPO_VIVIENDA = TIPO_VIVIENDA;
    }

    public void setF_REGISTRO(String F_REGISTRO) {
        this.F_REGISTRO = F_REGISTRO;
    }

    public void setEMPRESA(String EMPRESA) {
        this.EMPRESA = EMPRESA;
    }

    public void setDOMICILIO_EMPRESA(String DOMICILIO_EMPRESA) {
        this.DOMICILIO_EMPRESA = DOMICILIO_EMPRESA;
    }

    public void setTEL_EMPRESA(String TEL_EMPRESA) {
        this.TEL_EMPRESA = TEL_EMPRESA;
    }

    public void setHORA_ENTRADA(String HORA_ENTRADA) {
        this.HORA_ENTRADA = HORA_ENTRADA;
    }

    public void setHORA_SALIDA(String HORA_SALIDA) {
        this.HORA_SALIDA = HORA_SALIDA;
    }

    public void setADC(String ADC) {
        this.ADC = ADC;
    }

    public void setPROPIETARIO(String PROPIETARIO) {
        this.PROPIETARIO = PROPIETARIO;
    }

    public void setVIGENCIA(String VIGENCIA) {
        this.VIGENCIA = VIGENCIA;
    }

    public void setTIEMPO_RESIDENCIA(String TIEMPO_RESIDENCIA) {
        this.TIEMPO_RESIDENCIA = TIEMPO_RESIDENCIA;
    }

    public void setINGRESOS(double INGRESOS) {
        this.INGRESOS = INGRESOS;
    }

    public void setEGRESOS(double EGRESOS) {
        this.EGRESOS = EGRESOS;
    }

    public void setPERSONA(Persona PERSONA) {
        this.PERSONA = PERSONA;
    }
//string    
    public void setID(String var) {
        try {
            this.ID = Integer.valueOf(var);
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

    public void setUSUARIO(String var) {
        try {
            this.USUARIO = Integer.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        } 
    }

    public void setID_PERSONA(String var) {
        try {
            this.ID_PERSONA = Integer.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        } 
    }

    public void setDEPENDIENTES(String var) {
        try {
            this.DEPENDIENTES = Integer.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        } 
    }

    public void setOCUPACION(String var) {
        try {
            this.OCUPACION = Integer.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        } 
    }

    public void setESTUDIOS(String var) {
        try {
            this.ESTUDIOS = Integer.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        } 
    }

    public void setSCORE(String var) {
        try {
            this.SCORE = Integer.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        } 
    }

    public void setSTATUS(String var) {
        try {
            this.STATUS = Integer.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        } 
    }

    public void setACTIVIDAD(String var) {
        try {
            this.ACTIVIDAD = Integer.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        } 
    }

    public void setTIPO_VIVIENDA(String var) {
        try {
            this.TIPO_VIVIENDA = Integer.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        } 
    }
    
    public void setINGRESOS(String var) {
        try {
            this.INGRESOS = Double.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        } 
    }

    public void setEGRESOS(String var) {
        try {
            this.EGRESOS = Double.valueOf(var);
        } catch (NumberFormatException e) {
            System.out.println(this.getClass().getName() + ": " + e);
        }
    }    

}
