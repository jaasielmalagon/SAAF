package resources;

import database.conection;
import java.sql.ResultSet;
import java.sql.SQLException;
import objects.ErrorController;

/**
 *
 * @author mield
 */
public class Nomina_resource extends conection {

    //private final conection DB;
    ResultSet RS;
    ErrorController ERROR_CONTROLLER;
    //private final String MODULO;

    public Nomina_resource(String modulo) {
        //this.DB = new conection();
        //this.ERROR_CONTROLLER = new ErrorController();
        this.MODULO = modulo;
    }

    public String[] getRow(int param) {
        String[] array = null;
        try {
            this.Connect();
            RS = this.Select("*", "tabla", "condicion");
            if (RS.next()) {
                int size = RS.getMetaData().getColumnCount();
                array = new String[size];
                for (int i = 0; i < size; i++) {
                    array[i] = RS.getString(i + 1);
                }
            }
            this.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public String[][] getRows(int param) {
        String[][] array = null;
        try {
            this.Connect();
            RS = this.Select("COUNT(filas)", "tabla", "condicion");
            if (RS.next()) {
                int filas = RS.getInt(1);
                if (filas > 0) {
                    RS = this.Select("idPersona,sucursal,departamento", "personas_empleados", "condicion");
                    int columnas = RS.getMetaData().getColumnCount();
                    array = new String[filas][columnas];
                    filas = 0;
                    while (RS.next()) {
                        columnas = 1;
                        for (int i = 0; i < array[filas].length; i++) {
                            array[filas][i] = RS.getString(columnas);
                            columnas++;
                        }
                        filas++;
                    }
                }
            }
            this.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public boolean setSomething(String param) {
        this.Connect();
        boolean flag = this.Insert("tabla", "campos", "valores");
        this.Disconnect();
        return flag;
    }

    /*Buscar una nómina en la base de datos mediante un Id
    *@params Id: identificador del empleado
    *@return resultado: null
    *@since 1.0
     */
    public String[][] nomina(String filtroBusqueda) {
        return null;
    }

    /*Realiza una búsqueda en la base de datos del id y el cargo deseado
    *@params: filtro de busqueda por id
    *@return resultado: Tipo de cargos en forma ascendente
    *@since: 1.0
     */
    public String[][] cargos() {
        String[][] array = null;
        try {
            this.Connect();
            RS = this.freeSelect("COUNT(idCargo)", "tipo_cargo", "");
            int count = 0;
            if (RS.next()) {
                count = RS.getInt(1);
            }
            if (count > 0) {
                array = new String[count][2];
                int i = 0;
                RS = this.freeSelect("*", "tipo_cargo", "ORDER BY cargo ASC");
                while (RS.next()) {
                    array[i][0] = RS.getString(1);
                    array[i][1] = RS.getString(2);
                    i++;
                }
            }
            this.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    /*Realiza una búsqueda en la base de datos para traer la id y mostrar el cargo deseado
    *@params: Ninguno
    *@return resultado: tipos de Cargo Ordenados en forma ascendente
    *@since: 1.0
     */
    public String[][] getCargos() {
        String[][] array = null;
        try {
            this.Connect();
            RS = this.freeSelect("COUNT(*)", "tipo_cargo", "");
            int count = 0;
            if (RS.next()) {
                count = RS.getInt(1);
                if (count > 0) {
                    RS = this.freeSelect("idCargo,cargo", "tipo_cargo", "ORDER BY cargo ASC");
                    int columnas = RS.getMetaData().getColumnCount();
                    array = new String[count][columnas];
                    count = 0;
                    while (RS.next()) {
                        columnas = 1;
                        for (int i = 0; i < array[count].length; i++) {
                            array[count][i] = RS.getString(columnas);
                            columnas++;
                        }
                        count++;
                    }
                }
            }
            this.Disconnect();
        } catch (SQLException e) {
        }
        return array;
    }

    public String[] departamentos(int departamento) {
        String[] array = null;
        try {
            this.Connect();
            RS = this.freeSelect("COUNT(DISTINCT(departamento))", "personas_empleados", "WHERE departamento = " + departamento);
            int count = 0;
            if (RS.next()) {
                count = RS.getInt(1);
            }
            if (count > 0) {
                array = new String[count];
                int i = 0;
                RS = this.freeSelect("DISTINCT(departamento)", "personas_empleados", "WHERE departamento = " + departamento);
                while (RS.next()) {
                    array[i] = RS.getString(1);
                    i++;
                }
            }
            this.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public String[][] departamentos() {
        String[][] array = null;
        try {
            this.Connect();
            RS = this.freeSelect("COUNT(idTipoUsuario)", "tipo_usuarios", "");
            int count = 0;
            if (RS.next()) {
                count = RS.getInt(1);
            }
            if (count > 0) {
                array = new String[count][2];
                int i = 0;
                RS = this.freeSelect("*", "tipo_usuarios", "ORDER BY tipo ASC");
                while (RS.next()) {
                    array[i][0] = RS.getString(1);
                    array[i][1] = RS.getString(2);
                    i++;
                }
            }
            this.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public String[] usuario(int departamento, int cargo) {
        String[] array = null;
        try {
            this.Connect();
            RS = this.freeSelect("COUNT(DISTINCT(departamento))", "personas_empleados", "WHERE departamento = " + departamento + " AND cargo = " + cargo);
            int count = 0;
            if (RS.next()) {
                count = RS.getInt(1);
            }
            if (count > 0) {
                array = new String[count];
                int i = 0;
                RS = this.freeSelect("DISTINCT(departamento)", "personas_empleados", "WHERE departamento = " + departamento + " AND cargo = " + cargo);
                while (RS.next()) {
                    array[i] = RS.getString(1);
                    i++;
                }
            }
            this.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }
    
    public String[][] getSemanas() {
        String[][] array = null;
        try {
            this.Connect();
            RS = this.freeSelect("COUNT(id)", "semanas", "");
            if (RS.next()) {
                int filas = RS.getInt(1);
                if (filas > 0) {
                    RS = this.freeSelect("id, numero", "semanas", "");
                    int columnas = RS.getMetaData().getColumnCount();
                    array = new String[filas][columnas];
                    filas = 0;
                    while (RS.next()) {
                        columnas = 1;
                        for (int i = 0; i < array[filas].length; i++) {
                            array[filas][i] = RS.getString(columnas);
                            columnas++;
                        }
                        filas++;
                    }
                }
            }
            this.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }
}
