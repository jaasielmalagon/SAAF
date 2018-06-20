package services;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import objects.Amortizacion;
import objects.Cliente;
import objects.Lista;
import objects.Persona;
import objects.Solicitud;
import objects.TableCreator;
import objects.Usuario;
import resources.solicitudCredito_resource;

/**
 *
 * @author JMalagon
 */
public class solicitudes_service {

    private final solicitudCredito_resource RECURSO;
    private final String modulo;

    public solicitudes_service(String modulo) {
        this.modulo = modulo;
        this.RECURSO = new solicitudCredito_resource(this.modulo);
    }

    public String solicitarPrestamo(Usuario usuario, Persona p, Object cantidades, Object sems) {
        try {
            if (p.getReferencia() > 0) {
                if (cantidades != null) {
                    Cliente cliente = new clientes_service(modulo).cliente(p);
                    if (cliente != null) {
                        double pagoMax = cliente.getINGRESOS() - cliente.getEGRESOS();//LO MÁS QUE PUEDE PAGAR                        
                        int nPrestamos = 1;//CONSULTAR A LA BASE DE DATOS CUANTOS PRESTAMOS LLEVA EL CLIENTE
                        Amortizacion amr = new Amortizacion();
                        double tasa = amr.getTasa(nPrestamos);
                        //GENERAR LA TASA SEGUN EL NUMERO DE PRESTAMOS QUE TIENE EL CLIENTE                        
                        if (sems != null) {
                            int plazo = Integer.parseInt(sems.toString());//SEMANAS CONVERTIDAS A int (20 O 24)
                            int montoSolicitado = Integer.parseInt(cantidades.toString());//MONTO SOLICITADO (DE 1000 A 10000)                                
                            String tasaString = String.valueOf(tasa);//TASA EN STRING PARA UTILIZAR EL SWITCH NADA MAS
                            //SEGUN EL PLAZO SOLICITADO SE BUSCA Y GENERA LA TABLA DE AMORTIZACIÓN CORRESPONDIENTE A 20 O 24 SEMANAS
                            amr.setAmortizacion(plazo, montoSolicitado, tasaString);
                            //TENIENDO LA AMORTIZACIÓN BUSCAMOS SEGUN EL MONTO SOLICITADO, POR POLITICA A PARTIR DE $5000 SE SOLICITA EL RESPALDO DE UN AVAL
                            if (amr.getMONTO() >= 5000 && p.getAval() <= 0) {
                                return "La operación ha sido cancelada porque la persona seleccionada no cuenta con un aval asignado\ny el monto seleccionado es mayor o igual a $5000.00";
                            } else {
                                Solicitud ultimaSolicitud = this.ultimaSolicitud(cliente);
                                Solicitud solicitudNueva = new Solicitud(0, amr.getMONTO(), plazo, cliente.getID(), usuario.getIdUsuario(), usuario.getIdSucursal(), tasa, "", "", 0);
                                if (this.compararFechaSolicitud(ultimaSolicitud)) {
                                    return "Este cliente ya cuenta con una solicitúd expedida durante este día. Intente de nuevo el día de mañana.";
                                } else {
                                    System.out.println(modulo + " "+solicitudNueva.toString());
                                    boolean solIns = false;//this.guardarSolicitud(solicitudNueva);
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
        return this.RECURSO.guardarSolicitud(solicitud.getMONTO(), solicitud.getPLAZO(), solicitud.getCLIENTE(), solicitud.getUSUARIO(), solicitud.getSUCURSAL(), solicitud.getTASA());
    }

    public boolean compararFechaSolicitud(Solicitud ultima) {
        try {
            if (ultima != null) {
                String fechaServidor = this.RECURSO.fechaServidor();
                String fechaSolicitud = fechaServidor.substring(0, 10);
                return ultima.getFecha().equals(fechaSolicitud);
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
            String[] datos = this.RECURSO.fechaSolicitudAnterior(cliente.getID());
            if (datos != null) {
                solicitud = new Solicitud(Integer.valueOf(datos[0]), Integer.valueOf(datos[1]), Integer.valueOf(datos[3]), Integer.valueOf(datos[4]), Integer.valueOf(datos[5]), Integer.valueOf(datos[6]), Integer.valueOf(datos[2]), datos[7], datos[8], Integer.valueOf(datos[9]));
            }
        }
        return solicitud;
    }

    public String aprobacionSolicitud(Solicitud solicitud) {
        boolean b = this.RECURSO.cambiarEstadoSolicitud(solicitud.getID(), solicitud.getESTADO());
        if (b) {
            String estado = "";
            if (solicitud.getESTADO() == 0) {
                estado = "rechazada";
            } else if (solicitud.getESTADO() == 2) {
//                b = this.insertarPrestamo(solicitud);
                estado = "aprobada";
            }
            return "Solicitud " + estado + " correctamente";
        } else {
            return "El estado de la solicitud no pudo ser cambiado";
        }
    }

    public Solicitud solicitud(Usuario usuario, int idSolicitud) {
        String[] array = this.RECURSO.getSolicitud(idSolicitud, usuario.getIdSucursal());
        if (array != null) {
            Solicitud solicitud = new Solicitud();
            solicitud.setID(array[0]);
            solicitud.setMONTO(array[1]);
            solicitud.setTASA(array[2]);
            solicitud.setPLAZO(array[3]);
            solicitud.setCLIENTE(array[4]);
            solicitud.setUSUARIO(array[5]);
            solicitud.setSUCURSAL(array[6]);
            solicitud.setFECHA(array[7]);
            solicitud.setHORA(array[8]);
            solicitud.setESTADO(array[9]);
            return solicitud;
        } else {
            return null;
        }
    }

    public DefaultComboBoxModel comboAdc(Usuario USUARIO) {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        String[][] array = this.RECURSO.getAdcFromSucursal(USUARIO.getIdSucursal());
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

    public JTable tablaSolicitudes(JTable tabla, Usuario usuario, Object[] object) {
        String titulos[] = {"Folio", "Monto", "Interés", "Plazo", "Fecha", "Hora"};
        TableCreator tcr = new TableCreator();
        DefaultTableModel dtm = tcr.noEditableTableModel(titulos);
        String filtro = this.filtro(usuario, object);
        String[][] array = this.RECURSO.solicitudes(filtro);
        if (array != null) {
            for (String[] var : array) {
                Object[] cli = new Object[var.length];
                cli[0] = var[0];
                cli[1] = var[1];
                cli[2] = var[2] + "%";
                cli[3] = var[3] + " semanas";
                cli[4] = var[7];
                cli[5] = var[8];
                dtm.addRow(cli);
            }
        } else {
            Object[] cli = new Object[1];
            cli[0] = "SIN RESULTADOS";
            dtm.addRow(cli);
        }
        tabla.setModel(dtm);
        tabla.setColumnModel(tcr.noResizable(tabla));
        return tabla;
    }

    private String filtro(Usuario u, Object[] objects) {
        String f = "WHERE prestamos_solicitudes.sucursal = " + u.getIdSucursal();
        if (objects != null) {
            for (Object object : objects) {
                Lista l = (Lista) object;
                if (l.getID() > 0) {
                    if ("adc".equals(l.getSTRING2())) {
//                        int solicitudes = this.RECURSO.contarSolicitudesDeClientes(this.condicionContarSolicitudes(l));
//                        f = " INNER JOIN personas_clientes ON prestamos_solicitudes.cliente = personas_clientes.idCliente " + f + " AND personas_clientes.adc = " + l.getID();
                    } else if ("fecha".equals(l.getSTRING2()) && l.getID() > 0) {
                        String order = "";
                        if (l.getID() == 1) {
                            order = "ASC";
                        } else if (l.getID() == 2) {
                            order = "DESC";
                        }
                        f = f + " ORDER BY " + l.getSTRING2() + " " + order;
                    } else {
                        f = f + " AND " + l.getSTRING2() + " = " + l.getID();
                    }
                }
            }
            System.out.println(f);
        }
        return f;
    }

    private String condicionContarSolicitudes(Lista l) {
        String condicion = "";
        int[] idClientes = this.RECURSO.clientesDeAdc(l.getID());
        if (idClientes.length > 1) {
            int i = 1;
            for (int idCliente : idClientes) {
                if (i == 1) {
                    condicion = "cliente = " + idCliente;
                } else {
                    condicion = condicion + " OR cliente = " + idCliente;
                }
                i++;
            }
        } else {
            condicion = "cliente = " + idClientes[0];
        }
        return condicion;
    }

    public DefaultComboBoxModel comboPlazo() {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        dcbm.addElement(new Lista(0, "--- Seleccione ---", ""));
        dcbm.addElement(new Lista(20, "20 semanas", "plazo"));
        dcbm.addElement(new Lista(24, "24 semanas", "plazo"));
        return dcbm;
    }

    public DefaultComboBoxModel comboFecha() {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        dcbm.addElement(new Lista(0, "--- Seleccione ---", ""));
        dcbm.addElement(new Lista(1, "Ascendente", "fecha"));
        dcbm.addElement(new Lista(2, "Descendente", "fecha"));
        return dcbm;
    }

    public DefaultComboBoxModel comboMonto() {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        dcbm.addElement(new Lista(0, "--- Seleccione ---", ""));
        int monto = 1000;
        while (monto <= 10000) {
            dcbm.addElement(new Lista(monto, String.valueOf(monto), "monto"));
            monto = monto + 500;
        }
        return dcbm;
    }
}
