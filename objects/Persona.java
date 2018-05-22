package objects;

/**
 *
 * @author JMalagon
 */
public class Persona {

    private int idPersona, entidad_nac, edoCivil, domicilio, conyuge, referencia, aval;
    private String nombre, apaterno, amaterno, f_nacimiento, curp, ocr, sexo, telefono, celular;    

    public Persona(int idPersona, String nombre, String apaterno, String amaterno, String f_nacimiento, int entidad_nac,
            String curp, String ocr, String sexo, int edoCivil, String telefono, String celular, int domicilio, 
            int conyuge, int aval, int referencia) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.f_nacimiento = f_nacimiento;
        this.entidad_nac = entidad_nac;
        this.curp = curp;
        this.ocr = ocr;
        this.sexo = sexo;
        this.edoCivil = edoCivil;
        this.telefono = telefono;
        this.celular = celular;
        this.domicilio = domicilio;
        this.conyuge = conyuge;
        this.referencia = referencia;
        this.aval = aval;

    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public void setEntidad_nac(int entidad_nac) {
        this.entidad_nac = entidad_nac;
    }

    public void setEdoCivil(int edoCivil) {
        this.edoCivil = edoCivil;
    }

    public void setDomicilio(int domicilio) {
        this.domicilio = domicilio;
    }

    public void setConyuge(int conyuge) {
        this.conyuge = conyuge;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    public void setAval(int aval) {
        this.aval = aval;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public void setF_nacimiento(String f_nacimiento) {
        this.f_nacimiento = f_nacimiento;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public void setOcr(String ocr) {
        this.ocr = ocr;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }    

    //GETERS
    public int getIdPersona() {
        return idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApaterno() {
        return apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getF_nac() {
        return f_nacimiento;
    }

    public int getEntidadNac() {
        return entidad_nac;
    }

    public String getCurp() {
        return curp;
    }

    public String getOcr() {
        return ocr;
    }

    public String getSexo() {
        return sexo;
    }
    
    public int getEstadoCivil(){
        return edoCivil;
    }

    public String getCelular() {
        return celular;
    }

    public int getDomicilio() {
        return domicilio;
    }

    public int getConyuge() {
        return conyuge;
    }

    public int getReferencia() {
        return referencia;
    }

    public int getAval() {
        return aval;
    }

    @Override
    public String toString() {
        return nombre + " " + apaterno + " " + amaterno;
    }

}
