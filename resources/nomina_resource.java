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
import objects.ErrorController;

/**
 *
 * @author mield
 */
public class nomina_resource {

    private final conection DB;
    ResultSet RS = null;
    ErrorController ERROR_CONTROLLER;

    public nomina_resource() {
        this.DB = new conection();
        this.ERROR_CONTROLLER = new ErrorController();
    }
    
    public String[][] nominaEmpleados(){
        String[][] data = null;
        try{
            this.DB.Connect();
            this.RS = this.DB.Select(campos, tabla, condicion);
        }catch(Exception ex){
            System.out.println("Error en resource.nomina_resource.nominaEmpleados():"+ ex.getMessage());
        }
    }
    

}