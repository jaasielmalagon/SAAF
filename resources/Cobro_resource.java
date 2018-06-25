/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import database.conection;
import java.sql.ResultSet;
import java.sql.SQLException;
import objects.ErrorController;

/**
 *
 * @author 76053
 */
public class Cobro_resource {
    private final conection DB;
    ResultSet RS;
    ErrorController ERROR_CONTROLLER;
    private String MODULO;

    public Cobro_resource(String modulo) {
        this.DB = new conection();
        this.ERROR_CONTROLLER = new ErrorController();
        this.MODULO = modulo;
    }
    
     public Cobro_resource() {
        this.DB = new conection();
    }
     public String[][] buscarFolios(String Folio) {
        String[][] fol = null;
        try {
            this.DB.Connect();
            RS = this.DB.Select("COUNT(*)", "Cobro", "Folios like '%" + Folio + "%'");
            if (RS.next()) {
                int count = RS.getInt(1);
                if (count > 0) {
                    if (count > 20) {
                        count = 20;
                    }
//                    System.out.println("Count: " + count);
                    fol = new String[count][4];
                    RS = this.DB.Select("campo1, campo2, campo3, campo4", "Cobro", "Folios like '%" + Folio + "%' LIMIT " + count);
                    int i = 0;
                    while (RS.next()) {
                        fol[i][0] = RS.getString(1);
                        fol[i][1] = RS.getString(2);
                        fol[i][2] = RS.getString(3);
                        fol[i][3] = RS.getString(4);
                        i++;
                    }
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return fol;
    }
      public String[] buscarFolio(String Folios, String campo1, String campo2) {
        String[] fol = null;
        try {
            this.DB.Connect();
            RS = this.DB.Select("`idFolio`, `Folios`, `campo1`, `campo2`", "Cobros", "(Folios ='" + Folios + "') OR (campo1 = '" + campo1 + "' AND campo2 ='" + campo2 + "') LIMIT 1");
            if (RS.next()) {
                fol = new String[4];
                fol[0] = RS.getString(1);
                fol[1] = RS.getString(2);
                fol[2] = RS.getString(3);
                fol[3] = RS.getString(4);                
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return fol;
    }
      public int guardarCobro(String campo1, String campo2) {
        int id = 0;
        this.DB.Connect();
        id = this.DB.InsertId("Cobro", "`campo1`, `campo2`", "'" + campo1 + "','" + campo2 + "'");
        this.DB.Disconnect();
        return id;
    }
}
