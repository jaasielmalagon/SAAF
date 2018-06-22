package resources;

import database.conection;
import java.sql.ResultSet;
import java.sql.SQLException;
import objects.ErrorController;

/**
 *
 * @author mield
 */
public class Nomina_resource {

    private final conection DB;
    ResultSet RS;
    ErrorController ERROR_CONTROLLER;
    private final String MODULO;

    public Nomina_resource(String modulo) {
        this.DB = new conection();
        this.ERROR_CONTROLLER = new ErrorController();
        this.MODULO = modulo;
    }

    public String[][] nomina(String filtroBusqueda) {
        return null;
    }

    public String[][] cargos() {
        String[][] array = null;
        try {
            this.DB.Connect();
            RS = this.DB.freeSelect("COUNT(idCargo)", "tipo_cargo", "");
            int count = 0;
            if (RS.next()) {
                count = RS.getInt(1);
            }
            if (count > 0) {
                array = new String[count][2];
                int i = 0;
                RS = this.DB.freeSelect("*", "tipo_cargo", "ORDER BY cargo ASC");
                while (RS.next()) {
                    array[i][0] = RS.getString(1);
                    array[i][1] = RS.getString(2);
                    i++;
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

}
