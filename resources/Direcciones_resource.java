package resources;

import database.conection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author JMalagon
 */
public class Direcciones_resource {

    private final conection db;

    public Direcciones_resource() {
        this.db = new conection();
    }//MÉTODOS USADOS PARA ORIENTACIÓN A OBJETOS, LO DE ARRIBA HAY QUE CORREGIRLO E IRLO PASANDO A LA PARTE INFERIOR SI SIRVE, SI NO ELIMINARLO DEL SCRIPT

    

    public ResultSet colonia(int idColonia) throws SQLException {
        //SELECT calles.colonia,colonias.colonia AS col FROM calles,colonias WHERE calle = '' AND calles.colonia = colonias.idColonia
        //SELECT colonias.colonia WHERE idColonia = 
        ResultSet rs = this.db.Select("colonias.colonia", "colonias", "idColonia = " + idColonia);
        return rs;
    }

    public ResultSet countColonias(int idMunicipio) throws SQLException {
        ResultSet rs = this.db.Select("COUNT(idColonia)", "colonias", "municipio = " + idMunicipio);
        return rs;
    }

    public ResultSet countCalles(int idColonia) throws SQLException {
        ResultSet rs = this.db.Select("COUNT(idCalle)", "calles", "colonia = " + idColonia);
        return rs;
    }

    public ResultSet countMunicipios() throws SQLException {
        ResultSet rs = this.db.Select("COUNT(idMunicipio)", "municipios", "estado = 21");
        return rs;
    }

    public ResultSet countAsentamientos() throws SQLException {
        ResultSet rs = this.db.Select("COUNT(idTipoAsentamiento)", "tipo_asentamiento", "1");
        return rs;
    }

    public String traerNumero(String numero, int idCalle) throws SQLException {
        ResultSet rs;
        String street = null;
        rs = this.db.Select("numero", "numerosdomiciliares", "numero = '" + numero + "' AND calle = " + idCalle + " LIMIT 1");
        if (rs.next()) {
            street = rs.getString("numero");
        }
        return street;
    }

    public String traerCalle(String calle, int idColonia) throws SQLException {
        ResultSet rs;
        String street = null;
        rs = this.db.Select("calle", "calles", "calle = '" + calle + "' AND colonia = " + idColonia + " LIMIT 1");
        if (rs.next()) {
            street = rs.getString("calle");
        }
        return street;
    }

    public String traerColonia(String colonia, int municipio, int tipo) throws SQLException {
        ResultSet rs;
        String street = null;
        rs = this.db.Select("colonia", "colonias", "colonia = '" + colonia + "' AND municipio = " + municipio + " AND asentamiento = " + tipo + " LIMIT 1");
        if (rs.next()) {
            street = rs.getString("colonia");
        }
        return street;
    }

    public ResultSet calles(int idColonia) throws SQLException {
        ResultSet rs = this.db.Select("idCalle, calle, colonia", "calles", "calles.colonia = " + idColonia + " ORDER BY calle ASC");
        return rs;
    }

    public ResultSet colonias(int idMunicipio) throws SQLException {
        ResultSet rs = this.db.Select("`idColonia`, `colonia`, `asentamiento`, `cp`, `municipio`", "colonias", "municipio = " + idMunicipio + " ORDER BY colonia ASC");
        return rs;
    }

    public ResultSet municipios() throws SQLException {
        ResultSet rs = this.db.Select("idMunicipio,municipio,estado", "municipios", "estado = 21");
        return rs;
    }

    public ResultSet asentamientos() throws SQLException {
        ResultSet rs = this.db.Select("idTipoAsentamiento,tipoAsentamiento", "tipo_asentamiento", "1 ORDER BY tipoAsentamiento ASC");
        return rs;
    }

    public ResultSet tipoAsentamiento(int idTipo) throws SQLException {
        ResultSet rs = this.db.Select("tipoAsentamiento", "tipo_asentamiento", "idTipoAsentamiento = " + idTipo);
        return rs;
    }

    public boolean guardarNumero(String numero, int calle) throws SQLException {
        return this.db.Insert("numerosdomiciliares", "numero,calle", "'" + numero + "'," + calle);
    }

    public boolean guardarCalle(String calle, int idColonia) throws SQLException {
        return this.db.Insert("calles", "calle,colonia", "'" + calle + "'," + idColonia);
    }

    public boolean guardarColonia(String colonia, int idMunicipio, int tipoAsentamiento, int cp) throws SQLException {
        System.out.println("'" + colonia + "'," + idMunicipio + "," + tipoAsentamiento + "," + cp);
        return this.db.Insert("colonias", "colonia,municipio,asentamiento,cp", "'" + colonia + "'," + idMunicipio + "," + tipoAsentamiento + "," + cp);
    }

    //TERMINAN MÉTODOS DE POO
}
