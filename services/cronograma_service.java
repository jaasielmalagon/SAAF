/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import resources.cronograma_resource;
/**
 *
 * @author dell
 */
public class cronograma_service {
    private final cronograma_resource recurso;
    
    public cronograma_service(){
        this.recurso = new cronograma_resource();
    }
    
    public int agregarActividad(String fecha, String actividad){
        try{
            int status;
            status = (this.recurso.agregarActividad(fecha, actividad)) ? 1 : 0;
            return status;
        }catch(Exception ex){
            System.out.println("services.cronograma_service.agregarActividad(): " + ex);
            
            return 0;
        }
    }
    
    public int modificarActividad(String fecha, String actividad){
        try{
            int status;
            status = (this.recurso.modificarActividad(fecha, actividad)) ? 1 : 0;
            return status;
        }catch(Exception ex){
            System.out.println("services.cronograma_service.modificarActividad(): " + ex);
            
            return 0;
        }
    }
    
}
