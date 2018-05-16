package objects;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author JMalagon
 */
public class TableCellRenderer extends DefaultTableCellRenderer {

    private Component componente;
    private int fila, columna;

    public TableCellRenderer(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;        
    }

    public TableCellRenderer() {

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);        
        if ((row == this.fila && column == this.columna) || (componente.getBackground() == Color.red && componente.getForeground() == Color.white)) {
            componente.setBackground(Color.red);
            componente.setForeground(Color.white);
//            System.out.println(this.fila[0] + " " + this.columna[0]);
        }
        return componente;
    }

//    @Override
//    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//        if (c != null) {
//            componente = this.c;
//            System.out.println("COMPONENTE NO NULO");
//        }else{
//            componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//            System.out.println("COMPONENTE NULO");
//        }        
//        if ((row == this.fila[0] && column == this.columna[0]) || (componente.getBackground() == Color.red && componente.getForeground() == Color.white)) {
//            componente.setBackground(Color.red);
//            componente.setForeground(Color.white);
//            System.out.println(this.fila[0] + " " + this.columna[0]);
//        }
//        if (this.fila.length > 1 && this.columna.length > 1) {
//            int[] fil = this.fila;
//            int[] colu = this.columna;
//            this.fila = new int[fila.length - 1];
//            this.columna = new int[columna.length - 1];
//            int x = 1;
//            for (int i = 0; i < this.fila.length; i++) {
//                this.fila[i] = fil[x];
//                x++;
//            }
//            x = 1;
//            for (int i = 0; i < this.columna.length; i++) {
//                this.columna[i] = colu[x];
//                x++;
//            }
//            System.out.println("Recursando...");
//            new TableCellRenderer(fila, columna, componente);
//        }
//        return componente;
//    }
}

//
//    public JTable filasColoreadas(JTable tabla, String valor) {
//        int[] filas = null;
//        int[] columnas = null;
//        for (int i = 0; i < tabla.getRowCount(); i++) {
//            for (int j = 0; j < tabla.getColumnCount(); j++) {
//                int x = tabla.getValueAt(i, j).toString().indexOf(valor.toUpperCase());
//                if (x >= 0) {
//                    tabla = new JTable(tabla.getModel()) {
//                        public boolean isCellEditable(i, j) {
//                            return false; //Las celdas no son editables.
//                        }
//                    };
//                    tabla.isCellEditable(i, j);
////                    if (filas == null && columnas == null) {
////                        filas = new int[1];
////                        columnas = new int[1];
////                        filas[0] = i;
////                        columnas[0] = j;
////                    } else {
////                        int[] fil = filas;
////                        int[] col = columnas;
////                        filas = new int[fil.length];
////                        columnas = new int[col.length];
////                        filas = fil;
////                        columnas = col;
////                        filas[filas.length - 1] = i;
////                        columnas[columnas.length - 1] = j;
////                    }
//                }
//            }
//        }
//        this.fila = filas;
//        this.columna = columnas;
//        tabla.setDefaultRenderer(Object.class, new TableCellRenderer());
//        return tabla;
//    }
