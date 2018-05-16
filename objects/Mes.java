package objects;

/**
 *
 * @author JMalagon
 */
public class Mes {
    private String numero;
    private String nombre;
    
    public Mes(String numero, String nombre){
        this.numero = numero;
        this.nombre = nombre;
    }
    
    public String getNumeroMes(){
        return numero;
    }
        
    public String getNombreMes(){
        return nombre;
    }
    
    public void setNombreMes(String nombre){
        this.nombre = nombre;
    }
    
    public void setNumeroMes(String numero){
        this.numero = numero;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
