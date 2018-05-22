package objects;

/**
 *
 * @author JMalagon
 */
public class Persona {

    private final int idPersona;
    private final String nombre;
    private final String apaterno;
    private final String amaterno;
    private final String f_nacimiento;
    private final int entidad_nac;
    private final String curp;
    private final String ocr;
    private final String sexo;
    private final int edoCivil;
    private final String telefono;
    private final String celular;
    private final int domicilio;
    private final int conyuge;
    private final int referencia;
    private final int aval;

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
