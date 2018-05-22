package objects;

/**
 *
 * @author JMalagon
 */
public class Empleado {

    private int ID_STAFF, ID_PERSONA, CARGO, ESTUDIOS, DEPARTAMENTO, SUCURSAL, SALARIO, EFECTIVO, USUARIO;
    private String ENTRADA, SALIDA, DIAS_LABORALES, CASO_EMERGENCIA, FECHA_INCORPORACION, CODIGO, REGISTRO;
    private Persona PERSONA;

    public Empleado(int ID_STAFF, int SUCURSAL, int USUARIO, String REGISTRO, int ID_PERSONA, int CARGO, int ESTUDIOS, int DEPARTAMENTO, int SALARIO, String ENTRADA, String SALIDA, String DIAS_LABORALES, String CASO_EMERGENCIA, String FECHA_INCORPORACION, int EFECTIVO, String CODIGO, Persona PERSONA) {
        this.ID_STAFF = ID_STAFF;
        this.ID_PERSONA = ID_PERSONA;
        this.CARGO = CARGO;
        this.ESTUDIOS = ESTUDIOS;
        this.DEPARTAMENTO = DEPARTAMENTO;
        this.SUCURSAL = SUCURSAL;
        this.SALARIO = SALARIO;
        this.ENTRADA = ENTRADA;
        this.SALIDA = SALIDA;
        this.DIAS_LABORALES = DIAS_LABORALES;
        this.CASO_EMERGENCIA = CASO_EMERGENCIA;
        this.FECHA_INCORPORACION = FECHA_INCORPORACION;
        this.EFECTIVO = EFECTIVO;
        this.CODIGO = CODIGO;       
        this.USUARIO = USUARIO;
        this.REGISTRO = REGISTRO;
        this.PERSONA = PERSONA;
    }

    public int getID_STAFF() {
        return ID_STAFF;
    }

    public void setID_STAFF(int ID_STAFF) {
        this.ID_STAFF = ID_STAFF;
    }

    public int getID_PERSONA() {
        return ID_PERSONA;
    }

    public void setID_PERSONA(int ID_PERSONA) {
        this.ID_PERSONA = ID_PERSONA;
    }

    public int getCARGO() {
        return CARGO;
    }

    public void setCARGO(int CARGO) {
        this.CARGO = CARGO;
    }

    public int getESTUDIOS() {
        return ESTUDIOS;
    }

    public void setESTUDIOS(int ESTUDIOS) {
        this.ESTUDIOS = ESTUDIOS;
    }

    public int getDEPARTAMENTO() {
        return DEPARTAMENTO;
    }

    public void setDEPARTAMENTO(int DEPARTAMENTO) {
        this.DEPARTAMENTO = DEPARTAMENTO;
    }

    public int getSUCURSAL() {
        return SUCURSAL;
    }

    public void setSUCURSAL(int SUCURSAL) {
        this.SUCURSAL = SUCURSAL;
    }

    public int getSALARIO() {
        return SALARIO;
    }

    public void setSALARIO(int SALARIO) {
        this.SALARIO = SALARIO;
    }

    public String getENTRADA() {
        return ENTRADA;
    }

    public void setENTRADA(String ENTRADA) {
        this.ENTRADA = ENTRADA;
    }

    public String getSALIDA() {
        return SALIDA;
    }

    public void setSALIDA(String SALIDA) {
        this.SALIDA = SALIDA;
    }

    public String getDIAS_LABORALES() {
        return DIAS_LABORALES;
    }

    public void setDIAS_LABORALES(String DIAS_LABORALES) {
        this.DIAS_LABORALES = DIAS_LABORALES;
    }

    public String getCASO_EMERGENCIA() {
        return CASO_EMERGENCIA;
    }

    public void setCASO_EMERGENCIA(String CASO_EMERGENCIA) {
        this.CASO_EMERGENCIA = CASO_EMERGENCIA;
    }

    public String getFECHA_INCORPORACION() {
        return FECHA_INCORPORACION;
    }

    public void setFECHA_INCORPORACION(String FECHA_INCORPORACION) {
        this.FECHA_INCORPORACION = FECHA_INCORPORACION;
    }

    public void setPERSONA(Persona PERSONA) {
        this.PERSONA = PERSONA;
    }

    public Persona getPERSONA() {
        return PERSONA;
    }
    
    public void setEFECTIVO(int EFECTIVO){
        this.EFECTIVO = EFECTIVO;
    }
    
    public int getEFECTIVO(){
        return EFECTIVO;
    }

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }           

    public int getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(int USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getREGISTRO() {
        return REGISTRO;
    }

    public void setREGISTRO(String REGISTRO) {
        this.REGISTRO = REGISTRO;
    }    
    
    @Override
    public String toString() {
        return "Staff{" + "ID_STAFF=" + ID_STAFF + ", ID_PERSONA=" + ID_PERSONA + ", CARGO=" + CARGO + ", ESTUDIOS=" + ESTUDIOS + ", DEPARTAMENTO=" + DEPARTAMENTO + ", SUCURSAL=" + SUCURSAL + ", SALARIO=" + SALARIO + ", EFECTIVO=" + EFECTIVO + ", ENTRADA=" + ENTRADA + ", SALIDA=" + SALIDA + ", DIAS_LABORALES=" + DIAS_LABORALES + ", CASO_EMERGENCIA=" + CASO_EMERGENCIA + ", FECHA_INCORPORACION=" + FECHA_INCORPORACION + ", CODIGO=" + CODIGO + ", PERSONA=" + PERSONA + '}';
    }    

}
