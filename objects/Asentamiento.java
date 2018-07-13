package objects;

/**
 *
 * @author JMalagon
 */
public class Asentamiento {
    private int idTipoAsentamiento;
    private String asentamiento;
    
    public Asentamiento(int idTipoAsentamiento, String asentamiento){
        this.idTipoAsentamiento = idTipoAsentamiento;
        this.asentamiento = asentamiento;
    }
    
    public String getAsentamiento(){
        return asentamiento;
    }
    
    public int getIdAsentamiento(){
        return idTipoAsentamiento;
    }
    
    public void setAsentamiento(String asentamiento){
        this.asentamiento = asentamiento;
    }
    
    public void setidTipoAsentamiento(int idTipoAsentamiento){
        this.idTipoAsentamiento = idTipoAsentamiento;
    }
    
    @Override
    public String toString(){
        return asentamiento;
    }
}
