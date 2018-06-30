package services;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
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
                dcbm.addElement(new Lista(Integer.valueOf(array1), "Z" + agencia + "-" + array1));
            }
        } else {
            dcbm.addElement(new Lista(0, "-- Sin resultados --"));
        }
        return dcbm;
    }

    public String guardarPagos(Usuario USUARIO, String[][] pagos) {
        if (pagos != null) {
            String dataInsert = "";
            int limite = pagos.length;
            int i = 1;
            for (String[] pago : pagos) {
                System.out.println(Arrays.toString(pago));
                int id = 0;
                double monto = 0;
                String fecha = null;
                try {
                    id = Integer.parseInt(pago[0]);
                    monto = Double.parseDouble(pago[1]);
                    fecha = pago[2];
                } catch (NumberFormatException e) {
                }

                if (id > 0 && monto > 0) {
                    if (fecha == null) {
                        fecha = "now()";
                    } else {
                        fecha = "'" + fecha + "'";
                    }
                    dataInsert += "(" + id + "," + monto + "," + fecha + "," + USUARIO.getIdUsuario() + ")";
                    if (i < limite) {
                        dataInsert += ",";
                    }
                    i++;
                }
            }
            System.out.println(dataInsert);
            boolean b = this.RECURSO.guardarPagos(dataInsert);
            if (b) {
                return "Cobranza guardada exitosamente";
            } else {
                return "Ocurrió un error al intentar guardar los datos";
            }
        } else {
            return "No ha capturado ningún valor válido, los datos no fueron guardados";
        }
    }

}
