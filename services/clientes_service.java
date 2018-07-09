package services;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import objects.Adc;
import objects.Cliente;
import objects.Estudio;
import objects.Lista;
import objects.Ocupacion;
import objects.Persona;
import objects.Empleado;
import objects.Mes;
import objects.TableCreator;
import objects.Usuario;
import resources.clientes_resource;

/**
 *
 * @author JMalagon
 */
public class clientes_service {

    private final clientes_resource recurso;

    public clientes_service(String modulo) {
        this.recurso = new clientes_resource(modulo);
    }
    
    public DefaultComboBoxModel comboAdc(Usuario USUARIO) {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        String[][] array = this.recurso.getAdcFromSucursal(USUARIO.getIdSucursal());
        if (array != null) {
            dcbm.addElement(new Lista(0, "--- Seleccione ---"));
            for (String[] val : array) {
                if (Integer.parseInt(val[2]) < 10) {
                    val[2] = "0" + val[2];
                }
                dcbm.addElement(new Lista(Integer.parseInt(val[0]), "Z" + val[1] + "-" + val[2], "adc"));
            }
        } else {
            dcbm.addElement(new Lista(0, "Sin resultados"));
        }
        return dcbm;
    }

    public Adc crearAdc(Usuario USUARIO, Empleado EMPLEADO, Object agencia, Object vacante) {
        Adc adc;
        try {
//convertimos la agencia seleccionada a tipo int en caso de haber una, si no seguirá quedando como 0            
//convertimos el numero de vacante a tipo int si existe alguna                                        
            adc = new Adc();
            adc.setID_EMPLEADO(EMPLEADO.getID());
            adc.setSUCURSAL(USUARIO.getIdSucursal());
            adc.setAGENCIA(((Lista) agencia).getID());
            adc.setVACANTE(((Lista) vacante).getID());
        } catch (Exception ex) {
            adc = null;
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "() : " + ex);
        }
        return adc;
    }

    public Empleado crearEmpleado(Usuario USUARIO, Persona PERSONA_SELECCIONADA, int cargo, int estudios, int departamento, int salario, String entrada, String salida, String dias, String emergencia, String fecha, int efectivo) {
        Empleado emp = new Empleado();
        emp.setSUCURSAL(USUARIO.getIdSucursal());
        emp.setUSUARIO(USUARIO.getIdUsuario());
        emp.setID_PERSONA(PERSONA_SELECCIONADA.getIdPersona());
        emp.setCARGO(cargo);
        emp.setESTUDIOS(estudios);
        emp.setDEPARTAMENTO(departamento);
        emp.setSALARIO(salario);
        emp.setENTRADA(entrada);
        emp.setSALIDA(salida);
        emp.setDIAS_LABORALES(dias);
        emp.setCASO_EMERGENCIA(emergencia);
        emp.setFECHA_INCORPORACION(fecha);
        emp.setEFECTIVO(efectivo);
        emp.setPERSONA(PERSONA_SELECCIONADA);
        return emp;
    }

    public Cliente cliente(Persona persona) {
        Cliente c = null;
        if (persona != null) {
            String[] d = this.recurso.cliente(persona.getIdPersona());
            if (d != null) {
                c = new Cliente();
                c.setID(d[0]);
                c.setSUCURSAL(d[1]);
                c.setUSUARIO(d[2]);
                c.setF_REGISTRO(d[3]);
                c.setADC(d[4]);
                c.setID_PERSONA(d[5]);
                c.setINGRESOS(d[6]);
                c.setEGRESOS(d[7]);
                c.setDEPENDIENTES(d[8]);
                c.setOCUPACION(d[9]);
                c.setESTUDIOS(d[10]);
                c.setEMPRESA(d[11]);
                c.setDOMICILIO_EMPRESA(d[12]);
                c.setTEL_EMPRESA(d[13]);
                c.setHORA_ENTRADA(d[14]);
                c.setHORA_SALIDA(d[15]);
                c.setTIPO_VIVIENDA(d[16]);
                c.setPROPIETARIO(d[17]);
                c.setVIGENCIA(d[18]);
                c.setTIEMPO_RESIDENCIA(d[19]);
                c.setSCORE(d[20]);
                c.setSTATUS(d[21]);
                c.setACTIVIDAD(d[22]);
                c.setPERSONA(persona);
            }
        }
        return c;
    }
    
    public Cliente cliente(int idCliente) {
        Cliente c = null;
        if (idCliente > 0) {
            String[] d = this.recurso.cliente(idCliente);
            if (d != null) {
                c = new Cliente();
                c.setID(d[0]);
                c.setSUCURSAL(d[1]);
                c.setUSUARIO(d[2]);
                c.setF_REGISTRO(d[3]);
                c.setADC(d[4]);
                c.setID_PERSONA(d[5]);
                c.setINGRESOS(d[6]);
                c.setEGRESOS(d[7]);
                c.setDEPENDIENTES(d[8]);
                c.setOCUPACION(d[9]);
                c.setESTUDIOS(d[10]);
                c.setEMPRESA(d[11]);
                c.setDOMICILIO_EMPRESA(d[12]);
                c.setTEL_EMPRESA(d[13]);
                c.setHORA_ENTRADA(d[14]);
                c.setHORA_SALIDA(d[15]);
                c.setTIPO_VIVIENDA(d[16]);
                c.setPROPIETARIO(d[17]);
                c.setVIGENCIA(d[18]);
                c.setTIEMPO_RESIDENCIA(d[19]);
                c.setSCORE(d[20]);
                c.setSTATUS(d[21]);
                c.setACTIVIDAD(d[22]);                
            }
        }
        return c;
    }

    public Persona persona(int idSucursal, int idPersona) {
        Persona personas = null;
        String[] array = this.recurso.persona(idSucursal, idPersona);
        if (array != null) {
            personas = new Persona(Integer.parseInt(array[0]), array[1], array[2], array[3], array[4], Integer.parseInt(array[5]), array[6], array[7], array[8], Integer.parseInt(array[9]), array[10], array[11], Integer.parseInt(array[12]), Integer.parseInt(array[13]), Integer.parseInt(array[14]), Integer.parseInt(array[15]));
        }
        return personas;
    }

    public Empleado getEmpleado(int idPersona) {
        Empleado empleado = null;
        String[] array = this.recurso.datosEmpleado(idPersona);
        if (array != null) {
            empleado = new Empleado(
                    Integer.valueOf(array[0]),
                    Integer.valueOf(array[1]),
                    Integer.valueOf(array[2]),
                    array[3],
                    Integer.valueOf(array[4]),
                    Integer.valueOf(array[5]),
                    Integer.valueOf(array[6]),
                    Integer.valueOf(array[7]),
                    Integer.valueOf(array[8]),
                    array[9],
                    array[10],
                    array[11],
                    array[12],
                    array[13],
                    Integer.valueOf(array[14]),
                    array[15], this.persona(Integer.valueOf(array[1]), idPersona));
        }
        return empleado;
    }

    public JTable tablaEmpleados(JTable tabla, int idSucursal, String dato) {
        String titulos[] = {"ID", "Nombre", "Apellidos", "CURP", "Teléfono", "Celular", "Sexo", "Cargo", "Estudios", "Departamento", "Contacto", "Dias Laborales"};
        TableCreator tcr = new TableCreator();
        DefaultTableModel dtm = tcr.noEditableTableModel(titulos);
        String[][] array;
        if (dato.isEmpty()) {
            array = this.recurso.datosEmpleados(idSucursal, dato);
        } else {
            array = this.recurso.personas(idSucursal, dato);
        }
        if (array != null) {
            for (String[] var : array) {
                Object[] cli = new Object[var.length];
                cli[0] = var[0];
                cli[1] = var[1];
                cli[2] = var[2] + " " + var[3];
                cli[3] = var[4];
                cli[4] = var[5];
                cli[5] = var[6];
                cli[6] = var[7];
                cli[7] = var[8];
                cli[8] = var[9];
                cli[9] = var[10];
                cli[10] = var[11];
                cli[11] = var[12];
                dtm.addRow(cli);
            }
        }        
        tabla.setModel(dtm);
        tabla.setColumnModel(tcr.resizeTableEmpleados(tabla));
        return tabla;
    }

    public JTable tablaPersonas(JTable tabla, int idSucursal, String dato) {
        String titulos[] = {"Folio", "Nombre", "Apellidos", "CURP", "OCR", "Teléfono", "Celular", "Sexo"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[][] array;
        if (dato.isEmpty()) {
            array = this.recurso.personas(idSucursal, "");
        } else {
            array = this.recurso.personas(idSucursal, dato);
        }
        if (array != null) {
            for (String[] var : array) {
                Object[] cli = new Object[8];
                cli[0] = var[0];
                cli[1] = var[1];
                cli[2] = var[2] + " " + var[3];
                cli[3] = var[4];
                cli[4] = var[5];
                cli[5] = var[6];
                cli[6] = var[7];
                cli[7] = var[8];
                dtm.addRow(cli);
            }
        }
        TableCreator tcr = new TableCreator();
        tabla.setModel(dtm);
        tabla.setColumnModel(tcr.resizeTablePersonas(tabla));
        return tabla;
    }

    public DefaultComboBoxModel departamentos() {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        String[][] array = this.recurso.departamentos();
        if (array != null) {
            dcbm.addElement(new Lista(0, "-- Seleccione --"));
            for (String[] val : array) {
                Lista l = new Lista(Integer.valueOf(val[0]), val[1]);
                dcbm.addElement(l);
            }
        }
        return dcbm;
    }

    public DefaultComboBoxModel ocupaciones() {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        String[][] array = this.recurso.ocupaciones();
        if (array != null) {
            dcbm.addElement(new Ocupacion(0, "-- Seleccione --"));
            for (String[] val : array) {
                dcbm.addElement(new Ocupacion(Integer.valueOf(val[0]), val[1]));
            }
        }
        return dcbm;
    }

    public DefaultComboBoxModel estudios() {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        String[][] array = this.recurso.estudios();
        if (array != null) {
            dcbm.addElement(new Estudio(0, "-- Seleccione --"));
            for (String[] val : array) {
                dcbm.addElement(new Estudio(Integer.valueOf(val[0]), val[1]));
            }
        }
        return dcbm;
    }

    public DefaultComboBoxModel cargos() {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        String[][] array = this.recurso.cargos();
        if (array != null) {
            dcbm.addElement(new Lista(0, "-- Seleccione --"));
            for (String[] val : array) {
                dcbm.addElement(new Lista(Integer.valueOf(val[0]), val[1]));
            }
        }
        return dcbm;
    }

    public DefaultComboBoxModel agencias(int sucursal) {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        String[] array = this.recurso.agencias(sucursal);
        if (array != null) {
            dcbm.addElement(new Lista(0, "-- Seleccione --"));
            for (String array1 : array) {
                dcbm.addElement(new Lista(Integer.valueOf(array1), "Agencia " + array1));
            }
        }
        return dcbm;
    }

    public DefaultComboBoxModel vacantes(int sucursal, int agencia) {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        String[] array = this.recurso.vacantes(sucursal, agencia);
        if (array != null) {
            dcbm.addElement(new Lista(0, "-- Seleccione --"));
            for (String array1 : array) {
                dcbm.addElement(new Lista(Integer.valueOf(array1), "Vacante " + array1));
            }
        }
        return dcbm;
    }

    public String actualizarADC(Adc ADC, Adc nuevosDatos) {
        String mensaje;
        if (ADC != null) {
            boolean b = this.recurso.actualizarADC(ADC.getID(), nuevosDatos.getSUCURSAL(), nuevosDatos.getID_EMPLEADO(),
                    nuevosDatos.getAGENCIA(), nuevosDatos.getVACANTE());
            if (b) {
                mensaje = "Datos de ADC actualizados correctamente";
            } else {
                mensaje = "Algo falló al actualizar los datos del ADC";
            }
        } else {
            mensaje = "No es posible actualizar la información porque\nalguno de los datos se encuentra vacío o es incorrecto.";
        }
        return mensaje;
    }

    public String crearADC(Adc adc) {//int sucursal, int empleado, int agencia, int vacante) {
        String mensaje;
        //if (sucursal > 0 && empleado > 0 && agencia > 0 && vacante > 0) {
        if (adc != null) {
            boolean b = this.recurso.crearADC(adc.getSUCURSAL(), adc.getID_EMPLEADO(), adc.getAGENCIA(), adc.getVACANTE());
            if (b) {
                mensaje = "Datos de ADC insertados correctamente";
            } else {
                mensaje = "Algo falló al insertar los datos de ADC";
            }
        } else {
////            System.out.println(sucursal + "-" + empleado + "-" + agencia + "-" + vacante);
            mensaje = "Alguno de los datos se encuentra vacío o es incorrecto.";
        }
        return mensaje;
    }

    public int guardarDatosEmpleado(Empleado empleado, Adc adc) {
        if (empleado != null) {
            //System.out.println(empleado.toString());
            int guion = empleado.getDIAS_LABORALES().indexOf("-");
            if (guion >= 1) {
                empleado.setDIAS_LABORALES(empleado.getDIAS_LABORALES().substring(0, empleado.getDIAS_LABORALES().length() - 1));
            }
            return this.recurso.guardarDatosStaff(empleado.getPERSONA().getIdPersona(), empleado.getCARGO(), empleado.getESTUDIOS(), empleado.getDEPARTAMENTO(),
                    empleado.getSUCURSAL(), empleado.getSALARIO(), empleado.getENTRADA(), empleado.getSALIDA(),
                    empleado.getDIAS_LABORALES(), empleado.getCASO_EMERGENCIA(), empleado.getFECHA_INCORPORACION(), empleado.getEFECTIVO(),
                    this.codigoStaff(empleado, adc), empleado.getUSUARIO());

        } else {
            return 0;
        }
    }

    public boolean actualizarDatosEmpleado(Empleado nuevo, Adc adc) {
        if (nuevo != null) {
//            System.out.println(nuevo.toString());
            int guion = nuevo.getDIAS_LABORALES().indexOf("-");
            if (guion >= 1) {
                nuevo.setDIAS_LABORALES(nuevo.getDIAS_LABORALES().substring(0, nuevo.getDIAS_LABORALES().length() - 1));
            }
            this.recurso.quitarADC(nuevo.getSUCURSAL(), nuevo.getID());
            return this.recurso.actualizarDatosStaff(nuevo.getID(), nuevo.getID_PERSONA(), nuevo.getCARGO(), nuevo.getESTUDIOS(), nuevo.getDEPARTAMENTO(),
                    nuevo.getSUCURSAL(), nuevo.getSALARIO(), nuevo.getENTRADA(), nuevo.getSALIDA(), nuevo.getDIAS_LABORALES(),
                    nuevo.getCASO_EMERGENCIA(), nuevo.getFECHA_INCORPORACION(), nuevo.getEFECTIVO(),
                    this.codigoStaff(nuevo, adc), nuevo.getUSUARIO());
        } else {
            return false;
        }
    }

    private String codigoStaff(Empleado emp, Adc adc) {
        String codigo = "";
        if (emp != null) {
            if (emp.getSUCURSAL() < 10) {
                codigo = "0" + emp.getSUCURSAL();
            } else {
                codigo += emp.getSUCURSAL();
            }
            if (emp.getSUCURSAL() == 1 && emp.getCARGO() != 5) {//el 5 es ADC
                codigo += "C-";
            } else if ((emp.getSUCURSAL() == 1 && emp.getCARGO() == 5) || (emp.getSUCURSAL() > 1)) {//el 5 es ADC
                codigo += "S-";
            }

            int tipoCargo = Integer.valueOf(this.recurso.cargo(emp.getCARGO())[2]);
            int[] idCargos = this.recurso.idCargos(tipoCargo);
            String condicion = "idStaff != " + emp.getID() + " AND (";
            for (int i = 0; i < idCargos.length; i++) {
                condicion += "cargo = " + idCargos[i];
                if (i + 1 < idCargos.length) {
                    condicion += " OR ";
                }
            }
            condicion += ")";
            int countEmpleados = this.recurso.contarEmpleados(emp.getSUCURSAL(), condicion);//contar empleados del mismo tipo
            if (tipoCargo == 1 && emp.getCARGO() == 5 && adc != null) {
                codigo += "Z" + adc.getAGENCIA() + "-";
                if (adc.getVACANTE() < 10) {
                    codigo += "0";
                }
                codigo += adc.getVACANTE();

            } else {
                if (tipoCargo == 1) {
                    codigo += "O";
                } else if (tipoCargo == 0) {
                    codigo += "A";
                }

                if (countEmpleados < 10) {
                    codigo += "00" + (countEmpleados + 1);
                } else if (countEmpleados < 100 && countEmpleados > 9) {
                    codigo += "0" + (countEmpleados + 1);
                }
                //01C-O01300620
            }
            codigo += "-" + emp.getFECHA_INCORPORACION().substring(8, 10) + emp.getFECHA_INCORPORACION().substring(5, 7) + emp.getFECHA_INCORPORACION().substring(2, 4);
        }
        return codigo;
    }

    public int guardarDatosCliente(Cliente c) {
        String[] datos = this.recurso.cliente(c.getPersona().getIdPersona());
        if (datos == null) {
            return this.recurso.guardarDatosCliente(c.getSUCURSAL(), c.getUSUARIO(), c.getADC(), c.getID_PERSONA(), c.getINGRESOS(), c.getEGRESOS(),
                    c.getDEPENDIENTES(), c.getOCUPACION(), c.getESTUDIOS(), c.getEMPRESA(), c.getDOMICILIO_EMPRESA(), c.getTEL_EMPRESA(),
                    c.getHORA_ENTRADA(), c.getHORA_SALIDA(),
                    c.getTIPO_VIVIENDA(), c.getPROPIETARIO(),
                    c.getVIGENCIA(), c.getTIEMPO_RESIDENCIA());
        } else {
            return -1;//La persona ya es un cliente
        }
    }

    public int actualizarDatos(Cliente c) {
        String[] datos = this.recurso.cliente(c.getPersona().getIdPersona());
        if (datos != null && Integer.parseInt(datos[0]) == c.getID()) {
            return this.recurso.actualizarDatosCliente(c.getID(), c.getSUCURSAL(), c.getUSUARIO(), c.getADC(),
                    c.getID_PERSONA(), c.getINGRESOS(), c.getEGRESOS(), c.getDEPENDIENTES(),
                    c.getOCUPACION(), c.getESTUDIOS(), c.getEMPRESA(),
                    c.getDOMICILIO_EMPRESA(), c.getTEL_EMPRESA(), c.getHORA_ENTRADA(), c.getHORA_SALIDA(),
                    c.getTIPO_VIVIENDA(), c.getPROPIETARIO(), c.getVIGENCIA(), c.getTIEMPO_RESIDENCIA());
        } else {
            return -1;//La persona no es cliente
        }
    }

    public boolean desactivarCliente(Cliente cliente, int actividad) {
        if (cliente != null) {
            return this.recurso.actividadCliente(cliente.getID(), actividad);
        } else {
            return false;
        }
    }

    public Adc adc(int idSucursal, int id_empleado) {
        Adc adc = null;
        if (idSucursal > 0 && id_empleado > 0) {
            int[] val = this.recurso.datosAdc(idSucursal, id_empleado);
            if (val != null) {
                adc = new Adc(val[0], val[1], val[2], val[3], val[4], val[5]);
            }
        }
        return adc;
    }

    public Mes[] meses() {
        Mes[] meses = new Mes[13];
        meses[0] = new Mes("00", "--Seleccione--");
        meses[1] = new Mes("01", "Enero");
        meses[2] = new Mes("02", "Febrero");
        meses[3] = new Mes("03", "Marzo");
        meses[4] = new Mes("04", "Abril");
        meses[5] = new Mes("05", "Mayo");
        meses[6] = new Mes("06", "Junio");
        meses[7] = new Mes("07", "Julio");
        meses[8] = new Mes("08", "Agosto");
        meses[9] = new Mes("09", "Septiembre");
        meses[10] = new Mes("10", "Octubre");
        meses[11] = new Mes("11", "Noviembre");
        meses[12] = new Mes("12", "Diciembre");
        return meses;
    }

}
