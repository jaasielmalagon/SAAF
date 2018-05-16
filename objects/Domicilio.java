package objects;

/**
 *
 * @author JMalagon
 */
public class Domicilio {
    private int idDomicilio = 0;
    private int calle = 0;
    private int numero = 0;
    private int calle1 = 0;
    private int calle2 = 0;
    private int colonia = 0;
    private int tipo = 0;
    private String propietario = "";
    private String vigencia = "";
    private int tiempoResidencia = 0;
    private String[] DIRECCION = null;
    
    public Domicilio(int idDomicilio, int calle,int numero, int calle1, int calle2, int colonia, int tipo, String propietario, String vigencia, int tiempoResidencia, String[] direccion){
        this.idDomicilio = idDomicilio;
        this.calle = calle;
        this.numero = numero;
        this.calle1 = calle1;
        this.calle2 = calle2;
        this.colonia = colonia;
        this.tipo = tipo;
        this.propietario = propietario;
        this.vigencia = vigencia;
        this.tiempoResidencia = tiempoResidencia;
        this.DIRECCION = direccion;
    }
    
    public int getId(){
        return idDomicilio;
    }    
    public int getCalle(){
        return calle;
    }
    public int getNumero(){
        return numero;
    }
    public int getCalle1(){
        return calle1;
    }
    public int getCalle2(){
        return calle2;
    }
    public int getColonia(){
        return colonia;
    }
    public int getTipo(){
        return tipo;
    }
    public String getPropietario(){
        return propietario;
    }
    public String getVigencia(){
        return vigencia;
    }
    public int getTiempoResidencia(){
        return tiempoResidencia;
    }
    
    public String[] getDireccionArray(){
        //calle [0]
        //numero [1]
        //asentamiento [2]
        //colonia [3]
        //codigo postal [4]
        //municipio [5]
        //estado [6]
        return DIRECCION;
    }
    
    @Override
    public String toString(){
        return DIRECCION[0] + " " + DIRECCION[1] + " " + DIRECCION[2] + " " + DIRECCION[3] + " " + DIRECCION[5] + ", " + DIRECCION[6] + ", " + DIRECCION[4];
    }
}
