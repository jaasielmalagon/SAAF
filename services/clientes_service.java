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
import objects.TableCreator;
import resources.clientes_resource;

/**
 *
 * @author JMalagon
 */
public class clientes_service {

    private final clientes_resource recurso;

    public clientes_service() {
        this.recurso = new clientes_resource();
    }

    public Cliente cliente(Persona persona) {
        String[] datos = this.recurso.cliente(persona.getIdPersona());
        Cliente cliente = null;
//        System.out.println(Arrays.toString(datos));
        if (datos != null) {
            cliente = new Cliente(
                    Integer.parseInt(datos[0]), Integer.parseInt(datos[1]), Integer.parseInt(datos[2]),
                    datos[3], datos[4], Integer.parseInt(datos[5]),
                    Double.parseDouble(datos[6]), Double.parseDouble(datos[7]), Integer.parseInt(datos[8]),
                    Integer.parseInt(datos[9]), Integer.parseInt(datos[10]), datos[11],
                    datos[12], datos[13], datos[14],
                    datos[15], Integer.parseInt(datos[16]), Integer.parseInt(datos[17]),
                    Integer.parseInt(datos[18]), persona);
        }
        return cliente;
    }

    public Persona persona(int idSucursal, int idPersona) {
        Persona personas = null;
        String[] array = this.recurso.persona(idSucursal, idPersona);
        if (array != null) {
            personas = new Persona(Integer.parseInt(array[0]), array[1], array[2], array[3], array[4], Integer.parseInt(array[5]), array[6], array[7], array[8], Integer.parseInt(array[9]), array[10], array[11], Integer.parseInt(array[12]), Integer.parseInt(array[13]), Integer.parseInt(array[14]), Integer.parseInt(array[15]));
        }
        return personas;
    }

    public Empleado staff(int idPersona) {
        Empleado staff = null;
        String[] array = this.recurso.staff(idPersona);
        if (array != null) {
            //staff = new Empleado(Integer.valueOf(array[0]), Integer.valueOf(array[1]), Integer.valueOf(array[2]), Integer.valueOf(array[3]), Integer.valueOf(array[4]),
            //        Integer.valueOf(array[5]), Integer.valueOf(array[6]), array[7], array[8], array[9], array[10], array[11], Integer.parseInt(array[12]), array[13], this.persona(Integer.valueOf(array[5]), Integer.valueOf(array[1])));
            staff = new Empleado(Integer.valueOf(array[0]),
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
            array[15],this.persona(Integer.valueOf(array[1]), idPersona));
        }
        return staff;
    }

    public JTable tablaPersonas(JTable tabla, int idSucursal, String dato) {
        String titulos[] = {"Folio", "Nombre", "Apellidos", "CURP", "OCR", "Teléfono", "Celular", "Sexo"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos);
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
        String[][] array = this.recurso.vacantes(sucursal, agencia);
        if (array != null) {
            dcbm.addElement(new Lista(0, "-- Seleccione --"));
            for (String[] val : array) {
                dcbm.addElement(new Lista(Integer.valueOf(val[0]), "Vacante " +val[1]));
            }
        }
        return dcbm;
    }

    public int guardarDatosStaff(Empleado staff) {
        if (staff != null) {
            //System.out.println(staff.toString());
            int guion = staff.getDIAS_LABORALES().indexOf("-");
            if (guion >= 1) {
                staff.setDIAS_LABORALES(staff.getDIAS_LABORALES().substring(0, staff.getDIAS_LABORALES().length() - 1));
            }
            return this.recurso.guardarDatosStaff(staff.getPERSONA().getIdPersona(), staff.getCARGO(), staff.getESTUDIOS(), staff.getDEPARTAMENTO(),
                    staff.getSUCURSAL(), staff.getSALARIO(), staff.getENTRADA(), staff.getSALIDA(),
                    staff.getDIAS_LABORALES(), staff.getCASO_EMERGENCIA(), staff.getFECHA_INCORPORACION(), staff.getEFECTIVO(),
                    this.codigoStaff(staff),staff.getUSUARIO());

        } else {
            return 0;
        }
    }
    
    public boolean actualizarDatosStaff(Empleado nuevo) {
        if (nuevo != null ){
//            System.out.println(nuevo.toString());
            int guion = nuevo.getDIAS_LABORALES().indexOf("-");
            if (guion >= 1) {
                nuevo.setDIAS_LABORALES(nuevo.getDIAS_LABORALES().substring(0, nuevo.getDIAS_LABORALES().length() - 1));
            }
            return this.recurso.actualizarDatosStaff(nuevo.getID_STAFF(), nuevo.getID_PERSONA(), nuevo.getCARGO(), nuevo.getESTUDIOS(), nuevo.getDEPARTAMENTO(),
                    nuevo.getSUCURSAL(), nuevo.getSALARIO(), nuevo.getENTRADA(), nuevo.getSALIDA(), nuevo.getDIAS_LABORALES(),
                    nuevo.getCASO_EMERGENCIA(), nuevo.getFECHA_INCORPORACION(), nuevo.getEFECTIVO(), this.codigoStaff(nuevo), nuevo.getUSUARIO());

        } else {
            return false;
        }
    }

