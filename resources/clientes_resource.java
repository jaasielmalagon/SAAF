package resources;

import database.conection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import objects.ErrorController;

/**
 *
 * @author JMalagon
 */
public class clientes_resource {

    private final conection DB;
    private ResultSet RS;
    ErrorController ERROR_CONTROLLER;
    private final String modulo;

    public clientes_resource(String modulo) {
        this.DB = new conection();
        this.ERROR_CONTROLLER = new ErrorController();
        this.modulo = modulo;
    }

    public boolean actualizarDatosStaff(int idStaff, int idPersona, int cargo, int estudios, int departamento, int sucursal, int salario, String entrada, String salida,
            String dias, String llamara, String fecha, int efectivo, String codigo, int usuario) {
        boolean flag = false;
        try {
            this.DB.Connect();
            flag = this.DB.Update("personas_empleados",
                    "sucursal=" + sucursal + ",usuario=" + usuario + ",cargo=" + cargo + ",estudios=" + estudios
                    + ",departamento=" + departamento + ",sucursal=" + sucursal + ",salario=" + salario
                    + ",entrada='" + entrada + "',salida='" + salida + "',dias='" + dias + "',llamara='" + llamara + "',fecha_incorp='" + fecha
                    + "',efectivo=" + efectivo
                    + ",codigo='" + codigo + "'",
                    "idStaff=" + idStaff + " AND idPersona=" + idPersona);
            this.DB.Disconnect();
        } catch (Exception ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return flag;
    }

    public int guardarDatosStaff(int idPersona, int cargo, int estudios, int departamento, int sucursal, int salario, String entrada, String salida,
            String dias, String llamara, String fecha, int efectivo, String codigo, int usuario) {
        int flag = 0;
        try {
//            this.DB.Connect();
            flag = this.DB.InsertId("personas_empleados",
                    "idPersona,cargo,estudios,departamento,sucursal,salario,entrada,salida,dias,llamara,fecha_incorp,efectivo,codigo,usuario,registro",
                    idPersona + "," + cargo + "," + estudios + "," + departamento + "," + sucursal + "," + salario + ",'" + entrada + "','" + salida + "','" + dias + "','" + llamara + "','" + fecha + "'," + efectivo + ",'" + codigo + "'," + usuario + ",now()");
//            this.DB.Disconnect();
        } catch (Exception ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return flag;
    }

    public String[] datosEmpleado(int idPersona) {
        String[] array = null;
        try {
            this.DB.Connect();
            RS = this.DB.Select("*", "personas_empleados", "idPersona = " + idPersona + " LIMIT 1");
            if (RS.next()) {
                array = new String[RS.getFetchSize()];
                for (int i = 0; i < RS.getFetchSize(); i++) {
                    array[i] = RS.getString(i + 1);
                }
//                array[0] = RS.getString(1);
//                array[1] = RS.getString(2);
//                array[2] = RS.getString(3);
//                array[3] = RS.getString(4);
//                array[4] = RS.getString(5);
//                array[5] = RS.getString(6);
//                array[6] = RS.getString(7);
//                array[7] = RS.getString(8);
//                array[8] = RS.getString(9);
//                array[9] = RS.getString(10);
//                array[10] = RS.getString(11);
//                array[11] = RS.getString(12);
//                array[12] = RS.getString(13);
//                array[13] = RS.getString(14);
//                array[14] = RS.getString(15);
//                array[15] = RS.getString(16);
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public String[][] datosEmpleados(int idSucursal, String dato) {
        String[][] personas = null;
        int i = 0;
        try {
            this.DB.Connect();
            int count = this.contarEmpleados(idSucursal, dato);
            int columnas = 13;
            if (count > 0) {
                if (!dato.isEmpty()) {
                    //aquí va la búsqueda filtrada                    
                } else {
                    //busqueda de TODOS LOS RESULTADOS
                    if (count > 50) {
                        count = 50;
                    }
                    personas = new String[count][columnas];
                    RS = this.DB.fullSelect("SELECT personas.idPersona, personas.nombre, personas.apaterno, personas.amaterno, personas.curp, personas.telefono, personas.celular, personas.sexo,\n"
                            + "tipo_cargo.cargo, tipo_estudios.estudios, tipo_usuarios.tipo as depto, personas_empleados.llamara as contacto_emergencia, personas_empleados.dias\n"
                            + "FROM personas_empleados\n"
                            + "LEFT JOIN personas ON personas_empleados.idPersona = personas.idPersona\n"
                            + "RIGHT JOIN tipo_cargo ON personas_empleados.cargo = tipo_cargo.idCargo\n"
                            + "RIGHT JOIN tipo_estudios ON personas_empleados.estudios = tipo_estudios.idTipoEstudios\n"
                            + "RIGHT JOIN tipo_usuarios ON personas_empleados.departamento = tipo_usuarios.idTipoUsuario\n"
                            + "WHERE personas_empleados.sucursal = " + idSucursal);
                }
                while (RS.next()) {
                    String array[] = {RS.getString(1), RS.getString(2), RS.getString(3), RS.getString(4), RS.getString(5), RS.getString(6), RS.getString(7), RS.getString(8), RS.getString(9), RS.getString(10), RS.getString(11), RS.getString(12), RS.getString(13)};
                    personas[i] = array;
                    i++;
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return personas;
    }

    private int contarEmpleados(int idSucursal, String dato) {
        int count = 0;
        try {
            if (!dato.isEmpty()) {
                //RS = this.DB.Select("COUNT(idStaff)", "personas_empleados", "sucursal = " + idSucursal + " AND (nombre like '%" + dato + "%' OR apaterno like '%" + dato + "%' OR amaterno like '%" + dato + "%' OR curp like '%" + dato + "%' OR ocr like '%" + dato + "%' OR telefono like '%" + dato + "%' OR celular like '%" + dato + "%')");
            } else {
                RS = this.DB.Select("COUNT(idStaff)", "personas_empleados", "sucursal = " + idSucursal);
            }
            if (RS.next()) {
                count = RS.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return count;
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

    public String[] persona(int idSucursal, int idPersona) {
        String[] persona = null;
        if (idPersona != 0 && idPersona > 0) {
            try {
                this.DB.Connect();
                RS = this.DB.Select("idPersona, nombre, apaterno, amaterno,nacimiento, entidad, curp, ocr, sexo, edoCivil, telefono, celular, domicilio, conyuge, aval, referencia", "personas", "sucursal = " + idSucursal + " AND idPersona = " + idPersona);
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
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
                this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            }
        }
        return persona;
    }

    public String[][] personas(int idSucursal, String dato) {
        String[][] personas = null;
        int i = 0;
        try {
            this.DB.Connect();
            int count = this.contarPersonas(idSucursal, dato);
            if (count > 0) {
                if (!dato.isEmpty()) {
                    personas = new String[count][9];
                    RS = this.DB.Select("idPersona, nombre, apaterno, amaterno, curp, ocr, telefono, celular, sexo", "personas", "sucursal = " + idSucursal + " AND (nombre like '%" + dato + "%' OR apaterno like '%" + dato + "%' OR amaterno like '%" + dato + "%' OR curp like '%" + dato + "%' OR ocr like '%" + dato + "%' OR telefono like '%" + dato + "%' OR celular like '%" + dato + "%')");
                } else {
                    if (count > 50) {
                        personas = new String[50][9];
                    } else {
                        personas = new String[count][9];
                    }
                    RS = this.DB.Select("idPersona, nombre, apaterno, amaterno, curp, ocr, telefono, celular, sexo", "personas", "sucursal = " + idSucursal + " ORDER BY idPersona DESC LIMIT 50");
                }
                while (RS.next()) {
                    String array[] = {String.valueOf(RS.getInt(1)), RS.getString(2), RS.getString(3), RS.getString(4), RS.getString(5), RS.getString(6), RS.getString(7), RS.getString(8), RS.getString(9)};
                    personas[i] = array;
                    i++;
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return personas;
    }

    private int contarPersonas(int idSucursal, String dato) {
        int count = 0;
        try {
            if (!dato.isEmpty()) {
                RS = this.DB.Select("COUNT(idPersona)", "personas", "sucursal = " + idSucursal + " AND (nombre like '%" + dato + "%' OR apaterno like '%" + dato + "%' OR amaterno like '%" + dato + "%' OR curp like '%" + dato + "%' OR ocr like '%" + dato + "%' OR telefono like '%" + dato + "%' OR celular like '%" + dato + "%')");
            } else {
                RS = this.DB.Select("COUNT(idPersona)", "personas", "sucursal = " + idSucursal);
            }
            if (RS.next()) {
                count = RS.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return count;
    }

    public String[][] vacantes(int sucursal, int agencia) {
        String[][] array = null;
        try {
            this.DB.Connect();
            RS = this.DB.freeSelect("COUNT(*)", "personas_empleados_adc", "WHERE sucursal = " + sucursal + " AND agencia = " + agencia + " AND idStaff = 0");
            int count = 0;
            if (RS.next()) {
                count = RS.getInt(1);
            }
            if (count > 0) {
                array = new String[count][2];
                int i = 0;
                RS = this.DB.freeSelect("idAdc,vacante", "personas_empleados_adc", "WHERE sucursal = " + sucursal + " AND agencia = " + agencia + " AND idStaff = 0");
                while (RS.next()) {
                    array[i][0] = RS.getString(1);
                    array[i][1] = RS.getString(2);
                    i++;
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public String[] agencias(int sucursal) {
        String[] array = null;
        try {
            this.DB.Connect();
            RS = this.DB.freeSelect("COUNT(DISTINCT(agencia))", "personas_empleados_adc", "WHERE sucursal = " + sucursal);
            int count = 0;
            if (RS.next()) {
                count = RS.getInt(1);
            }
            if (count > 0) {
                array = new String[count];
                int i = 0;
                RS = this.DB.freeSelect("DISTINCT(agencia)", "personas_empleados_adc", "WHERE sucursal = " + sucursal);
                while (RS.next()) {
                    array[i] = RS.getString(1);
                    i++;
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public String[][] cargos() {
        String[][] array = null;
        try {
            this.DB.Connect();
            RS = this.DB.freeSelect("COUNT(idCargo)", "tipo_cargo", "");
            int count = 0;
            if (RS.next()) {
                count = RS.getInt(1);
            }
            if (count > 0) {
                array = new String[count][2];
                int i = 0;
                RS = this.DB.freeSelect("*", "tipo_cargo", "ORDER BY cargo ASC");
                while (RS.next()) {
                    array[i][0] = RS.getString(1);
                    array[i][1] = RS.getString(2);
                    i++;
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public String[] cargo(int idCargo) {
        String[] array = null;
        try {
            this.DB.Connect();
            array = new String[3];
            RS = this.DB.Select("*", "tipo_cargo", "idCargo=" + idCargo + " LIMIT 1");
            if (RS.next()) {
                array[0] = RS.getString(1);
                array[1] = RS.getString(2);
                array[2] = RS.getString(3);
                System.out.println(Arrays.toString(array));
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public String[][] estudios() {
        String[][] array = null;
        try {
            this.DB.Connect();
            RS = this.DB.freeSelect("COUNT(idTipoEstudios)", "tipo_estudios", "");
            int count = 0;
            if (RS.next()) {
                count = RS.getInt(1);
            }
            if (count > 0) {
                array = new String[count][2];
                int i = 0;
                RS = this.DB.freeSelect("*", "tipo_estudios", "ORDER BY estudios ASC");
                while (RS.next()) {
                    array[i][0] = RS.getString(1);
                    array[i][1] = RS.getString(2);
                    i++;
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public String[][] departamentos() {
        String[][] array = null;
        try {
            this.DB.Connect();
            RS = this.DB.freeSelect("COUNT(idTipoUsuario)", "tipo_usuarios", "");
            int count = 0;
            if (RS.next()) {
                count = RS.getInt(1);
            }
            if (count > 0) {
                array = new String[count][2];
                int i = 0;
                RS = this.DB.freeSelect("*", "tipo_usuarios", "ORDER BY tipo ASC");
                while (RS.next()) {
                    array[i][0] = RS.getString(1);
                    array[i][1] = RS.getString(2);
                    i++;
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public String[][] ocupaciones() {
        String[][] array = null;
        try {
            this.DB.Connect();
            RS = this.DB.freeSelect("COUNT(idTipo)", "tipo_ocupaciones", "");
            int count = 0;
            if (RS.next()) {
                count = RS.getInt(1);
            }
            if (count > 0) {
                array = new String[count][2];
                int i = 0;
                RS = this.DB.freeSelect("*", "tipo_ocupaciones", "ORDER BY ocupacion ASC");
                while (RS.next()) {
                    array[i][0] = RS.getString(1);
                    array[i][1] = RS.getString(2);
                    i++;
                }
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return array;
    }

    public int guardarDatosCliente(int sucursal, int usuario, String adc, int id_persona, double ingresos, double egresos, int dependientes,
            int ocupacion, int estudios, String empresa, String domicilio_empresa, String tel_empresa, String hora_entrada, String hora_salida,
            int tipo_vivienda, String propietario_inmueble, String vigencia_contrato, int tiempo_residencia) {
        int res = this.DB.InsertId("personas_clientes",
                "`sucursal`, `usuario`, `registro`, `adc`, `idPersona`, `ingresos`, `egresos`, `dependientes`, `ocupacion`,"
                + " `estudios`, `empresa`, `domicilio_empresa`, `tel_empresa`, `horario_entrada`, `horario_salida`,`tipo_vivienda`, `propietario`, `vigencia_contrato`, `tiempo_residencia`",
                sucursal + "," + usuario + ",now(),'" + adc + "'," + id_persona + "," + ingresos + "," + egresos + "," + dependientes + "," + ocupacion + ","
                + estudios + ",'" + empresa + "','" + domicilio_empresa + "','" + tel_empresa + "','" + hora_entrada + "','" + hora_salida + "'," + tipo_vivienda + ",'" + propietario_inmueble + "','" + vigencia_contrato + "'," + tiempo_residencia);
        return res;
    }

    public int actualizarDatosCliente(int cliente, int sucursal, int usuario, String adc, int id_persona, double ingresos, double egresos, int dependientes, int ocupacion, int estudios, String empresa, String domicilio_empresa, String tel_empresa, String hora_entrada, String hora_salida,
            int tipoVivienda, String propietario, String vigencia, int tiempoResidencia) {
        this.DB.Connect();
        boolean update = this.DB.Update("personas_clientes",
                "sucursal=" + sucursal + ",usuario=" + usuario + ",adc='" + adc + "',ingresos=" + ingresos + ",egresos=" + egresos + ",dependientes=" + dependientes + ",ocupacion=" + ocupacion
                + ",estudios=" + estudios + ",empresa='" + empresa + "',domicilio_empresa='" + domicilio_empresa + "',tel_empresa='" + tel_empresa + "',horario_entrada='" + hora_entrada + "',horario_salida='" + hora_salida + "',"
                + "tipo_vivienda = " + tipoVivienda + ",propietario='" + propietario + "',vigencia_contrato='" + vigencia + "',tiempo_residencia=" + tiempoResidencia,
                "idCliente=" + cliente + " AND idPersona=" + id_persona);
        this.DB.Disconnect();
        if (update == true) {
            return 1;
        } else {
            return 0;
        }
    }

    public boolean actividadCliente(int cliente, int actividad) {
        boolean f;
        this.DB.Connect();
        f = this.DB.Update("personas_clientes",
                "actividad = " + actividad,
                "idCliente=" + cliente + " LIMIT 1");
        this.DB.Disconnect();
        return f;
    }

    public boolean crearADC(int sucursal, int staff, int agencia, int vacante) {
        boolean flag;
        try {
            this.DB.Connect();
            flag = this.DB.Update("personas_empleados_adc", "idStaff = " + staff, "sucursal = " + sucursal + " AND agencia = " + agencia + " AND vacante = " + vacante);
            this.DB.Disconnect();
        } catch (Exception ex) {
            flag = false;
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return flag;
    }

    public boolean actualizarADC(int idAdc, int sucursal, int staff, int agencia, int vacante) {
        boolean flag;
        try {
            this.DB.Connect();
            flag = this.DB.Update("personas_empleados_adc", "idStaff = 0", "idAdc=" + idAdc);
            if (flag) {
                flag = this.DB.Update("personas_empleados_adc", "idStaff = " + staff, "sucursal = " + sucursal + " AND agencia = " + agencia + " AND vacante = " + vacante);
            } else {
                flag = false;
            }
            this.DB.Disconnect();
        } catch (Exception ex) {
            flag = false;
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return flag;
    }

    public int[] datosAdc(int idSucursal, int id_staff) {
        int[] datos = null;
        try {
            this.DB.Connect();
            RS = this.DB.Select("*", "personas_empleados_adc", "sucursal = " + idSucursal + " AND idStaff = " + id_staff + " LIMIT 1");
            if (RS.next()) {
                datos = new int[6];
                datos[0] = RS.getInt(1);
                datos[1] = RS.getInt(2);
                datos[2] = RS.getInt(3);
                datos[3] = RS.getInt(4);
                datos[4] = RS.getInt(5);
                datos[5] = RS.getInt(6);
            }
            this.DB.Disconnect();
        } catch (SQLException ex) {
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
            this.ERROR_CONTROLLER.escribirErrorLogger(this.modulo, Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return datos;
    }

}
