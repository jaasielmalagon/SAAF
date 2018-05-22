package objects;

/**
 *
 * @author JMalagon
 */
public class Usuario {
    
    private final int idUsuario;
    private final String usuario;
    private final String password;
    private final int tipo;
    private final int idStaff;
    private final int idSucursal;
    private final String fotografia;
    
    public Usuario(int idUsuario, String usuario, String password, int tipo, int idStaff, int idSucursal, String fotografia){
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.password = password;
        this.tipo = tipo;
        this.idStaff = idStaff;
        this.idSucursal = idSucursal;
        this.fotografia = fotografia;
    }
    
    public int getIdUsuario(){
        return idUsuario;
    }
    public String getPassword(){
        return password;
    }
    public int getTipoUsuario(){
        return tipo;
    }
    public int getIdStaff(){
        return idStaff;
    }
    public int getIdSucursal(){
        return idSucursal;
    }
    public String getFotografia() {
        return fotografia;
    }        
    @Override
    public String toString(){
        return usuario;
    }
}
