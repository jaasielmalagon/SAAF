package services;

import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import objects.Lista;
import objects.TableCreator;
import resources.Nomina_resource;

/**
 *
 * @author mield
 */
public class Nomina_service {

    private final Nomina_resource RECURSO;

    public Nomina_service(String modulo) {
        this.RECURSO = new Nomina_resource(modulo);
    }

    public JTable tablaNomina(JTable tabla, String codigo, Object[] filtro) {
        TableCreator tcr = new TableCreator();
        String titulos[] = {"Nombre", "Cargo", "Horario", "Pagos"};
        DefaultTableModel dtm = tcr.noEditableTableModel(titulos);
        String filtroBusqueda = this.filtro(codigo, filtro);
        String[][] array = this.RECURSO.nomina(filtroBusqueda);
        if (array != null) {
            for (String[] val : array) {
                Object[] o = new Object[val.length];
                System.arraycopy(val, 0, o, 0, o.length);                  
                dtm.addRow(o);
            }
        } else {
            Object[] cli = new Object[1];
            cli[0] = "NO SE OBTUVIERON RESULTADOS";
            dtm.addRow(cli);
        }        
        tabla.setModel(dtm);
        tabla.setColumnModel(tcr.resizeTableDireccionesGuardadas(tabla));
        return tabla;
    }

    public DefaultComboBoxModel cargos() {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        String[][] array = this.RECURSO.getCargos();
        
        if (array != null) {
            dcbm.addElement(new Lista(0, "-- Seleccione --"));
            for (String[] val : array) {
                dcbm.addElement(new Lista(Integer.valueOf(val[0]), val[1]));
            }
        }
        return dcbm;
    }
    private String filtro(String codigo, Object[] filtro) {
        return "";
    }
 
}
