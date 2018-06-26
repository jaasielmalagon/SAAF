package resources;

import database.conection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
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
   /*Buscar una nómina en la base de datos mediante un Id
    *@params Id: identificador del empleado
    *@return resultado: null
    *@since 1.0
    */
    public String[][] nomina(String filtroBusqueda) {
        return null;
    }
    /*Realiza una búsqueda en la base de datos del id y el cargo deseado
    *@params: filtro de busqueda por id
    *@return resultado: Tipo de cargos en forma ascendente
    *@since: 1.0
    */
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
    /*Realiza una búsqueda en la base de datos para traer la id y mostrar el cargo deseado
    *@params: Ninguno
    *@return resultado: tipos de Cargo Ordenados en forma ascendente
    *@since: 1.0
    */
    public String[][] getCargos() {
        String[][] array = null;
        try {
            this.DB.Connect();
            RS = this.DB.freeSelect("COUNT(*)", "tipo_cargo", "");
            int count = 0;
            if (RS.next()) {
                count = RS.getInt(1);
                if (count > 0) {
                    RS = this.DB.freeSelect("idCargo,cargo", "tipo_cargo", "ORDER BY cargo ASC");
                    int columnas = RS.getMetaData().getColumnCount();
                    array = new String[count][columnas];
                    count = 0;
                    while (RS.next()) {
                        columnas = 1;
                        for (int i = 0; i < array[count].length; i++) {
                            array[count][i] = RS.getString(columnas);
                            columnas++;
                        }
                        count++;
                    }
                }
            }
            this.DB.Disconnect();
        } catch (SQLException e) {
        }
        return array;

    }

}
