package resources;

import database.conection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import objects.ErrorController;

/**
 * Contiene los métodos básicos para consultar, insertar y/o actualizar
 * registros de acuerdo a la necesidad del service (controlador)
 *
 * @author Root
 */
public class Cobrar_resource extends conection {

    ResultSet RS = null;
    ErrorController ERROR_CONTROLLER;

    public Cobrar_resource(String modulo) {
        this.MODULO = modulo;
    }

    /*NO MODIFICAR NI ELIMINAR ESTE BLOQUE*/
    public String[] getRow(int param) {
        String[] array = null;
        try {
            this.Connect();
            //SOLO CAMBIAR LA CONSULTA
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
            //CAMBIAR ESTA CONSULTA PARA OBTENER EL TOTAL DE REGISTROS DE LA TABLA E INICIALIZAR EL
            //TAMAÑO DE LA MATRIZ QUE CREAREMOS EN FILAS
            RS = this.Select("COUNT(filas)", "tabla", "condicion");
            if (RS.next()) {
                int filas = RS.getInt(1);
                if (filas > 0) {
                    RS = this.Select("idAdc,agencia,vacante", "personas_empleados_adc", "condicion");
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

    /*NO MODIFICAR NI ELIMINAR ESTE BLOQUE*/
    public String[][] getPrestamosByAdc(String filtro) {
        String[][] array = null;
        try {
            this.Connect();
            RS = this.Select("COUNT(*)", "prestamos", filtro);
            if (RS.next()) {
                int filas = RS.getInt(1);
                if (filas > 0) {
                    RS = this.Select("`idPrestamo`, `cliente`, `total_prestado`, `capital`, `interes`, `plazo`, `tarifa`", "prestamos", filtro);
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

    public String[] agencias(int sucursal) {
        String[] array = null;
        try {
            this.Connect();
            RS = this.freeSelect("COUNT(DISTINCT(agencia))", "personas_empleados_adc", "WHERE sucursal = " + sucursal);
            int count = 0;
            if (RS.next()) {
                count = RS.getInt(1);
            }
            if (count > 0) {
                array = new String[count];
                int i = 0;
                RS = this.freeSelect("DISTINCT(agencia)", "personas_empleados_adc", "WHERE sucursal = " + sucursal);
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

    public String[] vacantes(int sucursal, int agencia) {
        String[] array = null;
        try {
            this.Connect();
            RS = this.freeSelect("COUNT(DISTINCT(adc))", "prestamos", "WHERE sucursal = " + sucursal + " AND zona = " + agencia);
            int count = 0;
            if (RS.next()) {
                count = RS.getInt(1);
            }
            if (count > 0) {
                array = new String[count];
                int i = 0;
                RS = this.freeSelect("DISTINCT(adc)", "prestamos", "WHERE sucursal = " + sucursal + " AND zona = " + agencia);
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

    public boolean guardarPagos(String dataInsert) {
        this.Connect();
        boolean flag = this.InsertMultiple("prestamos_pagos", "prestamo, monto, fecha, cobrador,capturado", dataInsert);
        this.Disconnect();
        return flag;
    }

    public String[] getRango(String fecha) {
        String[] array = null;
        try {
            this.Connect();
            RS = this.freeSelect("inicio, fin", "semanas", "WHERE inicio <= '" + fecha + "' AND fin >= '" + fecha + "' LIMIT 1");
            if (RS.next()) {
                array = new String[RS.getMetaData().getColumnCount()];
                for (int i = 0; i < RS.getMetaData().getColumnCount(); i++) {
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

    public String[] getLastPayment(int sucursal) {
        String[] array = null;
        try {
            this.Connect();
            RS = this.freeSelect("capturado,monto,prestamo", "prestamos_pagos", "INNER JOIN prestamos ON prestamos_pagos.prestamo WHERE prestamos_pagos.prestamo = prestamos.idPrestamo AND prestamos.cobrado < prestamos.total_prestado AND prestamos.sucursal = " + sucursal + " ORDER BY idPago DESC LIMIT 1");
            if (RS.next()) {
                int size = RS.getMetaData().getColumnCount();
                array = new String[size];
                for (int i = 0; i < size; i++) {
                    array[i] = RS.getString(i + 1);
                }
            }
            System.out.println(Arrays.toString(array));
            this.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }
}
