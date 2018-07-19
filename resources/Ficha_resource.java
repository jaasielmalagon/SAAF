package resources;

import database.conection;
import java.sql.ResultSet;
import java.sql.SQLException;
import objects.ErrorController;

/**
 *
 * @author JMalagon
 */
public class Ficha_resource extends conection {

    ResultSet RS = null;
    ErrorController ERROR_CONTROLLER;

    public Ficha_resource(String modulo) {
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
    public String estado(int id) {
        String estado = null;
        if (id > 0) {
            try {
                this.Connect();
                //SOLO CAMBIAR LA CONSULTA
                RS = this.Select("estado", "estados", "idEstado = " + id);
                if (RS.next()) {
                    estado = RS.getString(1);
                }
                this.Disconnect();
            } catch (SQLException ex) {
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
                this.ERROR_CONTROLLER.escribirErrorLogger(this.MODULO, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            }
        }
        return estado;
    }

    public String[] getCliente(int idPersona) {
        String[] array = null;
        try {
            this.Connect();
            //SOLO CAMBIAR LA CONSULTA
            RS = this.freeSelect("adc,ingresos,egresos,tipo_ocupaciones.ocupacion,dependientes,tipo_estudios.estudios,empresa,horario_entrada,horario_salida,domicilio_empresa,tel_empresa,tipo_vivienda,vigencia_contrato,tiempo_residencia,score,status,actividad,registro", "`personas_clientes`", "INNER JOIN tipo_ocupaciones ON personas_clientes.ocupacion = tipo_ocupaciones.idTipo INNER JOIN tipo_estudios ON personas_clientes.estudios = tipo_estudios.idTipoEstudios WHERE personas_clientes.idPersona = " + idPersona + " LIMIT 1");
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
}
/*SELECT adc,ingresos,egresos,tipo_ocupaciones.ocupacion,dependientes,tipo_estudios.estudios,empresa,horario_entrada,horario_salida,domicilio_empresa,tel_empresa,tipo_vivienda,vigencia_contrato,tiempo_residencia,score,status,actividad,registro FROM `personas_clientes` 
INNER JOIN tipo_ocupaciones ON personas_clientes.ocupacion = tipo_ocupaciones.idTipo
INNER JOIN tipo_estudios ON personas_clientes.estudios = tipo_estudios.idTipoEstudios WHERE personas_clientes.idPersona = 1*/