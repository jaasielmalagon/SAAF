package resources;

import database.conection;
import java.sql.ResultSet;
import java.sql.SQLException;
import objects.ErrorController;

/**
 *
 * @author JMalagon
 */
public class Domicilios_resource {
    private final conection DB;
    ResultSet RS;
    ErrorController ERROR_CONTROLLER;
    private String MODULO;
    
    public Domicilios_resource(String modulo) {
        this.DB = new conection();
        this.ERROR_CONTROLLER = new ErrorController();
        this.MODULO = modulo;
    }
    
    public String[] buscarDomicilio(String direccion, String latitud, String longitud){
        String[] dom = null;
          try {
            this.DB.Connect();
            RS = this.DB.Select("*", "domicilios", "direccion like '%" + direccion + "%' OR (latitud = '"+latitud+"' AND longitud ='"+longitud+"') LIMIT 1");
            if (RS.next()) {
                dom = new String[8];
                dom[0] = RS.getString(1);
                dom[1] = RS.getString(2);
                dom[2] = RS.getString(3);
                dom[3] = RS.getString(4);
                dom[4] = RS.getString(5);
                dom[5] = RS.getString(6);
                dom[6] = RS.getString(7);
                dom[7] = RS.getString(8);
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return dom;
    }
    
    public boolean asociarDomicilioPersona(int idPersona, int idDomicilio){
        boolean g;
        this.DB.Connect();
        g = this.DB.Update("personas", "domicilio = " + idDomicilio, "idPersona = " + idPersona + " LIMIT 1");
        this.DB.Disconnect();        
        return g;
    }
    
    public int guardarDomicilio(String direccion, String latitud, String longitud, int tipo, String propietario, String vigencia, String tiempoResidencia){
        int id = 0;
        this.DB.Connect();
        id = this.DB.InsertId("domicilios", "`direccion`, `latitud`, `longitud`, `tipo`, `propietario`, `vigencia_contrato`, `tiempoResidencia`",
                "'"+direccion+"','"+latitud+"','"+longitud+"',"+tipo+"','"+propietario+"','"+vigencia+"',"+tiempoResidencia);
        this.DB.Disconnect();        
        return id;
    }
    
}
