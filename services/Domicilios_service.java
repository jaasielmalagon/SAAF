package services;

import objects.Domicilio;
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
    
    public Domicilio buscarDomicilio(String direccion, String latitud, String longitud){
        Domicilio dom = null;
        String[] d = this.RECURSO.buscarDomicilio(direccion, latitud, longitud);
        if (d != null) {
            dom = new Domicilio(Integer.valueOf(d[0]), Integer.valueOf(d[4]), d[1], d[2], d[3], d[5], d[6], d[7]);
        }
        return dom;
    }
    
    public boolean guardarAsociarDomicilio(Domicilio domicilio, Persona persona){
        int id = this.RECURSO.guardarDomicilio(domicilio.getDIRECCION(), domicilio.getLATITUD(), domicilio.getLONGITUD(), domicilio.getTIPO(), domicilio.getPROPIETARIO(), domicilio.getVIGENCIA(), domicilio.getTIEMPO_RESIDENCIA());
        if (id > 0) {
            return this.RECURSO.asociarDomicilioPersona(persona.getIdPersona(), id);
        }else{
            return false;
        }
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
}
