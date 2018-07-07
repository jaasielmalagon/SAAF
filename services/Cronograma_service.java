package services;
import javax.swing.table.DefaultTableModel;
import resources.Cronograma_resource;
/**
 *
 * @author dell
 * Esta clase ha sido incorporada para manipular la información obtenida desde la base de datos
 * y posteriormente presentarla en la vista Cronograma.
 * Contiene los métodos necesarios para convertir la información en un objeto del tipo DefaultTableModel.
 * Los métodos contenidos tienen por nombre: tablaTareasDeTodoElAnio y tablaTareasDeUnaFecha.
 */

public class Cronograma_service {
    private final Cronograma_resource recurso;
    
    public Cronograma_service(){
        this.recurso = new Cronograma_resource();
    }
    
    /*Regresa un objeto DefaultTableModel con las tareas de todo el año registradas en la base de datos.
    *@params Ninguno
    *@return new DefaultTableModel: Objeto que contiene todas las tareas del año y será usado para llenar un objeto JTable
    *@since 1.0
    */
    public DefaultTableModel tablaTareasDeTodoElAnio(){
        String[][] datos = this.recurso.tareasDeTodoElAnio();
        String[] campos = {"Fecha","Descripción"};
        return (datos != null && campos != null) ? new DefaultTableModel(datos, campos) : null;
    }
    
    /*Regresa un objeto DefaultTableModel con las tareas de una fecha en específico registradas en la base de datos.
    *El parámetro fecha debe estar en el siguiente formato: yyyy:mm:dd
    *@params fecha: Fecha de las tareas  que se desean buscar
    *@return new DefaultTableModel: Objeto que contiene todas las tareas del parámetro fecha y que será usado para llenar un objeto JTable 
    *@since 1.0
    */
    public DefaultTableModel tablaTareasDeUnaFecha(String fecha){
        String[][] datos = this.recurso.tareasDeUnaFecha(fecha);
        String[] campos = {"Fecha","Descripción"};
        return (datos != null && campos != null) ? new DefaultTableModel(datos, campos) : null;
    }
}
