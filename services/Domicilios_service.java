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
import resources.Domicilios_resource;

/**
 *
 * @author JMalagon
 */
public class Domicilios_service {

    private final Domicilios_resource RECURSO;

    public Domicilios_service(String modulo) {
        this.RECURSO = new Domicilios_resource(modulo);
    }

    public JTable buscarDomicilios(JTable tabla, String direccion) {
        String titulos[] = {"ID", "Dirección"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos);
        String[][] resultados = this.RECURSO.buscarDomicilios(direccion);
        if (resultados != null) {
            for (String[] resultado : resultados) {
                Object[] o = new Object[2];
                o[0] = resultado[0];
                o[1] = resultado[1];
                dtm.addRow(o);
            }
            tabla.setModel(dtm);
        } else {
            Object[] cli = new Object[2];
            cli[0] = "";
            cli[1] = "NO SE ENCONTRARON RESULTADOS";
            dtm.addRow(cli);
        }
        return tabla;
    }

    public Domicilio buscarDomicilioGuardado(String direccion, String latitud, String longitud) {
        Domicilio dom = null;
        String[] d = this.RECURSO.buscarDomicilio(direccion, latitud, longitud);
        if (d != null) {
            dom = new Domicilio(Integer.valueOf(d[0]), Integer.valueOf(d[4]), d[1], d[2], d[3], d[5], d[6], d[7]);
        }
        return dom;
    }

    public boolean guardarAsociarDomicilio(Domicilio domicilio, Persona persona) {
        Fecha fecha = new Fecha();
        boolean flag = false;
        int error = 0;
        if (domicilio.getTIPO() > 1) {
            if (fecha.compareMinorDate(domicilio.getVIGENCIA()) == true || fecha.compareEqualDate(domicilio.getVIGENCIA()) == true) {
                System.err.println("La fecha es menor o igual a la actual");
                error = 1;
            } else if ("".equals(domicilio.getPROPIETARIO())) {
                System.err.println("El nombre del propietario está vacío");
                error = 2;
            } else if (Integer.valueOf(domicilio.getTIEMPO_RESIDENCIA()) < 1) {
                System.err.println("El tiempo de residencia es inválido.");
                error = 3;
            }
        }
        if (error == 0) {
            int id = this.RECURSO.guardarDomicilio(domicilio.getDIRECCION(), domicilio.getLATITUD(), domicilio.getLONGITUD(), domicilio.getTIPO(), domicilio.getPROPIETARIO(), domicilio.getVIGENCIA(), domicilio.getTIEMPO_RESIDENCIA());
            if (id > 0) {
                flag = this.RECURSO.asociarDomicilioPersona(persona.getIdPersona(), id);
            } else {
                flag = false;
            }
        }
        return flag;
    }

    public Mes[] meses() {
        Mes[] meses = new Mes[12];
        meses[0] = new Mes("01", "Enero");
        meses[1] = new Mes("02", "Febrero");
        meses[2] = new Mes("03", "Marzo");
        meses[3] = new Mes("04", "Abril");
        meses[4] = new Mes("05", "Mayo");
        meses[5] = new Mes("06", "Junio");
        meses[6] = new Mes("07", "Julio");
        meses[7] = new Mes("08", "Agosto");
        meses[8] = new Mes("09", "Septiembre");
        meses[9] = new Mes("10", "Octubre");
        meses[10] = new Mes("11", "Noviembre");
        meses[11] = new Mes("12", "Diciembre");
        return meses;
    }

    public JTable buscarCoordenadas(JTable tabla, String direccion) {
        Geocoding ObjGeocod = new Geocoding();
        try {
            Point2D.Double resultadoCD = ObjGeocod.getCoordinates(direccion);
            String titulos[] = {"Direcciónes relacionadas encontradas vía GPS"};
            DefaultTableModel dtm = new DefaultTableModel(null, titulos);
            ArrayList<String> array = ObjGeocod.getAddress(resultadoCD.x, resultadoCD.y);
            if (array != null) {
                for (String item : array) {
                    Object[] cli = new Object[1];
                    cli[0] = item;
                    dtm.addRow(cli);
                }
                tabla.setModel(dtm);
            }
        } catch (UnsupportedEncodingException | MalformedURLException e) {
            System.out.println("Error: " + e);
        }
        return tabla;
    }
}
