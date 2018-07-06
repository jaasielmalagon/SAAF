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
        Persona personas = null;
        String[] array = this.recurso.persona(idSucursal, idPersona);
        if (array != null) {
            personas = new Persona(Integer.parseInt(array[0]), array[1], array[2], array[3], array[4], Integer.parseInt(array[5]), array[6], array[7], array[8], Integer.parseInt(array[9]), array[10], array[11], Integer.parseInt(array[12]), Integer.parseInt(array[13]), Integer.parseInt(array[14]), Integer.parseInt(array[15]));
        }
        return personas;
    }
}
