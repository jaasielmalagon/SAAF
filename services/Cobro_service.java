/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import objects.Cobro;
import objects.TableCreator;
import resources.Cobro_resource;

/**
 *
 * @author 76053
 */
public class Cobro_service {
    private final Cobro_resource recurso;

    public Cobro_service(String modulo) {
        this.recurso = new Cobro_resource();
    }
    
     public JTable buscarFolio(JTable tabla, String Folio) {
        String titulos[] = {"campo1", "campo2", "campo3", "campo4"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos);
        String[][] resultados = this.recurso.buscarFolios(Folio);
//        System.out.println(Arrays.deepToString(resultados));
        if (resultados != null) {
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
        TableCreator tcr = new TableCreator();
        tabla.setModel(dtm);
        tabla.setColumnModel(tcr.resizeTableDireccionesGuardadas(tabla));
        return tabla;
    }
 public Cobro buscarFolioGuardado(String Folio, String campo1, String campo2) {
        Cobro cob = null;
        String[] d = this.recurso.buscarFolio(Folio, campo1, campo2);
        if (d != null) {
            cob = new Cobro(Integer.valueOf(d[0]), d[1], d[2], d[3]);
        }
        return cob;
    }
 public boolean guardarCobro(Cobro cobro) {        
        boolean flag;
        int idCobro = this.recurso.guardarCobro(cobro.getCAMPO1(), cobro.getCAMPO2());
        flag = idCobro > 0;
        return flag;
    }
}
