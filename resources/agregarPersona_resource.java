package resources;

import database.conection;
import java.sql.ResultSet;
import java.sql.SQLException;
import objects.ErrorController;

/**
 *
 * @author JMalagon
 */
public class agregarPersona_resource {//implements interfaces.conection {

    private final conection DB;
    ResultSet RS = null;
    ErrorController ERROR_CONTROLLER;
    private final String modulo;

    public agregarPersona_resource(String modulo) {
        this.DB = new conection();
        this.ERROR_CONTROLLER = new ErrorController();
        this.modulo = modulo;
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
                array = new String[9];
                array[0] = RS.getString(1);
                array[1] = RS.getString(2);
                array[2] = RS.getString(3);
                array[3] = RS.getString(4);
                array[4] = RS.getString(5);
                array[5] = RS.getString(6);
                array[6] = RS.getString(7);
                array[7] = RS.getString(8);
                array[8] = RS.getString(9);
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

    public String[] cliente(int idPersona) {
        String[] array = null;
        try {
            this.DB.Connect();
            RS = this.DB.Select("*", "personas_clientes", "idPersona = " + idPersona + " LIMIT 1");
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

    public boolean agregarReferencia(int idCliente, String valores) {
        boolean flag = false;
        this.DB.Connect();
        flag = this.DB.Update("personas", valores, "idPersona = " + idCliente + " LIMIT 1");
        this.DB.Disconnect();
        return flag;
    }

    public boolean buscarReferencia(String condicion) {
        boolean flag = false;
        try {            
            this.DB.Connect();
            RS = this.DB.Select("idPersona", "personas", condicion);
            if (RS.next()) {
                int id = RS.getInt(1);
                String idX = RS.getString(1);
                if (id > 0 || idX != null) {
                    flag = true;
                }
            }
            this.DB.Disconnect();            
        } catch (SQLException ex) {
            System.out.println("resources.agregarPersona_resource.buscarReferencia() : " + ex);
        }
        return flag;
    }

    public int buscarPersonaSinDomicilio(int idPersona) {
        int id = 0;
        try {
            this.DB.Connect();
            RS = this.DB.Select("domicilio", "personas", "idPersona = " + idPersona + " AND domicilio > 0 LIMIT 1");
            if (RS.next()) {
                id = RS.getInt(1);
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarPersona_resource.buscarReferenciaSinDomicilio() : " + ex);
        }
        return id;
    }

    public int contarAsociaciones(int idDomicilio) {
        int x = 0;
        try {
            this.DB.Connect();
            RS = this.DB.Select("COUNT(idPersona)", "personas", "domicilio = " + idDomicilio);
            if (RS.next()) {
                x = RS.getInt(1);
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarDirecciones_resource.contarAsociaciones() : " + ex);
        }
        return x;
    }

    public boolean asociarDomicilio(int idPersona, int idDomicilio) {
        boolean flag;
        this.DB.Connect();
        flag = this.DB.Update("personas", "domicilio = " + idDomicilio, "idPersona = " + idPersona + " LIMIT 1");
        this.DB.Disconnect();
        return flag;
    }

    public String estado(int id) {
        String estado = null;
        try {
            this.DB.Connect();
            RS = this.DB.Select("estado", "estados", "idEstado = " + id + " LIMIT 1");
            if (RS.next()) {
                estado = RS.getString(1);
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarPersona_resource.estados() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", "resources.agregarPersona_resource.estados() : " + ex);
        }
        return estado;
    }

    public int buscarCliente(String curp, String ocr, String cel, String tel) {
        int idEncontrado = 0;
        try {
            this.DB.Connect();
            this.RS = this.DB.Select("idPersona", "personas", "curp = '" + curp + "' OR ocr = '" + ocr + "' OR celular ='" + cel + "' OR celular = '" + tel + "' LIMIT 1");
            if (this.RS.next()) {
                idEncontrado = this.RS.getInt(1);
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarPersona_resource.buscarCliente() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", "resources.agregarPersona_resource.buscarCliente() : " + ex);
        }
        return idEncontrado;
    }

    public boolean guardarDatosCliente(int idUsuario, int sucuRSal, String nombre, String apaterno, String amaterno, String f_nac, int idEstadoNac, String curp, String ocr, String sexo, int edoCivil, String tel, String cel) {
        boolean flag = false;
        this.DB.Connect();
        this.DB.Insert("personas", "`sucuRSal`,`registro`,`usuario`,`nombre`, `apaterno`, `amaterno`, `nacimiento`, `entidad`, `curp`, `ocr`, `sexo`, `edoCivil`, `telefono`, `celular`",
                sucuRSal + ",now()," + idUsuario + ",'" + nombre + "','" + apaterno + "','" + amaterno + "','" + f_nac + "'," + idEstadoNac + ",'" + curp + "','" + ocr + "','" + sexo + "'," + edoCivil + ",'" + tel + "','" + cel + "'");
        this.DB.Disconnect();
        flag = true;
        return flag;
    }

    public boolean actualizarDatosCliente(int idUsuario, int idPersona, int sucuRSal, String nombre, String apaterno, String amaterno, String f_nac, int idEstadoNac, String curp, String ocr, String sexo, int edoCivil, String tel, String cel) {
        boolean flag = false;
        this.DB.Connect();
        this.DB.Update("personas",
                "`nombre` = '" + nombre + "', `apaterno` = '" + apaterno + "', `amaterno` = '" + amaterno + "', `nacimiento` = '" + f_nac + "', `entidad` = " + idEstadoNac + ", `curp` = '" + curp + "', `ocr` = '" + ocr + "', `sexo` = '" + sexo + "', `edoCivil` = " + edoCivil + ", `telefono` = '" + tel + "', `celular` = '" + cel + "', usuario = " + idUsuario,
                "idPersona = " + idPersona + " AND sucuRSal = " + sucuRSal + " LIMIT 1");
        this.DB.Disconnect();
        flag = true;
        return flag;
    }

    public String[][] estados() {
        String[][] estados = null;
        try {
            this.DB.Connect();
            int count = this.countEstados();
            if (count > 0) {
                estados = new String[count][2];
                RS = this.DB.freeSelect("idEstado,estado", "estados", "");
                int i = 0;
                while (RS.next()) {
                    String array[] = {String.valueOf(RS.getInt(1)), RS.getString(2)};
                    estados[i] = array;
                    i++;
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarPersona_resource.estados() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", "resources.agregarPersona_resource.estados() : " + ex);
        }
        return estados;
    }

    public int countEstados() {
        int count = 0;
        try {
            RS = this.DB.Select("COUNT(idEstado)", "estados", "1");
            if (RS.next()) {
                count = RS.getInt(1);
            }
        } catch (Exception ex) {
            System.out.println("resources.agregarPersona_resource.countEstados() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", "resources.agregarPersona_resource.countEstados() : " + ex);
        }
        return count;
    }

    private int contarPersonas(int idSucuRSal, String dato) {
        int count = 0;
        try {
            if (!dato.isEmpty()) {
                RS = this.DB.Select("COUNT(idPersona)", "personas", "sucuRSal = " + idSucuRSal + " AND (nombre like '%" + dato + "%' OR apaterno like '%" + dato + "%' OR amaterno like '%" + dato + "%' OR curp like '%" + dato + "%' OR ocr like '%" + dato + "%' OR telefono like '%" + dato + "%' OR celular like '%" + dato + "%')");
            } else {
                RS = this.DB.Select("COUNT(idPersona)", "personas", "sucuRSal = " + idSucuRSal);
            }
            if (RS.next()) {
                count = RS.getInt(1);
                if (count > 50) {
                    count = 50;
                }
            }
        } catch (SQLException ex) {
            System.out.println("resources.agregarPersona_resource.contarPersonas() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", "resources.agregarPersona_resource.contarPersonas() : " + ex);
        }
        return count;
    }

    public int contarPersonas2(int idSucuRSal, int idPersona, String dato) throws SQLException {
        int count = 0;
        try {
            if (!dato.isEmpty()) {
                RS = this.DB.Select("COUNT(idPersona)", "personas", "(sucuRSal = " + idSucuRSal + " AND idPersona != " + idPersona + ") AND (nombre like '%" + dato + "%' OR apaterno like '%" + dato + "%' OR amaterno like '%" + dato + "%' OR curp like '%" + dato + "%' OR ocr like '%" + dato + "%' OR telefono like '%" + dato + "%' OR celular like '%" + dato + "%')");
            } else {
                RS = this.DB.Select("COUNT(idPersona)", "personas", "sucuRSal = " + idSucuRSal + " AND idPersona != " + idPersona);
            }
            if (RS.next()) {
                count = RS.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("resources.agregarPersona_resource.contarPersonas2() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", "resources.agregarPersona_resource.contarPersonas2() : " + ex);
        }
        return count;
    }

    public String[][] personas2(int idSucuRSal, int idPersona, String dato) {
        String[][] personas = null;
        int i = 0;
        try {
            this.DB.Connect();
            int count = this.contarPersonas2(idSucuRSal, idPersona, dato);
            if (count > 0) {
                personas = new String[count][9];
                if (!dato.isEmpty()) {
                    RS = this.DB.Select("idPersona, nombre, apaterno, amaterno, curp, ocr, telefono, celular, sexo", "personas", "(sucuRSal = " + idSucuRSal + " AND idPersona != " + idPersona + ") AND (nombre like '%" + dato + "%' OR apaterno like '%" + dato + "%' OR amaterno like '%" + dato + "%' OR curp like '%" + dato + "%' OR ocr like '%" + dato + "%' OR telefono like '%" + dato + "%' OR celular like '%" + dato + "%')");
                } else {
                    RS = this.DB.Select("idPersona, nombre, apaterno, amaterno, curp, ocr, telefono, celular, sexo", "personas", "sucuRSal = " + idSucuRSal + " AND idPersona != " + idPersona);
                }
                while (RS.next()) {
                    String array[] = {String.valueOf(RS.getInt(1)), RS.getString(2), RS.getString(3), RS.getString(4), RS.getString(5), RS.getString(6), RS.getString(7), RS.getString(8), RS.getString(9)};
                    personas[i] = array;
                    i++;
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarPersona_resource.personas2() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", "resources.agregarPersona_resource.personas2() : " + ex);
        }
        return personas;
    }

    public String[][] personas(int idSucuRSal, String dato) {
        String[][] personas = null;
        int i = 0;
        try {
            this.DB.Connect();
            int count = this.contarPersonas(idSucuRSal, dato);
            if (count > 0) {
                personas = new String[count][9];
                if (!dato.isEmpty()) {
                    RS = this.DB.Select("idPersona, nombre, apaterno, amaterno, curp, ocr, telefono, celular, sexo", "personas", "sucuRSal = " + idSucuRSal + " AND (nombre like '%" + dato + "%' OR apaterno like '%" + dato + "%' OR amaterno like '%" + dato + "%' OR curp like '%" + dato + "%' OR ocr like '%" + dato + "%' OR telefono like '%" + dato + "%' OR celular like '%" + dato + "%')");
                } else {
                    RS = this.DB.Select("idPersona, nombre, apaterno, amaterno, curp, ocr, telefono, celular, sexo", "personas", "sucuRSal = " + idSucuRSal + " ORDER BY idPersona DESC LIMIT 50");
                    // + " ORDER BY idPersona DESC LIMIT 15"
                }
                while (RS.next()) {
                    String array[] = {String.valueOf(RS.getInt(1)), RS.getString(2), RS.getString(3), RS.getString(4), RS.getString(5), RS.getString(6), RS.getString(7), RS.getString(8), RS.getString(9)};
                    personas[i] = array;
                    i++;
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println("resources.agregarPersona_resource.personas() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger("administracion", "resources.agregarPersona_resource.personas() : " + ex);
        }
        return personas;
    }

    public String[] persona(int idSucuRSal, int idPersona) {
        String[] persona = null;
        if (idPersona != 0 && idPersona > 0) {
            try {
                this.DB.Connect();
                RS = this.DB.Select("idPersona, nombre, apaterno, amaterno,nacimiento, entidad, curp, ocr, sexo, edoCivil, telefono, celular, domicilio, conyuge, aval, referencia", "personas", "sucuRSal = " + idSucuRSal + " AND idPersona = " + idPersona);
                if (RS.next()) {
                    persona = new String[16];
                    persona[0] = String.valueOf(RS.getInt(1));
                    persona[1] = RS.getString(2);
                    persona[2] = RS.getString(3);
                    persona[3] = RS.getString(4);
                    persona[4] = RS.getString(5);
                    persona[5] = RS.getString(6);
                    persona[6] = RS.getString(7);
                    persona[7] = RS.getString(8);
                    persona[8] = RS.getString(9);
                    persona[9] = RS.getString(10);
                    persona[10] = RS.getString(11);
                    persona[11] = RS.getString(12);
                    persona[12] = RS.getString(13);
                    persona[13] = RS.getString(14);
                    persona[14] = RS.getString(15);
                    persona[15] = RS.getString(16);
                }
                this.DB.Disconnect();
            } catch (SQLException ex) {
                System.out.println("resources.agregarPersona_resource.persona() : " + ex);
                this.ERROR_CONTROLLER.escribirErrorLogger("administracion", "resources.agregarPersona_resource.persona() : " + ex);
            }
        }
        return persona;
    }
}
