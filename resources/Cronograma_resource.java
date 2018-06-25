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
 * @author Gerarcode (Gerardo Aguilar)
 */
public class Cronograma_resource {
    private final conection db;
    ErrorController ERROR_CONTROLLER;
    ResultSet resultados;
   
    public Cronograma_resource(){
        this.db = new conection();
    }
    
    /*Realiza una búsqueda en la base de datos para traer todas las tareas programadas durante el año
    *@params: Ninguno
    *@return: tareas: Matriz que contiene las tareas de todo el año
    *@since: 1.0
    */
    public String[][] tareasDeTodoElAnio(){
        String[][] tareas = null;
        try{
            this.db.Connect();
            resultados = this.db.fullSelect("SELECT * FROM tareas ORDER BY Fecha");
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

    /*Realiza una búsqueda en la base de datos para regresar las tareas programadas de una fecha en específico.
    *El parámetro fecha debe ser ingresado con el siguiente formato yyyy:mm:dd
    *@param fecha Fecha de las tareas que se desean buscar,
    *@return tareas: Matriz de cadenas que contiene todas las tareas del parámetro fecha.: 
    *@since 1.0
    */
    public String[][] tareasDeUnaFecha(String fecha){
       String[][] tareas = null;
       try{
           this.db.Connect();
           String consulta = String.format("SELECT * FROM tareas WHERE fecha =  %s;", fecha);
           resultados = this.db.fullSelect( consulta );
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
       this.db.Disconnect();
       return tareas;
    }
    
    /*Ingresa una tarea dentro de la base de datos.
    *El parámetro fecha debe tener el siguiente formato yyyy:mm:dd
    *@params fecha: Fecha de la tarea que se desea registrar, descripcion: Descripción de la tarea que se registrará.
    *@return resultado: Variable booleana que indica si la inserción en la base de datos fue exitosa.
    *@since 1.0
    */
    public boolean ingresarTarea(String fecha, String descripcion){
        this.db.Connect();
        boolean resultado = this.db.Insert("tareas","`fecha`, `descripcion`", " '"+ fecha +"', '"+ descripcion +"'");
        return resultado;
    }
    
    /*Modifica una tarea con un id en específico.
    *El parámetro fecha debe estar en el siguiente formato yyyy:mm:dd
    *@params fecha: Nueva fecha de la tarea a modificar, descripcion: Nueva descripción de la tarea a modificar.
    *@return resultado: Variable booleana que indica si la modificación de la tarea fue exitosa.
    *@since 1.0
    */
    public boolean modificarTarea(int idTarea, String fecha, String descripcion){
        this.db.Connect();
        boolean resultado =  this.db.Update("tareas", "Fecha = " + fecha + ", Descripcion = " + descripcion,"idTarea = " + idTarea );
        return resultado;
    }
    
    /*Borra una tarea de la base de datos mediante un Id
    *@params Id: identificador de la fecha que será eliminada.
    *@return resultado: Variable booleana que indica si la eliminación de la tarea fue exitosa.
    *@since 1.0
    */
    public boolean borrarTarea(int id){
        this.db.Connect();
        boolean resultado = this.db.Delete("tareas", "idTarea = "  + id );
        return resultado;
    }
}
