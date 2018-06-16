package services;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import objects.Lista;
import objects.Usuario;
import resources.solicitudCredito_resource;

/**
 *
 * @author JMalagon
 */
public class solicitudes_service {

    private final solicitudCredito_resource RECURSO;

    public solicitudes_service(String modulo) {
        this.RECURSO = new solicitudCredito_resource(modulo);
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

    public DefaultTableModel tablaSolicitudes(Usuario usuario, Object[] object) {
        String titulos[] = {"Folio", "Monto", "Interés", "Plazo", "Fecha", "Hora"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos);
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
        return dtm;
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
        }else{
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
