package services;

import objects.Usuario;
import resources.login_resource;

/**
 *
 * @author JMalagon
 */
public class login_service {

    private final login_resource RECURSO;

    public login_service() {
        this.RECURSO = new login_resource();
    }

    //RECIBE LAS CREDENCIALES DEL USUARIO Y REALIZA LA PETICIÓN DE CONSULTAR A LA BASE DE DATOS MEDIANTE EL MÉTODO
    // "buscar_usuario, ALOJADO EN UNA CLASE INFERIOR CON CONEXIÓN A LA BASE DE DATOS
    public Usuario datosUsuario(String usuario, String contrasena) {        
        Usuario user = null;
        String[] array = this.RECURSO.buscar_usuario(usuario, contrasena);        
        if (array != null) {
            user = new Usuario(Integer.parseInt(array[0]), array[1], array[2], Integer.parseInt(array[3]), Integer.parseInt(array[4]), Integer.parseInt(array[5]),array[6]);
        }
        return user;
    }
}
