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
    
    
    

}
