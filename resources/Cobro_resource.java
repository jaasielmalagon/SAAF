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
 * @author 76053
 */
public class Cobro_resource {
    private final conection DB;
    ResultSet RS = null;
    ErrorController ERROR_CONTROLLER;
    private final String modulo;
    
    public Cobro_resource(String modulo) {
        this.modulo = modulo;
        this.DB = new conection();        
    }
    
    
}
