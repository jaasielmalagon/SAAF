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
 * Contiene los métodos básicos para consultar, insertar y/o actualizar registros de acuerdo a la 
 * necesidad del service (controlador)
 * @author Root
 */
public class _resource extends conection{
    
    ResultSet RS = null;
    ErrorController ERROR_CONTROLLER;    
    
    public _resource(String modulo){
        this.MODULO = modulo;
    }    
    
    /*NO MODIFICAR NI ELIMINAR ESTE BLOQUE*/
    public String[] getRow(int param) {
        String[] array = null;
        try {
            this.Connect();
            //SOLO CAMBIAR LA CONSULTA
            RS = this.Select("*", "tabla", "condicion");
            if (RS.next()) {
                int size = RS.getMetaData().getColumnCount();
                array = new String[size];
                for (int i = 0; i < size; i++) {
                    array[i] = RS.getString(i + 1);
                }
            }
            this.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }
    
    public String[][] getRows(int param) {
        String[][] array = null;
        try {
            this.Connect();            
            //CAMBIAR ESTA CONSULTA PARA OBTENER EL TOTAL DE REGISTROS DE LA TABLA E INICIALIZAR EL
            //TAMAÑO DE LA MATRIZ QUE CREAREMOS EN FILAS
            RS = this.Select("COUNT(filas)", "tabla", "condicion");
            if (RS.next()) {
                int filas = RS.getInt(1);
                if (filas > 0) {                    
                    RS = this.Select("idAdc,agencia,vacante", "personas_empleados_adc", "condicion");
                    int columnas = RS.getMetaData().getColumnCount();
                    array = new String[filas][columnas];
                    filas = 0;
                    while (RS.next()) {
                        columnas = 1;
                        for (int i = 0; i < array[filas].length; i++) {
                            array[filas][i] = RS.getString(columnas);
                            columnas++;
                        }
                        filas++;
                    }
                }
            }
            this.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }
    
    public boolean setSomething(String param) {
        this.Connect();
        boolean flag = this.Insert("tabla", "campos", "valores");
        this.Disconnect();
        return flag;
    }
    /*NO MODIFICAR NI ELIMINAR ESTE BLOQUE*/
}