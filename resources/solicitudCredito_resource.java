package resources;

import database.conection;
import java.sql.ResultSet;
import java.sql.SQLException;
import objects.ErrorController;

/**
 *
 * @author JMalagon
 */
public class solicitudCredito_resource {

    private final conection db;
    ResultSet rs = null;
    ErrorController ERROR_CONTROLLER;

    public solicitudCredito_resource() {
        this.db = new conection();
    }

    public String[][] personas(int idSucursal, String dato) {
        String[][] personas = null;
        int i = 0;
        try {
            
            int count = this.contarPersonas(idSucursal, dato);
            if (count > 0) {
                this.db.Connect();
                personas = new String[count][10];
                rs = this.db.freeSelect("idPersona, nombre, apaterno, amaterno, curp, ocr, telefono, celular, sexo,"
                        + "calles.calle, numerosdomiciliares.numero, tipo_asentamiento.tipoAsentamiento, colonias.colonia, colonias.cp, municipios.municipio,estados.estado",
                        "personas",
                        "INNER JOIN domicilios ON personas.domicilio = domicilios.idDomicilio INNER JOIN calles ON domicilios.calle = calles.idCalle INNER JOIN numerosdomiciliares ON domicilios.numero = numerosdomiciliares.idNumero INNER JOIN colonias ON domicilios.colonia = colonias.idColonia INNER JOIN tipo_asentamiento ON colonias.asentamiento = tipo_asentamiento.idTipoAsentamiento INNER JOIN municipios ON municipios.idMunicipio = colonias.municipio INNER JOIN estados ON estados.idEstado = municipios.estado "
                        + "WHERE personas.sucursal = " + idSucursal + " AND (nombre like '%" + dato + "%' OR apaterno like '%" + dato + "%' OR amaterno like '%" + dato + "%' OR curp like '%" + dato + "%' OR ocr like '%" + dato + "%' OR telefono like '%" + dato + "%' OR celular like '%" + dato + "%')");
                while (rs.next()) {
                    String dir = rs.getString(10) + " " + rs.getString(11) + " " + rs.getString(12) + " " + rs.getString(13) + " " + rs.getString(15) + "," + rs.getString(16);
                    String array[] = {String.valueOf(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), dir};
                    personas[i] = array;
                    i++;
                }
                this.db.Disconnect();
            }            
        } catch (SQLException ex) {
            System.out.println("resources.agregarPersona_resource.personas() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", "resources.agregarPersona_resource.personas() : " + ex);
        }
        return personas;
    }

    private int contarPersonas(int idSucursal, String dato) {
        int count = 0;
        try {
            rs = this.db.Select("COUNT(idPersona)", "personas", "sucursal = " + idSucursal + " AND (nombre like '%" + dato + "%' OR apaterno like '%" + dato + "%' OR amaterno like '%" + dato + "%' OR curp like '%" + dato + "%' OR ocr like '%" + dato + "%' OR telefono like '%" + dato + "%' OR celular like '%" + dato + "%')");
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("resources.agregarPersona_resource.contarPersonas() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", "resources.agregarPersona_resource.contarPersonas() : " + ex);
        }
        return count;
    }

}
