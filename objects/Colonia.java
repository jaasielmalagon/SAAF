package objects;

/**
 *
 * @author JMalagon
 */
public class Colonia {
    private int idColonia = 0;
    private String colonia;
    private int tipo = 0;
    private int cp = 0;
    private int municipio = 0;
    
    public Colonia(int idColonia, String colonia, int tipo, int cp, int municipio){
        this.idColonia = idColonia;
        this.colonia = colonia;
        this.tipo = tipo;
        this.cp = cp;
        this.municipio = municipio;
    }
    
    public int getIdColonia(){
        return idColonia;       
    }
    
    public String getColonia(){
        return colonia;
    }
    
    public int getTipo(){
        return tipo;
    }
    
    public int getCP(){
        return cp;
    }
    
    public int getMunicipio(){
        return municipio;
    }
    
    public void setIdColonia(int idColonia){
        this.idColonia = idColonia;       
    }
    
    public void setColonia(String colonia){
        this.colonia = colonia;
    }
    
    public void setTipo(int tipo){
        this.tipo = tipo;
    }
    
    public void setCP(int cp){
        this.cp = cp;
    }
    
    public void setMunicipio(int municipio){
        this.municipio = municipio;
    }
    
    @Override
    public String toString(){
        return colonia;
    }
}
