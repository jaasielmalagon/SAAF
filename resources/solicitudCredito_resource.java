package resources;

import database.conection;
import java.sql.ResultSet;
import java.sql.SQLException;
import objects.ErrorController;

/**
 *
 * @author JMalagon
 */
public class solicitudCredito_resource {

    private final conection DB;
    ResultSet RS = null;
    ErrorController ERROR_CONTROLLER;
    private final String modulo;

    public solicitudCredito_resource(String modulo) {
        this.DB = new conection();
        this.modulo = modulo;
    }

    public String[][] getAdcFromSucursal(int idSucursal) {
        String[][] array = null;
        try {
            this.DB.Connect();
            String condicion = "sucursal = " + idSucursal + " AND idStaff != 0";
            RS = this.DB.Select("COUNT(*)", "personas_empleados_adc", condicion);
            if (RS.next()) {
                int count = RS.getInt(1);
                if (count > 0) {
                    array = new String[count][3];
                    RS = this.DB.Select("idAdc,agencia,vacante", "personas_empleados_adc", condicion);
                    count = count - count;
                    while (RS.next()) {
                        array[count][0] = RS.getString(1);
                        array[count][1] = RS.getString(2);
                        array[count][2] = RS.getString(3);
                        count++;
                    }
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

}
