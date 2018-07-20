package services;
import objects.Cliente;
import objects.Domicilio;
import objects.Persona;
import resources.Ficha_resource;

/**
 *
 * @author JMalagon
 */
public class Ficha_service {

    private final Ficha_resource recurso;
    private final String modulo;

    public Ficha_service(String modulo) {
        this.modulo = modulo;
        this.recurso = new Ficha_resource(this.modulo);
    }

    public String estado(int id) {
        return recurso.estado(id);
    }

    public Domicilio domicilio(int idDomicilio) {
        Domicilio domicilio = null;
        if (idDomicilio > 0) {
            domicilio = new Domicilios_service(modulo).buscarDomicilioGuardado(idDomicilio);
        }
        return domicilio;
    }

    public Persona persona(int idSucursal, int idPersona) {
        Persona persona = null;        
        if (idSucursal > 0 && idPersona > 0) {
            persona = new Personas_service(modulo).persona(idSucursal, idPersona);
        }
        return persona;
    }

    public Cliente cliente(int idPersona) {
        Cliente cliente = null;
        String[] datos = this.recurso.getCliente(idPersona);
        if (datos != null) {
            cliente = new Cliente();
            cliente.setADC(datos[0]);
            cliente.setINGRESOS(datos[1]);
            cliente.setEGRESOS(datos[2]);            
            cliente.setOCUPACION(datos[3]);            
            cliente.setDEPENDIENTES(datos[4]);            
            cliente.setESTUDIOS(datos[5]);            
            cliente.setEMPRESA(datos[6]);
            cliente.setHORA_ENTRADA(datos[7]);
            cliente.setHORA_SALIDA(datos[8]);
            cliente.setDOMICILIO_EMPRESA(datos[9]);
            cliente.setTEL_EMPRESA(datos[10]);
            cliente.setTIPO_VIVIENDA(datos[11]);
            cliente.setVIGENCIA(datos[12]);
            cliente.setTIEMPO_RESIDENCIA(datos[13]);
            cliente.setSCORE(datos[14]);
            cliente.setSTATUS(datos[15]);
            cliente.setACTIVIDAD(datos[16]);
        }
        return cliente;
    }
}
