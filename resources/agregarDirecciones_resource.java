package resources;

import database.conection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author JMalagon
 */
public class agregarDirecciones_resource {

    private final conection db;
    private ResultSet rs;

    public agregarDirecciones_resource() {
        this.db = new conection();
    }//MÉTODOS USADOS PARA ORIENTACIÓN A OBJETOS, LO DE ARRIBA HAY QUE CORREGIRLO E IRLO PASANDO A LA PARTE INFERIOR SI SIRVE, SI NO ELIMINARLO DEL SCRIPT    

    public String traerColonia(int idColonia) {
        //SELECT calles.colonia,colonias.colonia AS col FROM calles,colonias WHERE calle = '' AND calles.colonia = colonias.idColonia
        //SELECT colonias.colonia WHERE idColonia = 
        String colonia = "";
        try {
            this.db.Connect();
            rs = this.db.Select("colonia", "colonias", "idColonia = " + idColonia);
            if (rs.next()) {
                colonia = rs.getString(1);
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.traerColonia() : " + ex);
        }
        return colonia;
    }

    private int countColonias(int idMunicipio) {
        int count = 0;
        try {
            this.db.Connect();
            rs = this.db.Select("COUNT(idColonia)", "colonias", "municipio = " + idMunicipio);
            if (rs.next()) {
                count = rs.getInt(1);
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.countColonias() : " + ex);
        }
        return count;
    }

    private int countCalles(int idColonia) {
        int count = 0;
        try {
            this.db.Connect();
            rs = this.db.Select("COUNT(idCalle)", "calles", "colonia = " + idColonia);
            if (rs.next()) {
                count = rs.getInt(1);
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.countCalles() : " + ex);
        }
        return count;
    }

    private int countMunicipios(int idSucursal) {
        int count = 0;
        try {
            this.db.Connect();
            rs = this.db.Select("COUNT(idMunicipio)", "municipios", "sucursal = " + idSucursal);
            if (rs.next()) {
                count = rs.getInt("COUNT(idMunicipio)");
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.countMunicipios() : " + ex);
        }
        return count;
    }

    private int countAsentamientos() {
        int count = 0;
        try {
            this.db.Connect();
            rs = this.db.Select("COUNT(idTipoAsentamiento)", "tipo_asentamiento", "1");
            if (rs.next()) {
                count = rs.getInt(1);
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.countMunicipios() : " + ex);
        }
        return count;
    }

    public int idDomicilio(int numero) {
        int id = 0;
        try {
            this.db.Connect();
            rs = this.db.Select("idDomicilio", "domicilios", "numero = " + numero + " LIMIT 1");
            if (rs.next()) {
                id = rs.getInt(1);
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.idDomicilio() : " + ex);
        }
        return id;
    }

    public String traerCalle(String calle, int idColonia) {
        String street = null;
        try {
            this.db.Connect();
            rs = this.db.Select("calle", "calles", "calle = '" + calle + "' AND colonia = " + idColonia + " LIMIT 1");
            if (rs.next()) {
                street = rs.getString(1);
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.traerCalle() : " + ex);
        }
        return street;
    }

    public String traerColonia(String colonia, int municipio, int tipo) {
        String res = null;
        try {
            this.db.Connect();
            rs = this.db.Select("colonia", "colonias", "colonia = '" + colonia + "' AND municipio = " + municipio + " AND asentamiento = " + tipo + " LIMIT 1");
            if (rs.next()) {
                res = rs.getString(1);
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.traerColonia() : " + ex);
        }
        return res;
    }

    public String[][] traerCalles(int idColonia) {
        String[][] array = null;
        try {
            int count = this.countCalles(idColonia);
            if (count > 0) {
                this.db.Connect();
                array = new String[count][3];
                int i = 0;
                rs = this.db.Select("idCalle, calle, colonia", "calles", "calles.colonia = " + idColonia + " ORDER BY calle ASC");
                while (rs.next()) {
                    array[i][0] = rs.getString(1);
                    array[i][1] = rs.getString(2);
                    array[i][2] = rs.getString(3);
                    i++;
                }
                this.db.Disconnect();
            }
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.traerCalles() : " + ex);
        }
        return array;
    }

    public String[][] traerColonias(int idMunicipio) {
        String[][] array = null;
        try {
            int count = this.countColonias(idMunicipio);
            if (count > 0) {
                array = new String[count][5];
                int i = 0;
                this.db.Connect();
                rs = this.db.Select("idColonia, colonia, asentamiento, cp, municipio", "colonias", "municipio = " + idMunicipio + " ORDER BY colonia ASC");
                while (rs.next()) {
                    array[i][0] = rs.getString("idColonia");
                    array[i][1] = rs.getString("colonia");
                    array[i][2] = rs.getString("asentamiento");
                    array[i][3] = rs.getString("cp");
                    array[i][4] = rs.getString("municipio");
                    i++;
                }
                this.db.Disconnect();
            }
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.traerColonias() : " + ex);
        }
        return array;
    }

    public String[][] municipios(int idSucursal) {
        String[][] data = null;
        try {
            int count = countMunicipios(idSucursal);
//            System.out.println(count);
            if (count > 0) {
                data = new String[count][3];
                this.db.Connect();
                rs = this.db.Select("idMunicipio,municipio,estado", "municipios", "sucursal = " + idSucursal);
                int i = 0;
                while (rs.next()) {
                    data[i][0] = rs.getString("idMunicipio");
                    data[i][1] = rs.getString("municipio");
                    data[i][2] = rs.getString("estado");
//                    System.out.println(Arrays.toString(data[i]));
                    i++;
                }
                this.db.Disconnect();
            }
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.municipios() : " + ex);
        }
        return data;
    }

    public String[][] traerAsentamientos() {
        String[][] array = null;
        try {
            int count = this.countAsentamientos();
            if (count > 0) {
                array = new String[count][2];
                int i = 0;
                this.db.Connect();
                rs = this.db.Select("idTipoAsentamiento,tipoAsentamiento", "tipo_asentamiento", "1 ORDER BY tipoAsentamiento ASC");
                while (rs.next()) {
                    array[i][0] = rs.getString(1);
                    array[i][1] = rs.getString(2);
                    i++;
                }
                this.db.Disconnect();
            }
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.traerAsentamientos() : " + ex);
        }
        return array;
    }

    public String traerTipoAsentamiento(int idTipo) {
        String res = "";
        try {
            this.db.Connect();
            rs = this.db.Select("tipoAsentamiento", "tipo_asentamiento", "idTipoAsentamiento = " + idTipo);
            if (rs.next()) {
                res = rs.getString(1);
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.traerTipoAsentamiento() : " + ex);
        }
        return res;
    }

    public boolean guardarDomicilio(int sucursal, int calle, int numero, int calle1, int calle2, int colonia, int tipo, String propietario, String vigencia, int tiempoResidencia) {
        boolean flag;
        this.db.Connect();
        flag = this.db.Insert("domicilios", "sucursal, calle, numero, calle1, calle2, colonia, tipo, propietario, vigencia_contrato, tiempoResidencia",
                sucursal + "," + calle + "," + numero + "," + calle1 + "," + calle2 + "," + colonia + "," + tipo + ",'" + propietario + "','" + vigencia + "'," + tiempoResidencia);
        this.db.Disconnect();
        return flag;
    }

    private int countNumeros(int idCalle) {
        int count = 0;
        try {
            this.db.Connect();
            rs = this.db.Select("COUNT(idNumero)", "numerosdomiciliares", "calle = " + idCalle);
            if (rs.next()) {
                count = rs.getInt(1);
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.countNumeros() : " + ex);
        }
        return count;
    }

    public String[][] numeros(int idCalle) {
        String[][] array = null;
        try {
            int count = this.countNumeros(idCalle);
            if (count > 0) {
                array = new String[count][3];
                int i = 0;
                this.db.Connect();
                rs = this.db.Select("idNumero, numero, calle", "numerosdomiciliares", "calle = " + idCalle);
                while (rs.next()) {
                    array[i][0] = rs.getString(1);
                    array[i][1] = rs.getString(2);
                    array[i][2] = rs.getString(3);
                }
                this.db.Disconnect();
            }
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.numeros() : " + ex);
        }
        return array;
    }

    private int countDomicilios(String condicional) {
        int count = 0;
        try {
            this.db.Connect();
            rs = this.db.freeSelect("COUNT(idDomicilio)", "domicilios", condicional);
            if (rs.next()) {
                count = rs.getInt(1);
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.countDomicilios() : " + ex);
        }
        return count;
    }

    public String[][] domicilios(int sucursal) {
        String[][] domicilios = null;
        try {
            int count = this.countDomicilios("WHERE domicilios.sucursal = " + sucursal);
            if (count > 0) {
                domicilios = new String[count][8];
                int i = 0;
                this.db.Connect();
                rs = this.db.freeSelect("idDomicilio,calles.calle, numerosdomiciliares.numero, tipo_asentamiento.tipoAsentamiento, colonias.colonia, colonias.cp, municipios.municipio,estados.estado", "domicilios", "INNER JOIN calles ON domicilios.calle = calles.idCalle INNER JOIN numerosdomiciliares ON domicilios.numero = numerosdomiciliares.idNumero INNER JOIN colonias ON domicilios.colonia = colonias.idColonia INNER JOIN tipo_asentamiento ON colonias.asentamiento = tipo_asentamiento.idTipoAsentamiento INNER JOIN municipios ON municipios.idMunicipio = colonias.municipio INNER JOIN estados ON estados.idEstado = municipios.estado WHERE domicilios.sucursal = " + sucursal);
                while (rs.next()) {
                    String array[] = {String.valueOf(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)};
                    domicilios[i] = array;
                    i++;
                }
                this.db.Disconnect();
            }
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.domicilios() : " + ex);
        }
        return domicilios;
    }

    public String[][] domiciliosFiltrado(int sucursal, int idDomicilio) {
        String[][] domicilios = null;
        int i = 0;
        try {            
            int count = this.countDomicilios("WHERE domicilios.sucursal = " + sucursal + " AND domicilios.idDomicilio != " + idDomicilio);           
            this.db.Connect();
            if (count > 0) {               
                domicilios = new String[count][8];
                rs = this.db.freeSelect("idDomicilio,calles.calle, numerosdomiciliares.numero, tipo_asentamiento.tipoAsentamiento, colonias.colonia, colonias.cp, municipios.municipio,estados.estado", "domicilios", "INNER JOIN calles ON domicilios.calle = calles.idCalle INNER JOIN numerosdomiciliares ON domicilios.numero = numerosdomiciliares.idNumero INNER JOIN colonias ON domicilios.colonia = colonias.idColonia INNER JOIN tipo_asentamiento ON colonias.asentamiento = tipo_asentamiento.idTipoAsentamiento INNER JOIN municipios ON municipios.idMunicipio = colonias.municipio INNER JOIN estados ON estados.idEstado = municipios.estado WHERE domicilios.sucursal = " + sucursal + " AND domicilios.idDomicilio != " + idDomicilio);
                while (rs.next()) {
                    String array[] = {String.valueOf(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)};
                    domicilios[i] = array;
                    i++;
                }
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.domicilios() : " + ex);
        }
        return domicilios;
    }

    public String[] domicilio(int idDomicilio) {
        String[] domicilio = null;
        try {
            this.db.Connect();
            rs = this.db.freeSelect("idDomicilio, domicilios.calle, domicilios.numero, calle1, calle2, domicilios.colonia, tipo, propietario, vigencia_contrato, tiempoResidencia, calles.calle, numerosdomiciliares.numero, tipo_asentamiento.tipoAsentamiento, colonias.colonia, colonias.cp, municipios.municipio,estados.estado", "domicilios", "INNER JOIN calles ON domicilios.calle = calles.idCalle INNER JOIN numerosdomiciliares ON domicilios.numero = numerosdomiciliares.idNumero INNER JOIN colonias ON domicilios.colonia = colonias.idColonia INNER JOIN tipo_asentamiento ON colonias.asentamiento = tipo_asentamiento.idTipoAsentamiento INNER JOIN municipios ON municipios.idMunicipio = colonias.municipio INNER JOIN estados ON estados.idEstado = municipios.estado WHERE domicilios.idDomicilio = " + idDomicilio);
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

    public int contarAsociaciones(int idDomicilio) {
        int x = 0;
        try {
            this.db.Connect();
            rs = this.db.Select("COUNT(idPersona)", "personas", "domicilio = " + idDomicilio);
            if (rs.next()) {
                x = rs.getInt(1);
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.contarAsociaciones() : " + ex);
        }
        return x;
    }

    public boolean asociarDomicilio(int idPersona, int idDomicilio) {
        boolean flag;
        this.db.Connect();
        flag = this.db.Update("personas", "domicilio = " + idDomicilio, "idPersona = " + idPersona + " LIMIT 1");
        this.db.Disconnect();
        return flag;
    }

    public String[][] buscarDomicilios(String condicional) {
        String[][] domicilios = null;
        int i = 0;
        try {            
            int count = this.countDomicilios(condicional);
            if (count > 0) {
                this.db.Connect();
                domicilios = new String[count][8];
                rs = this.db.freeSelect("idDomicilio,calles.calle, numerosdomiciliares.numero, tipo_asentamiento.tipoAsentamiento, colonias.colonia, "
                        + "colonias.cp, municipios.municipio,estados.estado", "domicilios", 
                          "INNER JOIN calles ON domicilios.calle = calles.idCalle "
                        + "INNER JOIN numerosdomiciliares ON domicilios.numero = numerosdomiciliares.idNumero "
                        + "INNER JOIN colonias ON domicilios.colonia = colonias.idColonia "
                        + "INNER JOIN tipo_asentamiento ON colonias.asentamiento = tipo_asentamiento.idTipoAsentamiento "
                        + "INNER JOIN municipios ON municipios.idMunicipio = colonias.municipio "
                        + "INNER JOIN estados ON estados.idEstado = municipios.estado " + condicional);
                while (rs.next()) {
                    String array[] = {String.valueOf(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)};
                    domicilios[i] = array;
                    i++;
                }
                this.db.Disconnect();
            }            
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.buscarDomicilios() : " + ex);
        }
        return domicilios;
    }

    public String[][] buscarDomiciliosFiltrado(String condicional) {
        String[][] domicilios = null;
        int i = 0;
        try {
            this.db.Connect();
            int count = this.countDomiciliosFiltrado(condicional);
            if (count > 0) {
                domicilios = new String[count][8];
            }
            //System.out.println("SELECT idDomicilio,calles.calle, numerosdomiciliares.numero, tipo_asentamiento.tipoAsentamiento, colonias.colonia, colonias.cp, municipios.municipio,estados.estado FROM domicilios INNER JOIN calles ON domicilios.calle = calles.idCalle INNER JOIN numerosdomiciliares ON domicilios.numero = numerosdomiciliares.idNumero INNER JOIN colonias ON domicilios.colonia = colonias.idColonia INNER JOIN tipo_asentamiento ON colonias.asentamiento = tipo_asentamiento.idTipoAsentamiento INNER JOIN municipios ON municipios.idMunicipio = colonias.municipio INNER JOIN estados ON estados.idEstado = municipios.estado " + condicional);
            rs = this.db.freeSelect("idDomicilio,calles.calle, numerosdomiciliares.numero, tipo_asentamiento.tipoAsentamiento, colonias.colonia, colonias.cp, municipios.municipio,estados.estado", "domicilios", "INNER JOIN calles ON domicilios.calle = calles.idCalle INNER JOIN numerosdomiciliares ON domicilios.numero = numerosdomiciliares.idNumero INNER JOIN colonias ON domicilios.colonia = colonias.idColonia INNER JOIN tipo_asentamiento ON colonias.asentamiento = tipo_asentamiento.idTipoAsentamiento INNER JOIN municipios ON municipios.idMunicipio = colonias.municipio INNER JOIN estados ON estados.idEstado = municipios.estado " + condicional);
            while (rs.next()) {
                String array[] = {String.valueOf(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)};
                domicilios[i] = array;
                i++;
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.buscarDomiciliosFiltrado() : " + ex);
        }
        return domicilios;
    }

    public int countDomiciliosFiltrado(String condicional) {
        int count = 0;
        try {
            this.db.Connect();
            rs = this.db.freeSelect("COUNT(idDomicilio)", "domicilios", condicional);
            if (rs.next()) {
                count = rs.getInt(1);
            }
            this.db.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.countDomiciliosFiltrado() : " + ex);
        }
        return count;
    }
}
