package resources;

import database.conection;
import java.sql.ResultSet;
import java.sql.SQLException;
import objects.ErrorController;

/**
 *
 * @author JMalagon
 */
public class fichaPersonal_resource {

    private final conection db;
    ResultSet rs = null;
    ErrorController ERROR_CONTROLLER;

    public fichaPersonal_resource() {
        this.db = new conection();
        this.ERROR_CONTROLLER = new ErrorController();
    }

    public String estado(int id) {
        String estado = null;
        try {
            this.db.Connect();
            this.rs = this.db.Select("estado", "estados", "idEstado = " + id + " LIMIT 1");
            if (rs.next()) {
                estado = rs.getString(1);
            }
            this.db.Disconnect();
        } catch (SQLException ex) {            
            System.out.println("resources.fichaPersonal_resource.estado() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", "resources.fichaPersonal_resource.estados() : " + ex);
        }
        return estado;
    }
    
    public String[] domicilio(int idDomicilio) {
        String[] domicilio = null;
        try {
            this.db.Connect();
            rs = this.db.freeSelect("`idDomicilio`, domicilios.calle, domicilios.numero, `calle1`, `calle2`, domicilios.`colonia`, `tipo`, `propietario`, `vigencia_contrato`, `tiempoResidencia`, calles.calle, numerosdomiciliares.numero, tipo_asentamiento.tipoAsentamiento, colonias.colonia, colonias.cp, municipios.municipio,estados.estado", "domicilios", "INNER JOIN calles ON domicilios.calle = calles.idCalle INNER JOIN numerosdomiciliares ON domicilios.numero = numerosdomiciliares.idNumero INNER JOIN colonias ON domicilios.colonia = colonias.idColonia INNER JOIN tipo_asentamiento ON colonias.asentamiento = tipo_asentamiento.idTipoAsentamiento INNER JOIN municipios ON municipios.idMunicipio = colonias.municipio INNER JOIN estados ON estados.idEstado = municipios.estado WHERE domicilios.idDomicilio = " + idDomicilio);
            if (rs.next()) {
                domicilio = new String[17];
                domicilio[0] = String.valueOf(rs.getInt(1));//idDomicilio
                domicilio[1] = String.valueOf(rs.getInt(2));//idCalle
                domicilio[2] = String.valueOf(rs.getInt(3));//idNumero
                domicilio[3] = String.valueOf(rs.getInt(4));//idCalle1
                domicilio[4] = String.valueOf(rs.getInt(5));//idCalle2
                domicilio[5] = String.valueOf(rs.getInt(6));//idColonia
                domicilio[6] = String.valueOf(rs.getInt(7));//idTipo
                domicilio[7] = rs.getString(8);//propietario
                domicilio[8] = rs.getString(9);//vigencia
                domicilio[9] = String.valueOf(rs.getInt(10));//años
                domicilio[10] = rs.getString(11);//calle
                domicilio[11] = rs.getString(12);//numero
                domicilio[12] = rs.getString(13);//asentamiento
                domicilio[13] = rs.getString(14);//colonia
                domicilio[14] = rs.getString(15);//codigo_postal
                domicilio[15] = rs.getString(16);//municipio
                domicilio[16] = rs.getString(17);//estado
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.domicilio() : " + ex);
        }
        return domicilio;
    }
    
    public String[] persona(int idSucursal, int idPersona) {
        String[] persona = null;
        if (idPersona != 0 && idPersona > 0) {
            try {
                this.db.Connect();
                rs = this.db.Select("idPersona, nombre, apaterno, amaterno,nacimiento, entidad, curp, ocr, sexo, edoCivil, telefono, celular, domicilio, conyuge, aval, referencia", "personas", "sucursal = " + idSucursal + " AND idPersona = " + idPersona);
                if (rs.next()) {
                    persona = new String[16];
                    persona[0] = String.valueOf(rs.getInt(1));
                    persona[1] = rs.getString(2);
                    persona[2] = rs.getString(3);
                    persona[3] = rs.getString(4);
                    persona[4] = rs.getString(5);
                    persona[5] = rs.getString(6);
                    persona[6] = rs.getString(7);
                    persona[7] = rs.getString(8);
                    persona[8] = rs.getString(9);
                    persona[9] = rs.getString(10);
                    persona[10] = rs.getString(11);
                    persona[11] = rs.getString(12);
                    persona[12] = rs.getString(13);
                    persona[13] = rs.getString(14);
                    persona[14] = rs.getString(15);
                    persona[15] = rs.getString(16);
                }
                this.db.Disconnect();
            } catch (SQLException ex) {
                System.out.println("resources.agregarPersona_resource.persona() : " + ex);
                this.ERROR_CONTROLLER.escribirErrorLogger("administracion", "resources.agregarPersona_resource.persona() : " + ex);
            }
        } 
        return persona;
    }
}
