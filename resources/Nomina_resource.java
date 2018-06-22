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
    private String MODULO;

    public Nomina_resource(String modulo) {
        this.DB = new conection();
        this.ERROR_CONTROLLER = new ErrorController();
        this.MODULO = modulo;
    }
        public int[][] buscarNomina(int idEmpleado) {
        int[][] nom = null;
        try {
            this.DB.Connect();
            RS = this.DB.Select("`idEmpleado`", MODULO, MODULO);
            if (RS.next()) {
                int count = RS.getInt(1);
                if (count > 0) {
                    if (count > 20) {
                        count = 20;
                    }
                    nom = new int[count][4];
                    RS = this.DB.Select("`idEmpleado`", MODULO, MODULO);
                    int i = 0;
                    while (RS.next()) {
                        nom[i][0] = RS.getInt(1);
                        nom[i][1] = RS.getInt(2);
                        nom[i][2] = RS.getInt(3);
                        nom[i][3] = RS.getInt(4);
                        i++;
                    }
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return nom;
    }
        
           public int[] buscarNominas(int idEmpleado) {
        int[] nom = null;
        try {
            this.DB.Connect();
            RS = this.DB.Select(MODULO, MODULO, MODULO);
            if (RS.next()) {
                nom = new int[4];
                nom[0] = RS.getInt(1);
                nom[1] = RS.getInt(2);
                nom[2] = RS.getInt(3);
                nom[3] = RS.getInt(4);
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return nom;
    }

}
