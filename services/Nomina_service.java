package services;

import java.util.Arrays;
import javax.swing.ComboBoxModel;
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
    /**
     * Regresa un objeto JTable con los datos del empleado
     * registradas en la base de datos.
     *
     * @return new JTable se usa para mostrar y editar tablas de celdas bidimensionales regulares
     * Se usará para mostrar los datos del empleado
     */
    public JTable tablaNomina(JTable tabla, int departamento, int cargo, int semana) {
        String titulos[] = {"Nombre", "Cargo", "Horario", "Pagos"};
        TableCreator tcr = new TableCreator();       
        DefaultTableModel dtm = tcr.noEditableTableModel(titulos);
        String filtroBusqueda = this.filtro(departamento, cargo, semana);
        String[][] array = this.RECURSO.nomina(filtroBusqueda);
        if (array != null) {
            for (String[] val : array) {             
                dtm.addRow(val);
            }
        } else {
            Object[] cli = new Object[2];
            cli[0]="";
            cli[1] = "NO SE OBTUVIERON RESULTADOS";
            dtm.addRow(cli);
        }        
        tabla.setModel(dtm);
        return tabla; 
        //tabla.setColumnModel(tcr.resizeTableDireccionesGuardadas(tabla));
        
    }
 /**
     * Regresa un objeto setModel con los resultados del empleado  en
     * específico registrados en la base de datos. Se mostrarán el nombre,el cargo, el horario 
     * de trabajo y su sueldo
     * 
     * @return new DefaultComboBoxModel: Objeto que contiene los Cargos de cada empleado.
     * 
     */
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

  private String filtro (int departamento, int cargo, int semana){
      String fi = "departamento =" + departamento;
      if(cargo > 0 && semana > 0){
          return fi + "AND cargo = "+ cargo+"AND semana =" + semana;
      } else if (cargo > 0 && semana == 0){
          return fi + "AND cargo = " + cargo;
      }else if (cargo==0 && semana>0) {
          return fi + "AND semana = " + semana;
      }else {
          return fi + "LIMIT 50";
      }
  }
  public DefaultComboBoxModel areas(int departamento){
      DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
      String[] array = this.RECURSO.departamentos(departamento);
       if (array != null) {
            dcbm.addElement(new Lista(0, "-- Seleccione --"));
            for (String array1 : array) {
                dcbm.addElement(new Lista(Integer.valueOf(array1), "Departamento " + array1));
            }
       }
       return dcbm;
  }
    public DefaultComboBoxModel usuario(int sucursal, int agencia) {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        String[] array = this.RECURSO.usuario(sucursal, agencia);
        if (array != null) {
            dcbm.addElement(new Lista(0, "-- Seleccione --"));
            for (String array1 : array) {
                dcbm.addElement(new Lista(Integer.valueOf(array1), "Z" + agencia + "-" + array1));
            }
        } else {
            dcbm.addElement(new Lista(0, "-- Sin resultados --"));
        }
        return dcbm;
    }
}
