/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import javax.swing.table.DefaultTableModel;
import resources.Cronograma_resource;
/**
 *
 * @author dell
 */
public class Cronograma_service {
    private final Cronograma_resource recurso;
    
    public Cronograma_service(){
        this.recurso = new Cronograma_resource();
    }
    
    public DefaultTableModel tablaTareasDeTodoElAnio(){
        String[][] datos = this.recurso.tareasDeTodoElAnio();
        String[] campos = {"Fecha","Descripción"};
        return (datos != null && campos != null) ? new DefaultTableModel(datos, campos) : null;
    }
    
    public DefaultTableModel tablaTareasDeUnaFecha(String fecha){
        String[][] datos = this.recurso.tareasDeUnaFecha(fecha);
        String[] campos = {"Fecha","Descripción"};
        return (datos != null && campos != null) ? new DefaultTableModel(datos, campos) : null;
    }
    
}
