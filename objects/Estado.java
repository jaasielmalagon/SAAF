package objects;

/**
 *
 * @author JMalagon
 */
public class Estado {

    private int idEstado;
    private String estado;
    
    public Estado(int idEstado, String estado){
        this.idEstado = idEstado;
        this.estado = estado;
    }
    
    public int getId(){
        return idEstado;
    }
    
    public String getEstado(){
        return estado;
    }
    
    public void setId(int idEstado){
        this.idEstado = idEstado;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }      
    
    @Override
    public String toString(){
        return estado;
    }
}