    private String codigoStaff(Empleado s) {
        String codigo = "";
        if (s != null) {
            if (s.getSUCURSAL() < 10) {
                codigo = "0" + s.getSUCURSAL();
            } else {
                codigo += s.getSUCURSAL();
            }
            if (s.getSUCURSAL() == 1 && s.getCARGO() != 5) {//el 5 es ADC
                codigo += "C-";
            } else if ((s.getSUCURSAL() == 1 && s.getCARGO() == 5) || (s.getSUCURSAL() > 1)) {//el 5 es ADC
                codigo += "S-";
            }
            int tipoCargo = Integer.valueOf(this.recurso.cargo(s.getCARGO())[2]);
            if (tipoCargo == 1 && s.getCARGO() == 5) {
                codigo += "Z";
            } else if (tipoCargo == 1 && s.getCARGO() != 5) {
                codigo += "O";
            } else if (tipoCargo == 0) {
                codigo += "A";
            }
        }
        return codigo;
    }         

    public int guardarDatos(Cliente c) {
        String[] datos = this.recurso.cliente(c.getPersona().getIdPersona());
        if (datos == null) {
            return this.recurso.guardarDatosCliente(c.getSUCURSAL(), c.getUSUARIO(), c.getADC(), c.getID_PERSONA(), c.getINGRESOS(), c.getEGRESOS(), c.getDEPENDIENTES(), c.getOCUPACION(), c.getESTUDIOS(), c.getEMPRESA(), c.getDOMICILIO_EMPRESA(), c.getTEL_EMPRESA(), c.getHORA_ENTRADA(), c.getHORA_SALIDA());
        } else {
            return -1;//La persona ya es un cliente
        }
    }

    public int actualizarDatos(Cliente c) {
        String[] datos = this.recurso.cliente(c.getPersona().getIdPersona());
        if (datos != null && Integer.parseInt(datos[0]) == c.getIdCliente()) {
            return this.recurso.actualizarDatosCliente(c.getIdCliente(), c.getSUCURSAL(), c.getUSUARIO(), c.getADC(),
                    c.getID_PERSONA(), c.getINGRESOS(), c.getEGRESOS(), c.getDEPENDIENTES(),
                    c.getOCUPACION(), c.getESTUDIOS(), c.getEMPRESA(),
                    c.getDOMICILIO_EMPRESA(), c.getTEL_EMPRESA(), c.getHORA_ENTRADA(), c.getHORA_SALIDA());
        } else {
            return -1;//La persona no es cliente
        }
    }
    
    public String actualizarADC(Adc ADC, int sucursal, int staff, int agencia, int vacante) {
        String mensaje;
        if (ADC != null) {
            boolean b = this.recurso.actualizarADC(ADC.getID(),sucursal, staff, agencia, vacante);
            if (b) {
                mensaje = "Datos de ADC actualizados correctamente";
            }else{
                mensaje = "Algo falló al actualizar los datos del ADC";
            }
        }else{            
            mensaje = "No es posible actualizar la información porque\nalguno de los datos se encuentra vacío o es incorrecto.";
        }
        return mensaje;
    }

    public String crearADC(int sucursal, int staff, int agencia, int vacante) {
        String mensaje;
        if (sucursal > 0 && staff > 0 && agencia > 0 && vacante > 0) {
            boolean b = this.recurso.crearADC(sucursal,staff,agencia,vacante);
            if (b) {
                mensaje = "Datos de ADC insertados correctamente";
            }else{
                mensaje = "Algo falló al insertar los datos de ADC";
            }
        }else{
            System.out.println(sucursal +"-"+ staff+"-"+agencia+"-"+vacante);
            mensaje = "Alguno de los datos se encuentra vacío o es incorrecto.";
        }
        return mensaje;
    }

    public Adc adc(int idSucursal, int id_staff) {
        Adc adc = null;
        if (idSucursal > 0 && id_staff > 0) {
            int[] val = this.recurso.datosAdc(idSucursal, id_staff);
            if (val != null) {
                adc = new Adc(val[0], val[1], val[2], val[3], val[4], val[5]);
            }
        }
        return adc;
    }    

}
