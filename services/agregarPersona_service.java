package services;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import objects.Amortizacion;
import objects.Cliente;
import objects.Estado;
import objects.Fecha;
import objects.Persona;
import objects.Solicitud;
import objects.TableCreator;
import objects.Usuario;
import resources.agregarPersona_resource;

/**
 *
 * @author JMalagon
 */
public class agregarPersona_service {

    private final agregarPersona_resource recurso;

    public agregarPersona_service(String modulo) {
        this.recurso = new agregarPersona_resource(modulo);
    }

    private Amortizacion obtenerInteres(int plazo, String tasa, int montoSolicitado) {
        Amortizacion amr = new Amortizacion();
        if (plazo == 20) {//TABLAS A 20 SEMANAS                                    
            switch (tasa) {
                case "14.5":
                    amr.tabla145_20(montoSolicitado, plazo);//14.5% A 20 SEMANAS
                    break;
                case "14.0":
                    amr.tabla140_20(montoSolicitado, plazo);//14.0% A 20 SEMANAS
                    break;
                case "13.5":
                    amr.tabla135_20(montoSolicitado, plazo);//13.5% A 20 SEMANAS
                    break;
                case "13.0":
                    amr.tabla130_20(montoSolicitado, plazo);//13.0% A 20 SEMANAS
                    break;
                default:
                    System.out.println("No se encuentra la tabla de amortización para los parámetros ingresados.\nLa operación ha sido cancelada");
                    break;
            }
        } else if (plazo == 24) {//TABLAS A 24 SEMANAS
            switch (tasa) {
                case "14.5":
                    amr.tabla145_24(montoSolicitado, plazo);//14.5% A 24 SEMANAS
                    break;
                case "14.0":
                    amr.tabla140_24(montoSolicitado, plazo);//14.0% A 24 SEMANAS
                    break;
                case "13.5":
                    amr.tabla135_24(montoSolicitado, plazo);//13.5% A 24 SEMANAS
                    break;
                case "13.0":
                    amr.tabla130_24(montoSolicitado, plazo);//13.0% A 24 SEMANAS
                    break;
                default:
                    System.out.println("No se encuentra la tabla de amortización para los parámetros ingresados.\nLa operación ha sido cancelada");
                    break;
            }
        }
        return amr;
    }

    public String solicitarPrestamo(Usuario usuario, Persona p, Object cantidades, Object sems) {
        try {
            if (p.getReferencia() > 0) {
                if (cantidades != null) {
                    Cliente cliente = this.cliente(p);
                    if (cliente != null) {
                        double pagoMax = cliente.getINGRESOS() - cliente.getEGRESOS();//LO MÁS QUE PUEDE PAGAR                        
                        int nPrestamos = 100;//CONSULTAR A LA BASE DE DATOS CUANTOS PRESTAMOS LLEVA EL CLIENTE
                        double tasa;
                        //GENERAR LA TASA SEGUN EL NUMERO DE PRESTAMOS QUE TIENE EL CLIENTE
                        if (nPrestamos >= 10) {
                            tasa = 13.0;
                        } else if (nPrestamos <= 9 && nPrestamos >= 7) {
                            tasa = 13.5;
                        } else if (nPrestamos <= 6 && nPrestamos >= 4) {
                            tasa = 14.0;
                        } else {
                            tasa = 14.5;
                        }

                        if (sems != null) {
                            int plazo = Integer.parseInt(sems.toString());//SEMANAS CONVERTIDAS A int (20 O 24)
                            int montoSolicitado = Integer.parseInt(cantidades.toString());//MONTO SOLICITADO (DE 1000 A 10000)                                
                            String tasaString = String.valueOf(tasa);//TASA EN STRING PARA UTILIZAR EL SWITCH NADA MAS
                            //SEGUN EL PLAZO SOLICITADO SE BUSCA Y GENERA LA TABLA DE AMORTIZACIÓN CORRESPONDIENTE A 20 O 24 SEMANAS
                            Amortizacion amr = this.obtenerInteres(plazo, tasaString, montoSolicitado);
                            //TENIENDO LA AMORTIZACIÓN BUSCAMOS SEGUN EL MONTO SOLICITADO, POR POLITICA A PARTIR DE $5000 SE SOLICITA EL RESPALDO DE UN AVAL
                            if (amr.getMONTO() >= 5000 && p.getAval() <= 0) {
                                return "La operación ha sido cancelada porque la persona seleccionada no cuenta con un aval asignado\ny el monto seleccionado es mayor o igual a $5000.00";
                            } else {
                                Solicitud ultimaSolicitud = this.ultimaSolicitud(cliente);
                                Solicitud solicitudNueva = new Solicitud(0, amr.getMONTO(), plazo, cliente.getID(), usuario.getIdUsuario(), usuario.getIdSucursal(), tasa, "", "");
                                if (this.compararFechaSolicitud(ultimaSolicitud)) {
                                    return "Este cliente ya cuenta con una solicitúd expedida durante este día. Intente de nuevo el día de mañana.";
                                } else {
                                    boolean solIns = this.guardarSolicitud(solicitudNueva);
                                    if (solIns) {
                                        return "Solicitud guardada correctamente. Esté pendiente del resultado...";
                                    } else {
                                        return "No se guardó la solicitud";
                                    }
                                }
                                /**/
                            }
                        } else {
                            return "La operación ha sido cancelada";
                        }
                    } else {
                        return "La persona seleccionada todavía no cuenta con datos de cliente";
                    }
                } else {
                    return "La operación ha sido cancelada";
                }
            } else {
                return "La persona seleccionada no cuenta con una referencia válida asociada";
            }
        } catch (NumberFormatException e) {
            return "Debe ingresar un monto válido";
        }
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
            String[] datos = this.recurso.fechaSolicitudAnterior(cliente.getID());
            if (datos != null) {
                solicitud = new Solicitud(Integer.valueOf(datos[0]), Integer.valueOf(datos[1]), Integer.valueOf(datos[3]), Integer.valueOf(datos[4]), Integer.valueOf(datos[5]), Integer.valueOf(datos[6]), Integer.valueOf(datos[2]), datos[7], datos[8]);
            }
        }
        return solicitud;
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
