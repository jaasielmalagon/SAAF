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
 * @author dell
 */
public class cronograma_resource {
    private final conection DB;
    private ResultSet RS = null;
    private ErrorController ERROR_CONTROLLER;
    
    public cronograma_resource(){
        this.DB = new conection();
        this.ERROR_CONTROLLER = new ErrorController();
    }
    
    public boolean agregarActividad(String fecha, String actividad){
        try{
            this.DB.Connect();
            this.DB.Insert("cronograma", "fecha, actividad", " ' " + fecha + " ', ' " + actividad + " ' ");
            this.DB.Disconnect();
            return true;
        }catch(Exception ex){
            System.out.println("resources.cronograma_resource.actividadesDelDia() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", "resources.cronograma_resource.actividadesDelDia()  : " + ex);
            return false;
        }
    }
    
    public boolean modificarActividad(String fecha, String actividad){
        try{
            this.DB.Connect();
            this.DB.Update("cronograma", " ' " + actividad +  " ' ", "fecha = " + fecha);
            this.DB.Disconnect();
            return true;
        }catch(Exception ex){
            System.out.println("resources.cronograma_resource.modificarActividad() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", "resources.cronograma_resource.modificarActividad() : " + ex);
            return false;
        }
    }
    
    public String[][] actividadesDelDia(String fecha){
        String[][] data = null;
        try{
            this.DB.Connect();
            this.RS = this.DB.Select("*", "cronograma", "fecha = " + fecha);
            if(this.RS != null){
                int i = 0;
                
                while(this.RS.next()){
                    String[] actividad = {this.RS.getString("fecha"), this.RS.getString("actividad")};
                    data[i] = actividad;
                    i++;
                }
            }
        }catch(Exception ex){
            System.out.println("resources.cronograma_resource.actividadesDelDia() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", "resources.cronograma_resource.actividadesDelDia()  : " + ex);
        }
        return data;
    }
    
}
