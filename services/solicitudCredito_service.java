package services;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import objects.TableCreator;
import resources.solicitudCredito_resource;

/**
 *
 * @author JMalagon
 */
public class solicitudCredito_service {
    
    private final solicitudCredito_resource RECURSO;
    
    public solicitudCredito_service(){
        this.RECURSO = new solicitudCredito_resource();
    }
    
    public JTable tablaPersonas(JTable tabla,int idSucursal, String dato) {
        String titulos[] = {"Folio", "Nombre", "Apellidos", "CURP", "OCR", "Teléfono", "Celular", "Domicilio","Ingresos","Egresos","Dependientes","Ocupación","Nivel Estudios","Empresa"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos);
        String[][] array = this.RECURSO.personas(idSucursal, dato);
        if (array != null) {
            for (String[] var : array) {
                Object[] cli = new Object[9];
                cli[0] = var[0];
                cli[1] = var[1];
                cli[2] = var[2] + " " + var[3];
                cli[3] = var[4];
                cli[4] = var[5];
                cli[5] = var[6];
                cli[6] = var[7];
                cli[7] = var[8];
                cli[8] = var[9];
                dtm.addRow(cli);
            }
        }
        TableCreator tcr = new TableCreator();
        tabla.setModel(dtm);        
        tabla.setColumnModel(tcr.resizeTableSolicitud(tabla)); 
//        tcr.filasColoreadas(tabla, dato);        
        return tabla;
    }
}
