package resources;

import database.conection;
import java.sql.ResultSet;
import java.sql.SQLException;
import objects.ErrorController;

/**
 *
 * @author JMalagon
 */
public class login_resource {//implements interfaces.conection{

    private final conection db;
    private ResultSet rs = null;
    ErrorController ERROR_CONTROLLER;

    public login_resource() {
        this.db = new conection();
        this.ERROR_CONTROLLER = new ErrorController();
    }

    public String[] buscar_usuario(String usuario, String contrasena) {
        String[] data = null;
        try {
            this.db.Connect();
            rs = this.db.Select("COUNT(idUsuario)", "usuarios", "usuario = '" + usuario + "' AND password = '" + contrasena + "' LIMIT 1");
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            if (count > 0) {
                rs = this.db.Select("idUsuario,usuario,password,tipo,idStaff,idSucursal,fotografia", "usuarios", "usuario = '" + usuario + "' AND password = '" + contrasena + "' LIMIT 1");
                if (rs != null) {
                    data = new String[7];
                    if (rs.next()) {
                        data[0] = rs.getString(1);
                        data[1] = rs.getString(2);
                        data[2] = rs.getString(3);
                        data[3] = rs.getString(4);
                        data[4] = rs.getString(5);
                        data[5] = rs.getString(6);
                        data[6] = rs.getString(7);
                    }
                }
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.login_resource.buscar_usuario() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("login", "resources.login_resource.buscar_usuario() : " + ex);
        }
        return data;
    }

}
