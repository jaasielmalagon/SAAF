package objects;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author JMalagon
 */
public class TableCreator {

    public JTable filasColoreadas(JTable tabla, String valor) {
        int[] filas = null;
        int[] columnas = null;
        for (int i = 0; i < tabla.getRowCount(); i++) {
            for (int j = 0; j < tabla.getColumnCount(); j++) {
                int x = tabla.getValueAt(i, j).toString().indexOf(valor.toUpperCase());
                if (x >= 0) {
                    if (filas == null && columnas == null) {
                        filas = new int[1];
                        columnas = new int[1];
                        filas[0] = i;
                        columnas[0] = j;
                    } else {
                        int[] fil = filas;
                        int[] col = columnas;
                        filas = new int[fil.length + 1];
                        columnas = new int[col.length + 1];
                        filas = fil;
                        columnas = col;
                        filas[filas.length - 1] = i;
                        columnas[columnas.length - 1] = j;
                    }
//                    System.out.print(Arrays.toString(filas));
//                    System.err.print(Arrays.toString(columnas));
//                    tabla.setDefaultRenderer(Object.class, new TableCellRenderer());
                }
            }
        }
        return tabla;
    }

    public TableColumnModel resizeTableEmpleados(JTable tabla) {
        TableColumnModel columnModel = tabla.getColumnModel();
        if (columnModel.getColumnCount() > 0) {
            columnModel.getColumn(0).setPreferredWidth(30);
            columnModel.getColumn(1).setPreferredWidth(90);
            columnModel.getColumn(2).setPreferredWidth(130);
            columnModel.getColumn(3).setPreferredWidth(110);
            columnModel.getColumn(4).setPreferredWidth(60);
            columnModel.getColumn(5).setPreferredWidth(60);
            columnModel.getColumn(6).setPreferredWidth(35);
            columnModel.getColumn(7).setPreferredWidth(120);
            columnModel.getColumn(8).setPreferredWidth(120);
            columnModel.getColumn(9).setPreferredWidth(120);
            columnModel.getColumn(10).setPreferredWidth(120);
            columnModel.getColumn(11).setPreferredWidth(133);
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                columnModel.getColumn(i).setResizable(false);
            }
        }
        return columnModel;
    }

    public TableColumnModel resizeTablePersonas(JTable tabla) {
        TableColumnModel columnModel = tabla.getColumnModel();
        if (columnModel.getColumnCount() > 0) {
            columnModel.getColumn(0).setPreferredWidth(30);
            columnModel.getColumn(1).setPreferredWidth(90);
            columnModel.getColumn(2).setPreferredWidth(130);
            columnModel.getColumn(3).setPreferredWidth(110);
            columnModel.getColumn(4).setPreferredWidth(90);
            columnModel.getColumn(5).setPreferredWidth(90);
            columnModel.getColumn(6).setPreferredWidth(90);
            columnModel.getColumn(7).setPreferredWidth(5);
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                columnModel.getColumn(i).setResizable(false);
            }
        }
        return columnModel;
    }

    public TableColumnModel resizeTableSolicitud(JTable tabla) {
        TableColumnModel columnModel = tabla.getColumnModel();
        if (columnModel.getColumnCount() > 0) {
            columnModel.getColumn(0).setPreferredWidth(30);
            columnModel.getColumn(1).setPreferredWidth(90);
            columnModel.getColumn(2).setPreferredWidth(130);
            columnModel.getColumn(3).setPreferredWidth(110);
            columnModel.getColumn(4).setPreferredWidth(90);
            columnModel.getColumn(5).setPreferredWidth(90);
            columnModel.getColumn(6).setPreferredWidth(90);
            columnModel.getColumn(7).setPreferredWidth(5);
            columnModel.getColumn(8).setPreferredWidth(300);
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                columnModel.getColumn(i).setResizable(false);
            }
        }
        return columnModel;
    }

    public TableColumnModel resizeTableDireccionesGuardadas(JTable tabla) {
        TableColumnModel columnModel = tabla.getColumnModel();
        if (columnModel.getColumnCount() > 0) {
            columnModel.getColumn(0).setPreferredWidth(60);
            columnModel.getColumn(1).setPreferredWidth(800);

            for (int i = 0; i < tabla.getColumnCount(); i++) {
                columnModel.getColumn(i).setResizable(false);
            }
        }
        return columnModel;
    }

    public TableColumnModel noResizable(JTable tabla) {
        TableColumnModel columnModel = tabla.getColumnModel();
        if (columnModel.getColumnCount() > 0) {
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                columnModel.getColumn(i).setResizable(false);
            }
        }
        return columnModel;
    }

    public DefaultTableModel noEditableTableModel(String[] titulos) {
        boolean[] canEdit = new boolean[titulos.length];
        for (int i = 0; i < titulos.length; i++) {
            canEdit[i] = false;
        }
        return new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                //return false;
                return canEdit[column];
            }
        };
    }

}
