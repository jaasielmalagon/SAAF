package services;

import javax.swing.ComboBoxModel;
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
                dcbm.addElement(new Lista(Integer.parseInt(val[0]), "Z" + val[1] + "-" + val[2]));
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
        } else {
            Object[] cli = new Object[1];
            cli[0] = "SIN RESULTADOS";
            dtm.addRow(cli);
        }
        return dtm;
    }

    private String filtro(Usuario u, Object[] objects) {
        String f = "sucursal = " + u.getIdSucursal();
        if (objects != null) {
            for (Object object : objects) {
                Lista l = (Lista) object;
                if (l.getID() > 0) {
                    f = f + " AND " + l.getSTRING();
                }
            }
        }
        return f;
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
