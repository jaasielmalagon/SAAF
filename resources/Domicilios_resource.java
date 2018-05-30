package resources;

import database.conection;
import java.sql.ResultSet;
import java.sql.SQLException;
import objects.ErrorController;

/**
 *
 * @author JMalagon
 */
public class Domicilios_resource {

    private final conection DB;
    ResultSet RS;
    ErrorController ERROR_CONTROLLER;
    private String MODULO;

    public Domicilios_resource(String modulo) {
        this.DB = new conection();
        this.ERROR_CONTROLLER = new ErrorController();
        this.MODULO = modulo;
    }

    public String[][] buscarDomicilios(String direccion) {
        String[][] dom = null;
        try {
            this.DB.Connect();
            RS = this.DB.Select("COUNT(*)", "domicilios", "direccion like '%" + direccion + "%'");
            if (RS.next()) {
                int count = RS.getInt(1);
                if (count > 0) {
                    if (count > 20) {
                        count = 20;
                    }
//                    System.out.println("Count: " + count);
                    dom = new String[count][4];
                    RS = this.DB.Select("idDomicilio, direccion,latitud,longitud", "domicilios", "direccion like '%" + direccion + "%' LIMIT " + count);
                    int i = 0;
                    while (RS.next()) {
                        dom[i][0] = RS.getString(1);
                        dom[i][1] = RS.getString(2);
                        dom[i][2] = RS.getString(3);
                        dom[i][3] = RS.getString(4);
                        i++;
                    }
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return dom;
    }

    public String[] buscarDomicilio(String direccion, String latitud, String longitud) {
        String[] dom = null;
        try {
            this.DB.Connect();
            RS = this.DB.Select("`idDomicilio`, `direccion`, `latitud`, `longitud`", "domicilios", "(direccion ='" + direccion + "') OR (latitud = '" + latitud + "' AND longitud ='" + longitud + "') LIMIT 1");
            if (RS.next()) {
                dom = new String[4];
                dom[0] = RS.getString(1);
                dom[1] = RS.getString(2);
                dom[2] = RS.getString(3);
                dom[3] = RS.getString(4);                
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return dom;
    }

    public String[] buscarDomicilio(int idDomicilio) {
        String[] dom = null;
        try {
            this.DB.Connect();
            RS = this.DB.Select("`idDomicilio`, `direccion`, `latitud`, `longitud`", "domicilios", "idDomicilio = " + idDomicilio + " LIMIT 1");
            if (RS.next()) {
                dom = new String[4];
                dom[0] = RS.getString(1);
                dom[1] = RS.getString(2);
                dom[2] = RS.getString(3);
                dom[3] = RS.getString(4);    
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return dom;
    }

    public boolean asociarDomicilioPersona(int idPersona, int idDomicilio, int idUsuario, int idSucursal) {
        boolean g;
        this.DB.Connect();
        g = this.DB.Update("personas", "domicilio = " + idDomicilio + ", usuario = " + idUsuario + ",sucursal=" + idSucursal, "idPersona = " + idPersona + " LIMIT 1");
        this.DB.Disconnect();
        return g;
    }

    public int guardarDomicilio(String direccion, String latitud, String longitud) {
        int id = 0;
        this.DB.Connect();
        id = this.DB.InsertId("domicilios", "`direccion`, `latitud`, `longitud`", "'" + direccion + "','" + latitud + "','" + longitud + "'");
        this.DB.Disconnect();
        return id;
    }

    public boolean actualizarDatosDomicilio(String direccion, int idDomicilio) {
        boolean flag;
        try {
            this.DB.Connect();
            flag = this.DB.Update("domicilios", "direccion = '"+direccion+"'", "idDomicilio = " + idDomicilio +" LIMIT 1");
            this.DB.Disconnect();
        } catch (Exception ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            flag = false;
        }
        return flag;
    }

}
