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

    private final conection DB;
    ResultSet RS = null;
    ErrorController ERROR_CONTROLLER;
    private final String modulo;

    public solicitudCredito_resource(String modulo) {
        this.modulo = modulo;
        this.DB = new conection();        
    }

    public String[] getSolicitud(int idSolicitud, int idSucusal) {
        String[] array = null;
        try {
            this.DB.Connect();
            RS = this.DB.Select("*", "prestamos_solicitudes", "idSolicitud = " + idSolicitud + " AND sucursal = " + idSucusal + " LIMIT 1");
            if (RS.next()) {
                int size = RS.getMetaData().getColumnCount();
                array = new String[size];
                for (int i = 0; i < size; i++) {
                    array[i] = RS.getString(i + 1);
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public String[][] getAdcFromSucursal(int idSucursal) {
        String[][] array = null;
        try {
            this.DB.Connect();
            String condicion = "sucursal = " + idSucursal + " AND idStaff != 0";
            RS = this.DB.Select("COUNT(*)", "personas_empleados_adc", condicion);
            if (RS.next()) {
                int count = RS.getInt(1);
                if (count > 0) {
                    array = new String[count][3];
                    RS = this.DB.Select("idAdc,agencia,vacante", "personas_empleados_adc", condicion);
                    count = count - count;
//                    System.out.println(count);
                    while (RS.next()) {
//                        System.out.println(count);
                        array[count][0] = RS.getString(1);
                        array[count][1] = RS.getString(2);
                        array[count][2] = RS.getString(3);
                        count++;
                    }
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public String[][] solicitudes(String filtro) {
        String[][] array = null;
        try {
            this.DB.Connect();
            RS = this.DB.freeSelect("COUNT(*)", "prestamos_solicitudes", filtro);
            if (RS.next()) {
                int count = RS.getInt(1);
//                System.out.println("RESULTADOS: " + count);
                if (count > 0) {
                    RS = this.DB.freeSelect("prestamos_solicitudes.*", "prestamos_solicitudes", filtro);
                    int cols = RS.getMetaData().getColumnCount();
                    array = new String[count][cols];
                    count = count - count;
                    while (RS.next()) {
                        for (int i = 0; i < cols; i++) {
                            array[count][i] = RS.getString(i + 1);
                        }
//                        System.out.println(Arrays.toString(array[count]));
                        count++;
                    }
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public int[] clientesDeAdc(int adc) {
        int[] array = null;
        try {
            this.DB.Connect();
            RS = this.DB.Select("COUNT(DISTINCT(idCliente))", "personas_clientes", "adc = " + adc);
            if (RS.next()) {
                int count = RS.getInt(1);
                if (count > 0) {
                    RS = this.DB.freeSelect("DISTINCT(idCliente)", "personas_clientes", "adc = " + adc);
                    array = new int[count];
                    count = 0;
                    while (RS.next()) {
                        array[count] = RS.getInt(1);
                        count++;
                    }
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public int contarSolicitudesDeClientes(String condicion) {
        int count = 0;
        try {
            this.DB.Connect();
            RS = this.DB.Select("COUNT(*)", "prestamos_solicitudes", condicion);
            if (RS.next()) {
                count = RS.getInt(1);
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return count;
    }

    public boolean cambiarEstadoSolicitud(int id, int estado) {        
        this.DB.Connect();
        boolean flag = this.DB.Update("prestamos_solicitudes", "estado = " + estado, "idSolicitud = " + id + " LIMIT 1");
        this.DB.Disconnect();
        return flag;
    }
    
    public boolean guardarSolicitud(int MONTO, int PLAZO, int CLIENTE, int USUARIO, int SUCURSAL, double TASA) {
        try {
            this.DB.Connect();
            this.DB.Insert("prestamos_solicitudes", "monto,tasa,plazo,cliente,usuario,sucursal,fecha,hora", MONTO + "," + TASA + "," + PLAZO + "," + CLIENTE + "," + USUARIO + "," + SUCURSAL + ",now(),now()");
            this.DB.Disconnect();
            return true;
        } catch (Exception ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            return false;
        }
    }
    
    public String[] fechaSolicitudAnterior(int idCliente) {
        String[] array = null;
        try {
            this.DB.Connect();
            RS = this.DB.Select("*", "prestamos_solicitudes", "cliente=" + idCliente + " ORDER BY idSolicitud DESC LIMIT 1");
            if (RS.next()) {
                array = new String[10];
                array[0] = RS.getString(1);
                array[1] = RS.getString(2);
                array[2] = RS.getString(3);
                array[3] = RS.getString(4);
                array[4] = RS.getString(5);
                array[5] = RS.getString(6);
                array[6] = RS.getString(7);
                array[7] = RS.getString(8);
                array[8] = RS.getString(9);
                array[9] = RS.getString(10);
            }
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public String fechaServidor() {
        String hora = null;
        try {
            this.DB.Connect();
            RS = this.DB.fullSelect("SELECT now()");
            if (RS.next()) {
                hora = RS.getString(1);
            }            
            this.DB.Disconnect();            
            return hora;
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            return hora;
        }
    }

}
