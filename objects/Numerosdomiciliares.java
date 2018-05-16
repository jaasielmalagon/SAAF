package objects;

/**
 *
 * @author JMalagon
 */
public class Numerosdomiciliares {
    
    private int idNumero = 0;
    private String numero;
    private int calle = 0;
    
    public Numerosdomiciliares(int idNumero, String numero, int calle){
        this.idNumero = idNumero;
        this.numero = numero;
        this.calle = calle;
    }
    
    public int getIdNumero(){
        return idNumero;
    }
    
    public String getNumero(){
        return numero;
    }
    
    public int getCalle(){
        return calle;
    }
    
    @Override
    public String toString(){
        return String.valueOf(numero);
    }
    
}
