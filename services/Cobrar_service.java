package services;

import java.util.Arrays;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import objects.Cobro;
import objects.TableCreator;
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

    public JTable tablaPrestamosDe(JTable tabla, int zona, int adc) {
        String titulos[] = {"Folio", "Cliente", "Préstamo", "Capital", "Interés", "Plazo", "Tarifa"};
        TableCreator tcr = new TableCreator();
        DefaultTableModel dtm = tcr.noEditableTableModel(titulos);
        String[][] resultados = this.RECURSO.getPrestamosByAdc(zona, adc);
        System.out.println(Arrays.deepToString(resultados));
        /*if (resultados != null) {
            for (String[] resultado : resultados) {
                Object[] o = new Object[4];
                o[0] = resultado[0];
                o[1] = resultado[1];
                o[2] = resultado[2];
                o[3] = resultado[3];
                dtm.addRow(o);
            }
        } else {
            Object[] cli = new Object[2];
            cli[0] = "";
            cli[1] = "NO SE OBTUVIERON RESULTADOS";
            dtm.addRow(cli);
        }
        */
        tabla.setModel(dtm);        
        return tabla;
    }

    /**
     * Regresa un objeto setModel con los resultados del empleado en específico
     * registrados en la base de datos. Se mostrarán el nombre,la zona, el ADC
     * de nombre.
     *
     * @return Objeto que contiene los campos de zona y ADC de cada empleado.
     *
     */
    public Cobro buscarFolioGuardado(String Folio, String campo1, String campo2) {
        Cobro cob = null;
        String[] d = this.RECURSO.buscarFolio(Folio, campo1, campo2);
        if (d != null) {
            cob = new Cobro(Integer.valueOf(d[0]), d[1], d[2], d[3]);
        }
        return cob;
    }

    public boolean guardarCobro(Cobro cobro) {
        boolean flag;
        int idCobro = this.RECURSO.guardarCobro(cobro.getCAMPO1(), cobro.getCAMPO2());
        flag = idCobro > 0;
        return flag;
    }

}
