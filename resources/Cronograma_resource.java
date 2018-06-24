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
public class Cronograma_resource {
    private final conection db;
    ErrorController ERROR_CONTROLLER;
    
    public Cronograma_resource(){
        this.db = new conection();
    }
    
    public String[][] tareasDeTodoElAnio(){
        String[][] tareas = null;
        try{
            this.db.Connect();
            ResultSet resultados = this.db.fullSelect("SELECT * FROM tareas");
            if(resultados != null){
                int i = 0;
                while(resultados.next()){
                    String[] fila ={resultados.getString("fecha"), resultados.getString("descripcion")};
                    tareas[i] = fila;
                    i++;
                }
            }
        }catch(SQLException ex){
             System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);

        }
        return tareas;
    }

    public String[][] tareasDeUnaFecha(String fecha){
       String[][] tareas = null;
       try{
           this.db.Connect();
           ResultSet resultados = this.db.fullSelect("SELECT * FROM tareas WHERE fecha = " + fecha);
           if(resultados != null){
               int i = 0;
               while(resultados.next()){
                   String[] fila = {resultados.getString("fecha"), resultados.getString("descripcion")};
                   tareas[i] = fila;
                   i++;
               }
           }
       }catch(Exception ex){
           System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
       }
       
       return tareas;
    }
    
    public boolean ingresarTarea(String fecha, String descripcion){
        this.db.Connect();
        return this.db.Insert("tareas","`fecha`, `descripcion`", " '"+ fecha +"', '"+ descripcion +"'");
    }
    
    public boolean modificarTarea(int idTarea, String fecha, String descripcion){
        this.db.Connect();
        return this.db.Update("tareas", "Fecha = " + fecha + ", Descripcion = " + descripcion,"idTarea = " + idTarea );
    }
    
    public boolean borrarTarea(int id){
        this.db.Connect();
        return this.db.Delete("tareas", "idTarea = "  + id );
    }
}
