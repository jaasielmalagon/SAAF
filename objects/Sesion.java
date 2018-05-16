package objects;

/**
 *
 * @author JMalagon
 */
public class Sesion {

    private int idUsuario, idEstado, idMunicipio;
    private String nombreUsuario, alias, password;

    public Sesion(int idUsuario, String nombreUsuario, String alias, String password, int idEstado, int idMunicipio){
        this.idEstado = idEstado;
        this.idMunicipio = idMunicipio;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.alias = alias;
        this.password = password;
    }
    
    public void setIdUsuario(int idUsuario){
        this.idUsuario = idUsuario;
    }
    public void setIdEstado(int idEstado){
        this.idEstado = idEstado;
    }
    public void setIdMunicipio(int idMunicipio){
        this.idMunicipio = idMunicipio;
    }
    public void setNombreUsuario(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }
    public void setAlias(String alias){
        this.alias = alias;
    }
    public void setPassword(String password){
        this.password = password;
    }
    
    public int getIdUsuario(){
        return idUsuario;
    }
    public int getIdEstado(){
        return idEstado;
    }
    public int getIdMunicipio(){
        return idMunicipio;
    }
    public String getNombreUsuario(){
        return nombreUsuario;
    }
    public String getAlias(){
        return alias;
    }
    public String getPassword(){
        return password;
    }
    
    @Override
    public String toString(){
        return nombreUsuario +"("+alias+")";
    }
    
}
