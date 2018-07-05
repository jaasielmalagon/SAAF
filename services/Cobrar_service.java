package services;

import com.toedter.calendar.JDateChooser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import objects.Cobro;
import objects.Fecha;
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
                    dataInsert += "(" + id + "," + monto + "," + fecha + "," + USUARIO.getIdUsuario() + ",now())";
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

    public String[] getRango(String fecha) {
        return this.RECURSO.getRango(fecha);
    }

    public String[] setRangoSemana() {
        String[] rangoFechas = null;
        try {
            String[] f = new Fecha().fechaSegmentada();
            String fecha = f[3] + "-" + f[2] + "-" + f[1];
//        String fecha = "2018-06-30";
            String[] rango = this.getRango(fecha);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(rango[0]);
            Calendar inicio = Calendar.getInstance();
            inicio.setTime(date); // Configuramos la fecha que se recibe

            rangoFechas = new String[7];
            rangoFechas[0] = sdf.format(sdf.parse(inicio.get(Calendar.YEAR) + "-" + (inicio.get(Calendar.MONTH) + 1) + "-" + inicio.get(Calendar.DAY_OF_MONTH)));
            for (int i = 1; i < 7; i++) {
                inicio.add(Calendar.DAY_OF_YEAR, 1);  // numero de días a añadir, o restar en caso de días<0
                rangoFechas[i] = sdf.format(sdf.parse(inicio.get(Calendar.YEAR) + "-" + (inicio.get(Calendar.MONTH) + 1) + "-" + inicio.get(Calendar.DAY_OF_MONTH)));
            }
        } catch (ParseException ex) {
            System.out.println("services.Cobrar_service.setRangoSemana() : " + ex);
        }
        return rangoFechas;
    }

    public String elegirFecha(String[] fechas, String d) {        
        if (fechas != null && d != null) {
            try {
                String f = null;
                int dia = Integer.parseInt(d);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (String fecha : fechas) {
                    Date date = sdf.parse(fecha);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int day = cal.get(Calendar.DAY_OF_MONTH);                                        
                    if (day == dia) {
                        f = fecha;
                        break;
                    }
                }
                return f;
            } catch (NumberFormatException | ParseException e) {
                System.out.println("services.Cobrar_service.elegirFecha() : " + e);
                return null;
            }
        }else{
            return null;
        }
    }

    public String[] ultimoPago(int sucursal) {
        return this.RECURSO.getLastPayment(sucursal);
    }

}
