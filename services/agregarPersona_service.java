package services;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import objects.Cliente;
import objects.Estado;
import objects.Fecha;
import objects.Persona;
import objects.Solicitud;
import objects.TableCreator;
import resources.agregarPersona_resource;

/**
 *
 * @author JMalagon
 */
public class agregarPersona_service {

    private final agregarPersona_resource recurso;

    public agregarPersona_service() {
        this.recurso = new agregarPersona_resource();
    }

    public boolean guardarSolicitud(Solicitud solicitud) {
        return this.recurso.guardarSolicitud(solicitud.getMONTO(), solicitud.getPLAZO(), solicitud.getCLIENTE(), solicitud.getUSUARIO(), solicitud.getSUCURSAL(), solicitud.getTASA());
    }

    public boolean compararFechaSolicitud(Solicitud ultima) {
        try {
            if (ultima != null) {
                String fechaServidor = this.recurso.fechaServidor();
                String fecha = fechaServidor.substring(0, 10);
                System.out.println("Fecha actual: " + fecha);
                return ultima.getFecha().equals(fecha);
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public Solicitud ultimaSolicitud(Cliente cliente) {
        Solicitud solicitud = null;
        if (cliente != null) {
            String[] datos = this.recurso.fechaSolicitudAnterior(cliente.getIdCliente());
            if (datos != null) {
                solicitud = new Solicitud(Integer.valueOf(datos[0]), Integer.valueOf(datos[1]), Integer.valueOf(datos[3]), Integer.valueOf(datos[4]), Integer.valueOf(datos[5]), Integer.valueOf(datos[6]), Integer.valueOf(datos[2]), datos[7], datos[8]);
            }            
        } 
        return solicitud;
    }

    public Cliente cliente(Persona persona) {
        String[] datos = this.recurso.cliente(persona.getIdPersona());
        Cliente cliente = null;
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

    public boolean agregarReferencia(int idCliente, String valores) {
        return this.recurso.agregarReferencia(idCliente, valores);
    }

    public boolean buscarReferencia(String condicion) {
        return this.recurso.buscarReferencia(condicion);
    }

    public int buscarPersonaSinDomicilio(int idPersona) {
        return this.recurso.buscarPersonaSinDomicilio(idPersona);
    }

    public int actualizarPersona(int idPersona, int idDomicilio) {
        int error = 0;
        if (idPersona > 0 && idDomicilio > 0) {
//            int asociados = this.recurso.contarAsociaciones(idDomicilio);
//            if (asociados < 3) {
            boolean asociar = this.recurso.asociarDomicilio(idPersona, idDomicilio);
            if (asociar != true) {
                error = 2;//ALGO FALLÓ EN LA ACTUALIZACIÓN
            }
//            } else {
//                error = 1;//EL DOMICILIO YA TIENE 3 ASOCIADOS
//            }
        }
        return error;
    }

    public DefaultTableModel tablaPersonas2(int idSucursal, int idPersona, String dato) {
        String titulos[] = {"Folio", "Nombre", "Apellidos", "CURP", "OCR", "Teléfono", "Celular", "Sexo"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos);
        String[][] array = this.recurso.personas2(idSucursal, idPersona, dato);
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
        return dtm;
    }

    public DefaultTableModel tablaPersonas2(int idSucursal, int idPersona) {
        String titulos[] = {"Folio", "Nombre", "Apellidos", "CURP", "OCR", "Teléfono", "Celular", "Sexo"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos);
        String[][] array = this.recurso.personas2(idSucursal, idPersona, "");
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
        return dtm;
    }

    public JTable tablaPersonas(JTable tabla, int idSucursal, String dato) {
        String titulos[] = {"Folio", "Nombre", "Apellidos", "CURP", "OCR", "Teléfono", "Celular", "Sexo"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos);
        String[][] array = this.recurso.personas(idSucursal, dato);
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
//        tcr.filasColoreadas(tabla, dato);        
        return tabla;
    }

    public JTable tablaPersonas(JTable tabla, int idSucursal) {
        String titulos[] = {"Folio", "Nombre", "Apellidos", "CURP", "OCR", "Teléfono", "Celular", "Sexo"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos);
        String[][] array = this.recurso.personas(idSucursal, "");
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

    public Persona persona(int idSucursal, int idPersona) {
        Persona personas = null;
        String[] array = this.recurso.persona(idSucursal, idPersona);
        if (array != null) {
            personas = new Persona(Integer.parseInt(array[0]), array[1], array[2], array[3], array[4], Integer.parseInt(array[5]), array[6], array[7], array[8], Integer.parseInt(array[9]), array[10], array[11], Integer.parseInt(array[12]), Integer.parseInt(array[13]), Integer.parseInt(array[14]), Integer.parseInt(array[15]));
        }
        return personas;
    }

    public DefaultComboBoxModel estados() {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        String[][] array = this.recurso.estados();
        if (array != null) {
            for (String[] val : array) {
                dcbm.addElement(new Estado(Integer.parseInt(val[0]), val[1]));
            }
        }
        return dcbm;
    }

    public String estado(int id) {
        return recurso.estado(id);
    }    

    public int buscarCliente(String curp, String ocr, String cel, String tel) {
        return this.recurso.buscarCliente(curp, ocr, cel, tel);
    }

    public int guardarDatosCliente(int usuario, int sucursal, String nombre, String apaterno, String amaterno, String f_nac, int idEstadoNac, String curp, String ocr, String sexo, int edoCivil, String tel, String cel) {
        int error = 0;
        Fecha fecha = new Fecha();
        if (fecha.isValid(f_nac) == true) {
            int ano = Integer.parseInt(f_nac.substring(0, 4));
            if (ano > (fecha.currentYear() - 18)) {
                System.out.println("La persona es menor de 18 años");
                error = 2;
            }
            if (error == 0 && fecha.compareMinorDate(f_nac) == true) {
                boolean gdc = this.recurso.guardarDatosCliente(usuario, sucursal, nombre, apaterno, amaterno, f_nac, idEstadoNac, curp, ocr, sexo, edoCivil, tel, cel);
                if (gdc != true) {
                    error = 3;
                }
            }
        } else {
            System.out.println("La fecha ingresada no es válida.");
            error = 1;
        }
        return error;
    }

    public int actualizarDatosCliente(int usuario, int idPersona, int sucursal, String nombre, String apaterno, String amaterno, String f_nac, int idEstadoNac, String curp, String ocr, String sexo, int edoCivil, String tel, String cel) {
        int error = 0;
        Fecha fecha = new Fecha();
        if (fecha.isValid(f_nac) == true) {
            int ano = Integer.parseInt(f_nac.substring(0, 4));
            if (ano > (fecha.currentYear() - 18)) {
                System.out.println("La persona es menor de 18 años");
                error = 2;
            }
            if (error == 0 && fecha.compareMinorDate(f_nac) == true) {
                boolean adc = this.recurso.actualizarDatosCliente(usuario, idPersona, sucursal, nombre, apaterno, amaterno, f_nac, idEstadoNac, curp, ocr, sexo, edoCivil, tel, cel);
                if (adc != true) {
                    error = 3;
                }
            }
        } else {
            System.out.println("La fecha ingresada no es válida.");
            error = 1;
        }
        return error;
    }
}
