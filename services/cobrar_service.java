/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import resources.cobrar_resource;

/**
 *
 * @author 76053
 */
public class cobrar_service {
    private final cobrar_resource recurso;

    public cobrar_service() {
        this.recurso = new cobrar_resource();
    }
    
    public int InsertarZonaYADC(String LugarZona, String TipoADC){
        try{
            int status;
            status = (this.recurso.InsertarZonaYADC(LugarZona,TipoADC)) ? 1 : 0;
            return status;
        }catch(Exception ex){
            System.out.println("services.cobrar_service.InsertarZonaYADC(): " + ex);
            
            return 0;
        }
    }
    
        public int ModificarZonaYADC(String LugarZona, String TipoADC){
        try{
            int status;
            status = (this.recurso.ModificarZonaYADC(LugarZona,TipoADC)) ? 1 : 0;
            return status;
        }catch(Exception ex){
            System.out.println("services.cobrar_service.ModificarZonaYADC(): " + ex);
            
            return 0;
        }
    }
}
