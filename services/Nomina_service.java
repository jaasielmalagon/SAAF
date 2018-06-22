package services;

import java.awt.geom.Point2D;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import maps.java.Geocoding;
import objects.Domicilio;
import objects.Fecha;
import objects.Mes;
import objects.Persona;
import objects.TableCreator;
import objects.Usuario;
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
       
        public JTable buscarNomina(JTable tabla, int id) {
        String titulos[] = {"ID", "Nombre", "Horario", "Pagos"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos);
        int[][] resultados = this.RECURSO.buscarNomina(0);
        if (resultados != null) {
            for (int[] resultado : resultados) {
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
        TableCreator tcr = new TableCreator();
        tabla.setModel(dtm);
        tabla.setColumnModel(tcr.resizeTableDireccionesGuardadas(tabla));
        return tabla;
    }

       
}
