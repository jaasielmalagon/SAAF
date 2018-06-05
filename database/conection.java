package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import objects.ErrorController;

/**
 *
 * @author JMalagon Esta clase ha sido incorporada para el uso práctico de las
 * sentencias más concurridas en el sistema gestor de bases de datos utilizado
 * (MySQL), en la cual se manejan 5 métodos con los nombres Connect, Insert,
 * Select, Update y Delete; los cuales a su vez retornan los tipos de datos
 * Connection, boolean, ResultSet, boolean y boolean, respectivamente.
 */
public class conection {

    Statement estado = null;
    ErrorController ERROR_CONTROLLER;
    ResultSet rs = null;

    String servidor = "localhost";
    String usuario = "root";
    String password = "";
    String basedatos = "avante";
//    String servidor = "avantecorporativo.com:3306";
//    String usuario = "avante";
//    String password = ",C&RTZ@@+I2{";
//    String basedatos = "avanteDB";

    public conection() {
        this.ERROR_CONTROLLER = new ErrorController();
    }

    /*
    Connect() es el método de conexión principal utilizado para realizar un enlace entre la base de datos MySQL y 
    la aplicación, el cual a su vez retorna una variable de tipo Connection, con el enlace de conexión en caso de ser
    válidos los datos proporcionados para la conexión o una excepción y una variable de tipo Connection con el valor null
    en caso de no poder realizar el enlace por causa de datos erroneos o falla en la red
     */
    public boolean Connect() {
        Connection cnx = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection("jdbc:mysql://" + servidor + "/" + basedatos + "?"
                    + "zeroDateTimeBehavior=convertToNull", usuario, password);
            estado = cnx.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            System.out.println("database.conection.Connect() : CONEXIÓN ABIERTA EXITOSAMENTE");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("database.conection.Connect() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("conection", "database.conection.Connect() : " + ex);
            return false;
        }
    }

    public boolean Disconnect() {
        try {
            if (estado != null) {
                estado.close();
                estado = null;
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("database.conection.Disconnect() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("conection", "database.conection.Disconnect() : " + ex);
            return false;
        }
    }

    public int InsertId(String tabla, String campos, String valores) {
        int idGenerado = 0;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://" + servidor + "/" + basedatos + "?"
                + "zeroDateTimeBehavior=convertToNull", usuario, password)) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO " + tabla + "(" + campos + ") VALUES (" + valores + ")", Statement.RETURN_GENERATED_KEYS);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("No se pudo guardar");
            }

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                idGenerado = generatedKeys.getInt(1);
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("database.conection.InsertId() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("conection", "database.conection.InsertId() : " + ex);
        }
        return idGenerado;
    }

    /*
    Insert() es un método que puede ser utilizado para ingresar los datos recibidos desde las capas superiores a la base de
    datos de manera directa, pues no cuenta con filtrado de caracteres
     */
    public boolean Insert(String tabla, String campos, String valores) {
        boolean xsas = false;
        if (tabla.isEmpty() || campos.isEmpty() || valores.isEmpty()) {
            System.err.println("No hay datos para guardar.");
        } else {
            try {
                if (estado != null) {
                    //System.out.println("INSERT INTO " + tabla + "(" + campos + ") VALUES (" + valores + ")");
                    estado.execute("INSERT INTO " + tabla + "(" + campos + ") VALUES (" + valores + ")");
                    xsas = true;
                }
            } catch (SQLException ex) {
                System.out.println("database.conection.Insert() : " + ex);
                this.ERROR_CONTROLLER.escribirErrorLogger("conection", "database.conection.Insert() : " + ex);
            }
        }
        return xsas;
    }

    /*
    Select() es un método que puede ser utilizado para obtener los datos desde la base de
    datos de manera directa, pues no cuenta con filtrado de caracteres
     */
    public ResultSet Select(String campos, String tabla, String condicion) {
        try {
            if (estado != null) {
                rs = estado.executeQuery("SELECT " + campos + " FROM " + tabla + " WHERE " + condicion);                
            } else {
                rs = null;
            }
        } catch (SQLException ex) {
            System.out.println("database.conection.Select() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("conection", "database.conection.Select() : " + ex);
        }
        return rs;
    }

    public ResultSet freeSelect(String campos, String tabla, String clausula) {
        try {
            if (estado != null) {
                rs = estado.executeQuery("SELECT " + campos + " FROM " + tabla + " " + clausula);
            } else {
                rs = null;
            }
        } catch (SQLException ex) {
            rs = null;
            System.out.println("database.conection.freeSelect() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("conection", "database.conection.freeSelect() : " + ex);
        }
        return rs;
    }
    
    public ResultSet fullSelect(String sql) {
        try {
            if (estado != null) {
                rs = estado.executeQuery(sql);
            } else {
                rs = null;
            }
        } catch (SQLException ex) {
            rs = null;
            System.out.println("database.conection.fullSelect() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("conection", "database.conection.fullSelect() : " + ex);
        }
        return rs;
    }

    /*
    Update() es un método que puede ser utilizado para actualizar los datos recibidos desde las capas superiores a la base de
    datos de manera directa, pues no cuenta con filtrado de caracteres
     */
    public boolean Update(String tabla, String valores, String condicion) {
        boolean xsas = false;
        if (tabla.isEmpty() || condicion.isEmpty() || valores.isEmpty()) {
            System.err.println("No hay datos para actualizar.");
        } else {
            try {
                if (estado != null) {
                    estado.execute("UPDATE " + tabla + " SET " + valores + " WHERE " + condicion);
                    xsas = true;
                }
            } catch (SQLException ex) {
                System.out.println("database.conection.Update() : " + ex);
                this.ERROR_CONTROLLER.escribirErrorLogger("conection", "database.conection.Update() : " + ex);
            }
        }
        return xsas;
    }

    /*
    Delete() es un método que puede ser utilizado para eliminar un registro existente en la base de datos y en el cual
    se debe utilizar una sentencia condicional reemplazando la variable "condicion" con la sentencia que se requiera, ya que
    de no ser requerida dicha sentencia, el método puede poner en peligro la información de toda la tabla
     */
    public boolean Delete(String tabla, String condicion) {
        boolean xsas = false;
        if (tabla.isEmpty() || condicion.isEmpty()) {
            xsas = false;
        } else {
            try {
                if (estado != null) {
                    estado.execute("DELETE FROM " + tabla + " WHERE " + condicion);
                    xsas = true;
                }
            } catch (SQLException ex) {
                System.out.println("database.conection.Delete() : " + ex);
                this.ERROR_CONTROLLER.escribirErrorLogger("conection", "database.conection.Delete() : " + ex);
            }
        }
        return xsas;
    }
}
