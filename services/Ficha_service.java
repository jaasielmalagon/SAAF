package services;
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
}
