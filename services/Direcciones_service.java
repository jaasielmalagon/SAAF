package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import objects.Asentamiento;
import resources.Direcciones_resource;
import objects.Calle;
import objects.Colonia;
import objects.Municipio;

/**
 *
 * @author JMalagon
 */
public class Direcciones_service {

    private final Direcciones_resource recurso;
    private ResultSet rs;
    public Direcciones_service() {
        this.recurso = new Direcciones_resource();
    }
/*
    //FUNCIONALES EN POO
    public boolean buscarNumero(String numero, int idCalle) throws SQLException {
        boolean flag = false;
        try {
            this.recurso.conectar();
            String c = this.recurso.traerNumero(numero, idCalle);
            if (c != null && !"".equals(c)) {
                flag = true;
            }
            this.recurso.desconectar();
        } catch (Exception e) {
        }
        return flag;
    }

    public boolean buscarCalle(String calle, int colonia) throws SQLException {
        boolean flag = false;
        try {
            this.recurso.conectar();
            String c = this.recurso.traerCalle(calle, colonia);
            if (c != null && !"".equals(c)) {
                flag = true;
            }
            this.recurso.desconectar();
        } catch (Exception e) {
        }
        return flag;
    }

    public boolean buscarColonia(String colonia, int municipio, int tipo) throws SQLException {
        boolean flag = false;
        try {
            this.recurso.conectar();
            String c = this.recurso.traerColonia(colonia, municipio, tipo);
            if (c != null && !"".equals(c)) {
                flag = true;
            }
            this.recurso.desconectar();
        } catch (Exception e) {
        }
        return flag;
    }

    public Calle[] calles(int idColonia) throws SQLException {
        Calle[] calles = null;
        try {
            this.recurso.conectar();
            rs = this.recurso.countCalles(idColonia);
            if (rs.next()) {                
                calles = new Calle[rs.getInt("COUNT(idCalle)")];
            }
            rs = this.recurso.calles(idColonia);
            int i = 0;
            while (rs.next()) {
                System.err.println(rs.getString("calle"));
                calles[i] = new Calle(rs.getInt("idCalle"), rs.getString("calle"), rs.getInt("colonia"));
                i++;
            }
            this.recurso.desconectar();
        } catch (Exception e) {
        }
        return calles;
    }

    public Colonia[] colonias(int idMunicipio) throws SQLException {
        Colonia[] values = null;
        try {
            this.recurso.conectar();
            rs = this.recurso.countColonias(idMunicipio);
            if (rs.next()) {
                values = new Colonia[rs.getInt("COUNT(idColonia)")];
            }
            rs = this.recurso.colonias(idMunicipio);
            int i = 0;
            while (rs.next()) {
                values[i] = new Colonia(rs.getInt("idColonia"), rs.getString("colonia"), rs.getInt("asentamiento"), rs.getInt("cp"), rs.getInt("municipio"));
                i++;
            }
            this.recurso.desconectar();
        } catch (Exception e) {
        }
        return values;
    }

    public String colonia(int idColonia) throws SQLException {
        String col = null;
        try {
            this.recurso.conectar();
            rs = this.recurso.colonia(idColonia);
            if (rs.next()) {
                col = rs.getString("colonia");
            }
            this.recurso.desconectar();
        } catch (Exception e) {
        }
        return col;
    }

    public Municipio[] municipios() throws SQLException {
        Municipio[] municipios = null;
        try {
            this.recurso.conectar();
            rs = this.recurso.countMunicipios();
            if (rs.next()) {
                municipios = new Municipio[rs.getInt("COUNT(idMunicipio)")];
            }
            rs = this.recurso.municipios();
            int i = 0;
            while (rs.next()) {
                municipios[i] = new Municipio(rs.getInt("idMunicipio"), rs.getString("municipio"), rs.getInt("estado"));
                i++;
            }
            this.recurso.desconectar();
        } catch (Exception e) {
        }
        return municipios;
    }

    public Asentamiento[] asentamientos() throws SQLException {
        Asentamiento[] values = null;
        try {
            this.recurso.conectar();
            rs = this.recurso.countAsentamientos();
            if (rs.next()) {
                values = new Asentamiento[rs.getInt("COUNT(idTipoAsentamiento)")];
            }
            rs = this.recurso.asentamientos();
            int i = 0;
            while (rs.next()) {
                values[i] = new Asentamiento(rs.getInt("idTipoAsentamiento"), rs.getString("tipoAsentamiento"));
                i++;
            }
            this.recurso.desconectar();
        } catch (Exception e) {
        }
        return values;
    }

    public String tipoAsentamiento(int idTipo) throws SQLException {
        String tipo = null;
        try {
            this.recurso.conectar();
            rs = this.recurso.tipoAsentamiento(idTipo);
            if (rs.next()) {
                tipo = rs.getString("tipoAsentamiento");
            }
            this.recurso.conectar();
        } catch (Exception e) {
        }
        return tipo;
    }

    public boolean guardarNumero(String numero, int calle) throws SQLException {
        this.recurso.conectar();
        boolean b = this.recurso.guardarNumero(numero, calle);
        this.recurso.desconectar();
        return b;
    }

    public boolean guardarColonia(String colonia, int idMunicipio, int asentamiento, int cp) throws SQLException {
        this.recurso.conectar();
        boolean b = this.recurso.guardarColonia(colonia, idMunicipio, asentamiento, cp);
        this.recurso.desconectar();
        return b;
    }

    public boolean guardarCalle(String calle, int idColonia) throws SQLException {
        this.recurso.conectar();
        boolean b = this.recurso.guardarCalle(calle, idColonia);
        this.recurso.desconectar();
        return b;
    }

    //TERMINAN FUNCIONALES EN POO*/
}
