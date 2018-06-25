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
        String titulos[] = {"ID", "Dirección", "Lat", "Long"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos);
        String[][] resultados = this.RECURSO.buscarDomicilios(direccion);
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

    public Domicilio buscarDomicilioGuardado(String direccion, String latitud, String longitud) {
        Domicilio dom = null;
        String[] d = this.RECURSO.buscarDomicilio(direccion, latitud, longitud);
        if (d != null) {
            dom = new Domicilio(Integer.valueOf(d[0]), d[1], d[2], d[3]);
        }
        return dom;
    }

    public Domicilio buscarDomicilioGuardado(int idDomicilio) {
        Domicilio dom = null;
        String[] d = this.RECURSO.buscarDomicilio(idDomicilio);
        if (d != null) {
            dom = new Domicilio(Integer.valueOf(d[0]), d[1], d[2], d[3]);
        }
        return dom;
    }

    public boolean asociarDomicilioPersona(int idDomicilio, Persona persona, Usuario usuario) {
        boolean flag;
        //int idPersona, int idDomicilio, int idUsuario, int idSucursal
        flag = this.RECURSO.asociarDomicilioPersona(persona.getIdPersona(), idDomicilio, usuario.getIdUsuario(), usuario.getIdSucursal());
        return flag;
    }

    public boolean actualizarDomicilio(Domicilio domicilio, String direccionNueva, int idDomicilio) {
        boolean f = false;
        if (domicilio != null && !direccionNueva.isEmpty() && idDomicilio > 0) {
            try {
                Geocoding ObjGeocod = new Geocoding();
                Point2D.Double resultadoCD = ObjGeocod.getCoordinates(direccionNueva);
                if (String.valueOf(resultadoCD.x).equals(domicilio.getLATITUD()) && String.valueOf(resultadoCD.y).equals(domicilio.getLONGITUD())) {
                    f = this.RECURSO.actualizarDatosDomicilio(direccionNueva, idDomicilio);
                } else {
                    System.out.println("services.Domicilios_service.actualizarDomicilio() : LAS COORDENADAS SON DIFERENTES");
                    f = false;
                   }
            } catch (UnsupportedEncodingException | MalformedURLException e) {
                System.out.println("Error: " + e);
            }
        } else {
            f = false;
        }
        return f;
    }

    public boolean guardarAsociarDomicilio(Domicilio domicilio, Persona persona, Usuario usuario) {
        boolean flag;
        int idDomicilio = this.RECURSO.guardarDomicilio(domicilio.getDIRECCION(), domicilio.getLATITUD(), domicilio.getLONGITUD());
        if (idDomicilio > 0) {
            flag = this.asociarDomicilioPersona(idDomicilio, persona, usuario);
        } else {
            flag = false;
        }

        return flag;
    }

    public boolean guardarDomicilio(Domicilio domicilio) {        
        boolean flag;
        int idDomicilio = this.RECURSO.guardarDomicilio(domicilio.getDIRECCION(), domicilio.getLATITUD(), domicilio.getLONGITUD());
        flag = idDomicilio > 0;
        return flag;
    }

    public JTable buscarCoordenadas(JTable tabla, String direccion) {
        Geocoding ObjGeocod = new Geocoding();
        String titulos[] = {"ID", "Dirección relacionada encontrada vía GPS"};
        DefaultTableModel dtm = new DefaultTableModel(null, titulos);
        try {
            Point2D.Double resultadoCD = ObjGeocod.getCoordinates(direccion);
            ArrayList<String> array = ObjGeocod.getAddress(resultadoCD.x, resultadoCD.y);
            if (array != null) {
                int i = 1;
                for (String item : array) {
                    Object[] cli = new Object[2];
                    cli[0] = i;
                    cli[1] = item;
                    dtm.addRow(cli);
                    i++;
                }
            } else {
                Object[] cli = new Object[2];
                cli[0] = "0";
                cli[1] = "NO SE OBTUVIERON RESULTADOS";
                dtm.addRow(cli);
            }
        } catch (UnsupportedEncodingException | MalformedURLException e) {
            System.out.println("Error: " + e);
        }
        TableCreator tcr = new TableCreator();
        tabla.setModel(dtm);
        tabla.setColumnModel(tcr.resizeTableDireccionesGuardadas(tabla));
        return tabla;
    }

}
