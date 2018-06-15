/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import database.conection;
import java.sql.ResultSet;
import objects.ErrorController;

/**
 *
 * @author 76053
 */
public class cobrar_resource {

    private final conection DB;
    ResultSet RS = null;
    ErrorController ERROR_CONTROLLER;
    String MODULO;

    public cobrar_resource(String modulo) {
        this.DB = new conection();
        this.ERROR_CONTROLLER = new ErrorController();
        this.MODULO = modulo;
    }

    public cobrar_resource() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean InsertarZonaYADC(String LugarZona, String TipoADC) {
        boolean flag;
        try {
            this.DB.Connect();
            flag = this.DB.Insert("Cobro", "LugarZona, TipoADC", " ' " + LugarZona + " ' , ' " + TipoADC + " ' " );
            this.DB.Disconnect();
        } catch (Exception ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            flag = false;
        }
        return flag;
    }

    public boolean ModificarZonaYADC(String LugarZona, String TipoADC) {
        boolean flag;
        try {
            this.DB.Connect();
            flag = this.DB.Update(" Cobro ", " ' " + TipoADC + " ' " ,  " LugarZona = " +  LugarZona);
            this.DB.Disconnect();
        } catch (Exception ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            flag = false;
        }
        return flag;
    }

  //  public String[] ejemploSelectFila(int id) {
  //      String[] array = null;
  //      try {
  //          this.DB.Connect();
  //          this.RS = this.DB.Select("campos,id", "tabla", "condicion");
  //          if (this.RS.next()) {
   //             int size = this.RS.getFetchSize();
  //              if (size > 0) {
  //                  array = new String[size];
  //                  for (int i = 0; i < size; i++) {
  //                      array[i] = this.RS.getString(i + 1);
   //                 }
   //             }
   //         }
   //         this.DB.Disconnect();
   //     } catch (Exception ex) {
   //         System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
   //         this.ERROR_CONTROLLER.escribirErrorLogger(this.MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
   //         array = null;
   ///     }
   //     return array;
   // }

    public String[][] DatosCobro(int id) {
         // ------//|
                  //|
                  //|
             String[][] array = null;
            try {
            this.DB.Connect();
            int registros = 0;
            int i = 0;
            this.RS = this.DB.Select("count(*)", "cobro", "condicion");//filas
            if (this.RS.next()) { //si debe contar                        //la condicion tiene que ser igual la del campo 
                registros = this.RS.getInt(1); // cantidad de registros
                this.RS = this.DB.Select("campos,id,fecha,nombre,edad", "tabla", "condicion");
                                          // 1    2    3    4      5               //esta condicion tiene que
                                          //no debe contar                                        //ser igual a la de filas
                array = new String[registros][5];
                while (this.RS.next()) {
                    array[i][0] = RS.getString(1);
                    array[i][1] = RS.getString(2);
                    array[i][2] = RS.getString(3);
                    array[i][3] = RS.getString(4);
                    array[i][4] = RS.getString(5);
                    
                }
            }
            this.DB.Disconnect();
        } catch (Exception ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            array = null;
        }
        return array;
    }
     
}