package services;

import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import objects.Cobro;
import objects.Lista;
import objects.TableCreator;
import objects.Usuario;
import resources.Cobrar_resource;

/**
 * Regresa un objeto JTable con los datos del empleado registradas en la base de
 * datos.
 *
 */
public class Cobrar_service {

    private final Cobrar_resource RECURSO;

    public Cobrar_service(String modulo) {
        this.RECURSO = new Cobrar_resource(modulo);
    }

    public JTable tablaPrestamosDe(JTable tabla, int sucursal, int zona, int adc) {
        String titulos[] = {"Folio", "Cliente", "Préstamo", "Capital", "Interés", "Plazo", "Tarifa"};
        TableCreator tcr = new TableCreator();
        DefaultTableModel dtm = tcr.noEditableTableModel(titulos);
        String filtro = this.filtro(sucursal, zona, adc);
        String[][] resultados = this.RECURSO.getPrestamosByAdc(filtro);
        //System.out.println(Arrays.toString(resultados));
        if (resultados != null) {
            for (String[] resultado : resultados) {               
                dtm.addRow(resultado);
            }
        } else {
            Object[] cli = new Object[2];
            cli[0] = "";
            cli[1] = "NO SE OBTUVIERON RESULTADOS";
            dtm.addRow(cli);
        }        
        tabla.setModel(dtm);
        return tabla;
    }
    
    private String filtro(Usuario u, Object[] objects) {
        //int zona, int adc
        String f = "WHERE prestamos.sucursal = " + u.getIdSucursal();
        if (objects != null) {
            for (Object object : objects) {
                Lista l = (Lista) object;
                if (!"".equals(l.getSTRING2())) {
                    
                }
            }
        }
        return f;
    }

    private String filtro(int sucursal, int zona, int adc) {
        String f = "sucursal = " + sucursal;
        if (zona > 0 && adc > 0) {
            return f + " AND zona = " + zona + " AND adc = " + adc;
        } else if (zona > 0 && adc == 0) {
            return f + " AND zona = " + zona;
        } else if (zona == 0 && adc > 0) {
            return f + " AND adc = " + adc;
        } else {
            return f + " LIMIT 50";
        }
    }

    public DefaultComboBoxModel agencias(int sucursal) {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        String[] array = this.RECURSO.agencias(sucursal);
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
        String[] array = this.RECURSO.vacantes(sucursal, agencia);
        if (array != null) {
            dcbm.addElement(new Lista(0, "-- Seleccione --"));
            for (String array1 : array) {
                dcbm.addElement(new Lista(Integer.valueOf(array1), "Vacante " + array1));
            }
        }else{
            dcbm.addElement(new Lista(0, "-- Sin resultados --"));
        }
        return dcbm;
    }

}
