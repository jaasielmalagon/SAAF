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
    
    
    
}
